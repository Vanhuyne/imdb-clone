package com.huy.imdb_backend.service.impl;

import com.huy.imdb_backend.dto.GenreDTO;
import com.huy.imdb_backend.dto.MovieDTO;
import com.huy.imdb_backend.models.Genre;
import com.huy.imdb_backend.models.Movie;
import com.huy.imdb_backend.repository.MovieRepo;
import com.huy.imdb_backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepo movieRepo;

    @Override
    public Page<MovieDTO> getAllMovies(Pageable pageable) {
        return movieRepo.findAll(pageable).map(
                this::convertToMovieDTO
        );
    }

    private MovieDTO convertToMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .runtime(movie.getRuntime())
                .overview(movie.getOverview())
                .posterPath(movie.getPosterPath())
                .backdropPath(movie.getBackdropPath())
                .tmdbId(movie.getTmdbId())
                .createdAt(movie.getCreatedAt())
                .genres(movie.getGenres().stream().map(
                        this::convertToGenreDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    private GenreDTO convertToGenreDTO(Genre genre) {
        return GenreDTO.builder()
                .genreId(genre.getGenreId())
                .name(genre.getName())
                .build();
    }
}
