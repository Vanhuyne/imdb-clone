package com.huy.imdb_backend.controller;

import com.huy.imdb_backend.dto.MovieDTO;
import com.huy.imdb_backend.service.impl.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieServiceImpl movieServiceImpl;

    @GetMapping
    public ResponseEntity<Page<MovieDTO>>getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(movieServiceImpl.getAllMovies(pageable), HttpStatus.OK);
    }
}
