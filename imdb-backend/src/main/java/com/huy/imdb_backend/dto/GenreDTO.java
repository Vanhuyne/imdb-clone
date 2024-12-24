package com.huy.imdb_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GenreDTO {
    private Long genreId;
    private String name;
}
