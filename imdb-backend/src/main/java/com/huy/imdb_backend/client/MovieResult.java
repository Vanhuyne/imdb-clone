package com.huy.imdb_backend.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieResult {
    private int id;
    private String title;
    private String releaseDate;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private double popularity;
    private double voteAverage;
    private int voteCount;
    private List<Long> genreIds;
}
