package com.huy.imdb_backend.client;

import com.huy.imdb_backend.client.mapper.GenreMapper;
import com.huy.imdb_backend.client.response.MovieApiResponse;
import com.huy.imdb_backend.dto.MovieDTO;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieClient {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.url}")
    private String baseUrl;

    @Value("${tmdb.api.token}")
    private String token;

    public List<MovieDTO> getMovies() {
        String url = baseUrl + "/discover/movie?language=en-US&page=1&sort_by=popularity.desc";
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
        if (movieApiResponse == null || movieApiResponse.getResults().isEmpty()) {
//            throw new RuntimeException("Failed to fetch movies or no results found");
            log.error("Failed to fetch movies or no results found");
            log.info(movieApiResponse.toString());
        }

        return movieApiResponse.getResults().stream()
                .map(movie -> {
                    return MovieDTO.builder()
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
                            .build();

                })
                .collect(Collectors.toList());
    }
}
