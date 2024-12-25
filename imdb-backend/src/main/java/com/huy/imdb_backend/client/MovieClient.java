package com.huy.imdb_backend.client;

import com.huy.imdb_backend.client.mapper.GenreMapper;
import com.huy.imdb_backend.client.response.MovieApiResponse;
import com.huy.imdb_backend.dto.GenreDTO;
import com.huy.imdb_backend.dto.MovieDTO;
import com.huy.imdb_backend.exception.ResourceNotFoundException;
import com.huy.imdb_backend.models.Genre;
import com.huy.imdb_backend.models.Movie;
import com.huy.imdb_backend.repository.MovieRepo;
import com.huy.imdb_backend.utils.GenerateRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieClient {

    private final RestTemplate restTemplate;
    private final MovieRepo movieRepo;

    @Value("${tmdb.api.url}")
    private String baseUrl;

    @Value("${tmdb.api.token}")
    private String token;

    private List<MovieDTO> getMovies(int pageEnd) {
        List<MovieDTO> movieDTOList = new ArrayList<>();

        for (int page = 1; page <= pageEnd; page++) {
            try {
                String url = baseUrl + "/discover/movie?language=en-US&page=" + page + "&sort_by=popularity.desc";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);
                headers.set("accept", "application/json");

                // create an HTTP entity with the headers
                HttpEntity<Void> entity = new HttpEntity<>(headers);

                ResponseEntity<MovieApiResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        MovieApiResponse.class
                );


                //extract the body from the response
                MovieApiResponse movieApiResponse = response.getBody();
                if (movieApiResponse != null && !movieApiResponse.getResults().isEmpty()) {
                    List<MovieDTO> pageMovies = movieApiResponse.getResults().stream()
                            .map(movie -> MovieDTO.builder()
                                    .movieId(GenerateRandom.generateRandomLong())
                                    .tmdbId(movie.getId())
                                    .title(movie.getTitle())
                                    .releaseDate(LocalDate.parse(movie.getReleaseDate()))
                                    .overview(movie.getOverview())
                                    .posterPath(movie.getPosterPath())
                                    .backdropPath(movie.getBackdropPath())
                                    .popularity(movie.getPopularity())
                                    .voteAverage(movie.getVoteAverage())
                                    .voteCount(movie.getVoteCount())
                                    .createdAt(LocalDateTime.now())
                                    .genres(GenreMapper.mapGenreIdsToDTOs(Set.copyOf(movie.getGenreIds())))
                                    .build())
                            .collect(Collectors.toList());
                    movieDTOList.addAll(pageMovies);
                } else {
                    log.warn("No movies found on page {}", page);
                }
            } catch (Exception e) {
                log.error("Error fetching movies on page {}: {}", page, e.getMessage());
            }
        }
        return movieDTOList;
    }

    /*
     * Fetch movies from api.themoviedb.org  and save them to the database
     * */
    public void syncMovie() {
        List<MovieDTO> movieDTOList = getMovies(10);

        if (movieDTOList.isEmpty()) {
            throw new ResourceNotFoundException("No movies fetched from the API.");
        }

        // check if the movie already exists in the database
        movieDTOList.forEach(movieDTO -> {
            Optional<Movie> movieOptional = movieRepo.findByTmdbId(movieDTO.getTmdbId());
            if (movieOptional.isEmpty()) {
                movieRepo.save(convertToMovie(movieDTO));
            }
        });
    }


    private Movie convertToMovie(MovieDTO movieDTO) {
        return Movie.builder()
                .movieId(movieDTO.getMovieId())
                .title(movieDTO.getTitle())
                .releaseDate(movieDTO.getReleaseDate())
                .runtime(movieDTO.getRuntime())
                .overview(movieDTO.getOverview())
                .posterPath(movieDTO.getPosterPath())
                .backdropPath(movieDTO.getBackdropPath())
                .tmdbId(movieDTO.getTmdbId())
                .createdAt(movieDTO.getCreatedAt())
                .popularity(movieDTO.getPopularity())
                .voteAverage(movieDTO.getVoteAverage())
                .voteCount(movieDTO.getVoteCount())
                .genres(movieDTO.getGenres().stream().map(
                                this::convertToGenre)
                        .collect(Collectors.toSet()))
                .build();
    }

    private Genre convertToGenre(GenreDTO genreDTO) {
        return Genre.builder()
                .genreId(genreDTO.getGenreId())
                .name(genreDTO.getName())
                .build();
    }


}
