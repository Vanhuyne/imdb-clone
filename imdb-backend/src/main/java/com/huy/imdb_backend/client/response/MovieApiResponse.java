package com.huy.imdb_backend.client.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieApiResponse {
    private int page;
    private List<MovieResult> results;
}
