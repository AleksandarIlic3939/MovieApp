import { HttpHeaders } from '@angular/common/http';
import { Component, ElementRef, HostListener, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../model/category';
import { Movie } from '../model/movie';
import { CategoryService } from '../service/category.service';
import { MovieService } from '../service/movie.service';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  categoryId: number;

  p: number = 1;

  page: number = 0;
  size: number = 5;
  totalItems: number = 0;

  movies: Movie[] = [];
  moviesCopy: Movie[] = [];
  categories: Category[] = [];
  response: any;

  headers: HttpHeaders;

  currentMovie: number = -1;
  currentCategory: number = 0;

  constructor(private movieService: MovieService, private categoryService: CategoryService, private route: ActivatedRoute, private router: Router, private element: ElementRef, private renderer: Renderer2) { }

  async ngOnInit(): Promise<void> {
    this.headers = new HttpHeaders().set('Authorization', 'Bearer ' + sessionStorage.getItem("token"));
    this.categoryId = this.route.snapshot.params['categoryId'];
    await this.getAllMoviesAndCategories(this.headers);
    console.log(sessionStorage.getItem("token"));
  }

  @HostListener('window:keydown', ['$event'])
  spaceEvent(event: any) {
    if (event.keyCode == 38) {
      // the up arrow key was pressed
      if (this.currentCategory > 1) {
        this.currentCategory--;
        let moviesCopy: Movie[] = [];
        let position = 0;
        let bool = false;
        this.movies.forEach((item: Movie) => {
          if (item.category.id == this.currentCategory) {
            if (bool == false) {
              position = this.movies.indexOf(item);
              bool = true;
            }
            moviesCopy.push(item);
          }
        });
        this.currentMovie = position;
        this.renderPart();
        console.log(this.movies[position]);
      }
    } else if (event.keyCode == 40) {
      // the down arrow key was pressed
      if (this.currentCategory < this.categories.length - 1) {
        this.currentCategory++;
        let moviesCopy: Movie[] = [];
        let position = 0;
        let bool = false;
        this.movies.forEach((item: Movie) => {
          if (item.category.id == this.currentCategory) {
            if (bool == false) {
              position = this.movies.indexOf(item);
              bool = true;
            }
            moviesCopy.push(item);
          }
        });
        this.currentMovie = position;
        this.renderPart();
        console.log(this.movies[position]);
      }
    } else if (event.keyCode == 37) {
      // the left arrow key was pressed
      if (this.currentMovie > 0) {
        this.currentMovie--;
        this.movies.forEach((item: Movie) => {
          if (this.movies[this.currentMovie] == item) {
            this.renderPart();
            console.log(item);
          }
        });
      }
    } else if (event.keyCode == 39) {
      // the right arrow key was pressed
      if (this.currentMovie < this.movies.length - 1) {
        this.currentMovie++;
        this.movies.forEach((item: Movie) => {
          if (this.movies[this.currentMovie] == item) {
            this.renderPart();
            console.log(item);
          }
        });
      }
    }
  }

  private renderPart() {
    let part = this.element.nativeElement.querySelector('.cover');
    this.renderer.setStyle(part, 'opacity', '0.8');
  }
  
  private async getAllMoviesAndCategories(headers: HttpHeaders): Promise<void> {
    let page: number = 0;
    let size: number = 0;
    this.categoryService.getCategoryList(this.page, this.size, headers).subscribe((data: any) => {
      this.categories = data['content'];
      console.log(this.categories);
      this.categories.forEach((item: Category) => {
        let index: number = item.id;
        this.movieService.getMovieListByCategory(index, page, size, headers).subscribe((data: any) => {
          this.moviesCopy = data['content'];
          this.totalItems = data['totalElements'];
          console.log(this.moviesCopy);
          console.log(this.totalItems);
          this.moviesCopy.forEach((item: any) => {
            this.movies.push(item);
          });
        });
      });
    });
  }

  public getMoviesByCategoryId(categoryId: number): any {
    this.moviesCopy = [];
    this.movies.filter((item: Movie) => {
      if (item.category.id === categoryId) {
        this.moviesCopy.push(item);
      }
    });
    return this.moviesCopy;
  }

  public movieDetails(movieId: number) {
    this.router.navigate(['movie-details', movieId]);
  }

  public logout() {
    sessionStorage.clear()
    this.router.navigate(['login']);
  }

  public goToHome() {
    this.router.navigate(['movie-list/1']);
  }

}