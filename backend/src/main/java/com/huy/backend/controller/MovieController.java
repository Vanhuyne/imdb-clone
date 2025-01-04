package com.huy.backend.controller;

import com.huy.backend.dto.MovieDTO;
import com.huy.backend.service.impl.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {
    private final MovieServiceImpl movieServiceImpl;

    @GetMapping
    public ResponseEntity<Page<MovieDTO>>getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return new ResponseEntity<>(movieServiceImpl.getAllMovies(pageable), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieDTO>> searchMovies(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MovieDTO> movieDTOS =  movieServiceImpl.searchMovies(query, page, size);
//        movieServiceImpl.isDataCached(query, page, size);
        return new ResponseEntity<>(movieDTOS, HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long movieId) {
        return new ResponseEntity<>(movieServiceImpl.getMovieById(movieId), HttpStatus.OK);
    }


}
