import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MovieResponse } from '../models/Movie';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  baseUrl = 'http://localhost:8081/api/movies';
  constructor(
    private http: HttpClient
  ) { }

  getMovies() : Observable<MovieResponse> {
    return this.http.get<MovieResponse>(this.baseUrl);
  }
}
