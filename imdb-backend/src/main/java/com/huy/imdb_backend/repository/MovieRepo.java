package com.huy.imdb_backend.repository;

import com.huy.imdb_backend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<Movie , Long> {
    Optional<Movie> findByTmdbId(Integer tmdbId);
}
