package com.huy.imdb_backend.service;

import com.huy.imdb_backend.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieDTO> getAllMovies(Pageable pageable);
}
