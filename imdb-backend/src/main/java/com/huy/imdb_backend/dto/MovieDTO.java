package com.huy.imdb_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Data
public class MovieDTO {
    private Long movieId;
    private String title;
    private LocalDate releaseDate;
    private Integer runtime;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private Integer tmdbId;
    private LocalDateTime createdAt;
    private Set<GenreDTO> genres;
    private Double popularity;
    private Double voteAverage;
    private Integer voteCount;
}
