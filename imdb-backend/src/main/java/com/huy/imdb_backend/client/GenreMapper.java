package com.huy.imdb_backend.client;

import com.huy.imdb_backend.dto.GenreDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreMapper {
    private static final Map<Long, String> GENRE_MAP = new HashMap<>();
    static {
        GENRE_MAP.put(28L, "Action");
        GENRE_MAP.put(14L, "Fantasy");
        GENRE_MAP.put(35L, "Comedy");
        GENRE_MAP.put(18L, "Drama");
        // Add more mappings as needed
    }

    public static Set<GenreDTO> mapGenreIdsToDTOs(Set<Long> genreIds) {
        return genreIds.stream()
                .map(id -> GenreDTO.builder()
                        .genreId(id)
                        .name(GENRE_MAP.get(id))
                        .build())
                .collect(Collectors.toSet());
    }
}
