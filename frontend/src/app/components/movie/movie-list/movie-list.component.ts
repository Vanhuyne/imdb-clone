import { Component } from '@angular/core';
import { Movie, MovieResponse } from '../../../models/Movie';
import { MovieService } from '../../../service/movie.service';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrl: './movie-list.component.scss'
})
export class MovieListComponent {
  movies: Movie[] = []
  
  constructor(
    private movieService: MovieService
  ) {
    this.movieService.getMovies().subscribe((response : MovieResponse) => {
      this.movies = response.content;
    })
  }

  getImageUrl(path: string): string {
    return `https://image.tmdb.org/t/p/original${path}`;
  }
}
