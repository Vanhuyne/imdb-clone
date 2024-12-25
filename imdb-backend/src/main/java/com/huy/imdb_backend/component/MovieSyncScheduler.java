package com.huy.imdb_backend.component;

import com.huy.imdb_backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSyncScheduler {

    private final MovieService movieService;

    /**
     * Sync movies every 10 days at fixed intervals.
     */
    @Scheduled(fixedRate = 10 * 24 * 60 * 60 * 1000) // 10 days in milliseconds
    public void synchronizeMovies() {
        movieService.syncMovie();
    }
}
