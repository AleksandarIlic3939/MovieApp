import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Movie } from '../model/movie';
import { MovieService } from '../service/movie.service';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {

  movieId: number;
  movie: Movie;
  headers: HttpHeaders;

  constructor(private movieService: MovieService, private route: ActivatedRoute, private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.headers = new HttpHeaders().set('Authorization', 'Bearer ' + sessionStorage.getItem("token"));
    this.movieId = this.route.snapshot.params['movieId'];
    console.log(sessionStorage.getItem("token"));

    await this.getMovie();
  }

  private async getMovie(): Promise<void> {
    this.movie = new Movie();
    this.movieService.getMovieById(this.movieId, this.headers).subscribe((data: any) => {
      this.movie = data;
      console.log(data);
    });
  }

  public logout() {
    sessionStorage.clear()
    this.router.navigate(['login']);
  }

  public goToHome() {
    this.router.navigate(['movie-list/1']);
  }

}