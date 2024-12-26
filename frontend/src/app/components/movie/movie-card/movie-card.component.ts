import { Component } from '@angular/core';

interface Movie {
  id: number;
  title: string;
  poster: string;
  rating: number;
  imdbRating: number;
  maxRating: number;
}
@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrl: './movie-card.component.scss'
})

export class MovieCardComponent {
  
  movies: Movie[] = [
    {
      id: 1,
      title: 'The Shawshank Redemption',
      poster: 'assets/shawshank.jpg',
      rating: 80,
      imdbRating: 7,
      maxRating: 10
    },
    // Add other movies...
  ];
}
