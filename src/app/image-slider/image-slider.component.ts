import { Component } from '@angular/core';
import { MovieService } from '../movie.service';
import { MatIconModule } from '@angular/material/icon';
import { MessageService } from '../message.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-image-slider',
  templateUrl: './image-slider.component.html',
  styleUrls: ['./image-slider.component.css'],
})
export class ImageSliderComponent {
  constructor(
    private movieService: MovieService,
    private auth: AuthService,
    private messageService: MessageService,
    private router: Router
  ) {}
  genres = [
    {
      id: 28,
      name: 'Action',
    },
    {
      id: 12,
      name: 'Adventure',
    },
    {
      id: 16,
      name: 'Animation',
    },
    {
      id: 35,
      name: 'Comedy',
    },
    {
      id: 80,
      name: 'Crime',
    },
    {
      id: 99,
      name: 'Documentary',
    },
    {
      id: 18,
      name: 'Drama',
    },
    {
      id: 10751,
      name: 'Family',
    },
    {
      id: 14,
      name: 'Fantasy',
    },
    {
      id: 36,
      name: 'History',
    },
    {
      id: 27,
      name: 'Horror',
    },
    {
      id: 10402,
      name: 'Music',
    },
    {
      id: 9648,
      name: 'Mystery',
    },
    {
      id: 10749,
      name: 'Romance',
    },
    {
      id: 878,
      name: 'Science Fiction',
    },
    {
      id: 10770,
      name: 'TV Movie',
    },
    {
      id: 53,
      name: 'Thriller',
    },
    {
      id: 10752,
      name: 'War',
    },
    {
      id: 37,
      name: 'Western',
    },
  ];





  API_KEY = 'api_key=fafbf863cca0b32b03435f4b8fc46490';
  BASE_URL = 'https://api.themoviedb.org/3';
  API_URL = this.BASE_URL + '/discover/movie?sort_by=popularity.desc&' + this.API_KEY;
  IMG_URL = 'https://image.tmdb.org/t/p/w500';
  searchURL = this.BASE_URL + '/search/movie?' + this.API_KEY;
  searchText: String = '';

  movieList: [] | any;
  tempArray: [] | any = [];
  flag: boolean = this.auth.isLoggedIn;
  userdetails: any = {};
  viewList: boolean = false;
  favList: any;
  watchListMovies: string[] = [];
  favListMovies: string[] = [];
  watchIn: boolean = false;
  favIn: boolean = false;
  afterDelete: any;
  searchMovieUrl: any;

  ngOnInit(): void {
    this.getUserDetails();
    this.getMovies();
  }

  //method to get movies from api
  getMovies() {
    this.watchIn = false;
    this.favIn = false;
    this.viewList = false;
    this.movieList = fetch(this.API_URL)
      .then((res) => res.json())
      .then((data) => {
        console.log(data.results);
        this.movieList = data.results;
        console.log(data.results[0].title);
        console.log('title ' + this.movieList[0].title);
      });
  }


  watchList: any;
  watchLater(movieId: any) {
    if (this.flag) {
      this.watchList = this.movieService.watchLater(movieId).subscribe({
        next: (data) => {
          this.watchList = data;
        },
        error: (error) => {
          this.messageService.provideMessages(
            'movie Already exists!!',
            'exists'
          );
        },
      });

      console.log('watch list ' + this.watchList);
      console.log('movie id' + movieId);
      this.messageService.provideMessages('added successfully', 'success');
    } else {
      Swal.fire('Sorry', 'You need to Login first', 'warning');
    }
  }


  addTofavorite(movieId: any) {
    if (this.flag) {
      this.movieService.addToFavourites(movieId).subscribe({
        next: (data) => {
          this.favList = data;
        },
        error: (error) => {
          this.messageService.provideMessages(
            'movie Already exists!!',
            'exists'
          );
        },
      });
      this.messageService.provideMessages('added successfully', 'success');
      console.log('fav list :' + this.favList);
    } else {
      Swal.fire('Sorry', 'You need to Login first', 'warning');
    }
  }



  viewWatchList() {
    this.watchIn = true;
    this.favIn = false;
    this.viewList = true;
    this.movieService.getUserDetails().subscribe(
      (data) => {
        this.watchListMovies = data.watchList;

        this.movieList.length = 0;
        for (let i = 0; i < this.watchListMovies.length; i++) {
          console.log(this.watchListMovies[i]);
          const favMovieUrl = this.BASE_URL + '/movie/' + this.watchListMovies[i] + '?' + this.API_KEY; // fetching movie
          console.log(favMovieUrl);
          fetch(favMovieUrl)
            .then((res) => res.json())
            .then((data) => {
              console.log(data);
              this.movieList[i] = data;
              // this.watchListMovies = data;
            });
        }
        Swal.fire('welcome to your ', 'watchlist', 'success');
      },
      (error) => {
        console.log('error msg : ');
      }
    );
  }



  viewfavList() {
    this.viewList = true;
    this.favIn = true;
    this.watchIn = false;
    this.movieService.getUserDetails().subscribe(
      (data) => {
        console.log('fav list :' + data.favouriteMovies);

        this.favListMovies = data.favouriteMovies;

        this.movieList.length = 0;
        for (let i = 0; i < this.favListMovies.length; i++) {
          console.log(this.favListMovies[i]);
          const favMovieUrl =
            this.BASE_URL +
            '/movie/' +
            this.favListMovies[i] +
            '?' +
            this.API_KEY;
          console.log(favMovieUrl);
          fetch(favMovieUrl)
            .then((res) => res.json())
            .then((data) => {
              console.log(data);
              this.movieList[i] = data;
              this.favListMovies = data;
            });
        }
        Swal.fire('welcome to your ', 'favourites', 'success');
      },
      (error) => {
        console.log('error msg : ');
      }
    );
  }



  deleteMovieFromList(movieId: any) {
    if (this.watchIn) {
      this.movieService
        .deleteMovieFromWatchList(movieId)
        .subscribe((data) => data);
      Swal.fire('movie ', 'deleted', 'success');
      this.tempArray = [];
      console.log(this.movieList);

      for (let i = 0; i < this.movieList.length; i++) {
        if (this.movieList[i].id != movieId) {
          this.tempArray.push(this.movieList[i]);
        }
      }
      this.movieList = this.tempArray;
    }
    if (this.favIn) {
      this.movieService
        .deleteMovieFromFavList(movieId)
        .subscribe((data) => data);
      Swal.fire('movie ', 'deleted', 'success');
      this.tempArray = [];
      console.log(this.movieList);

      for (let i = 0; i < this.movieList.length; i++) {
        if (this.movieList[i].id != movieId) {
          this.tempArray.push(this.movieList[i]);
        }
      }
      this.movieList = this.tempArray;
      console.log(this.tempArray);
    }
  }


  navigateTo(id: any) {
    if (this.flag) {
      this.router.navigate(['/recommendation', id]);
    } else {
      Swal.fire('Sorry', 'You need to login First', 'warning');
    }
  }


  logOut() {
    this.auth.logOutUser();
    this.flag = false;
    Swal.fire('You have LoggedOut', 'successfully', 'success');
  }



  SearchMovie(searchText: String) {
    console.log(searchText);
    if (searchText.length != 0) {
      console.log(searchText);
      this.searchMovieUrl = this.searchURL + '&query=' + searchText;
      fetch(this.searchMovieUrl)
        .then((res) => res.json())
        .then((data) => {
          this.movieList = data.results;
          console.log(this.movieList);
        });
    } else {
      this.getMovies();
    }
  }

  getUserDetails() {
    this.movieService.getUserDetails().subscribe((data) => {
      this.userdetails = data;
    });
  }
  menuOpen: boolean = false;
  toggleMenu() {
    this.menuOpen = !this.menuOpen;
    console.log(this.menuOpen);
    console.log(this.auth.isLoggedIn);
  }

  genreUrl: any;
  getGenre(id: any) {
    console.log(id);
    this.genreUrl =
      this.BASE_URL + '/discover/movie?' + this.API_KEY + '&with_genres=' + id;
    fetch(this.genreUrl)
      .then((res) => res.json())
      .then((data) => {
        this.movieList = data.results;
        console.log(this.movieList);
      });
  }

  ReleaseDateUrl: any;
  SortByRating() {
    this.ReleaseDateUrl =
      this.BASE_URL +
      '/discover/movie?' +
      this.API_KEY +
      '&sort_by=vote_average.desc';
    fetch(this.ReleaseDateUrl)
      .then((res) => res.json())
      .then((data) => {
        // console.log(data.results);
        this.movieList = data.results;
        console.log(this.movieList);
      });
  }


  langUrl: any;
  getLangEn() {
    this.langUrl =
      this.BASE_URL +
      '/discover/movie?' +
      this.API_KEY +
      '&with_original_Language=en';
    fetch(this.langUrl)
      .then((res) => res.json())
      .then((data) => {
        this.movieList = data.results;
        console.log(this.movieList);
      });
  }

  getLangKo() {
    this.langUrl =
      this.BASE_URL +
      '/discover/movie?' +
      this.API_KEY +
      '&with_original_Language=ko';
    fetch(this.langUrl)
      .then((res) => res.json())
      .then((data) => {
        this.movieList = data.results;
        console.log(this.movieList);
      });
  }
}
