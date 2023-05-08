import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MovieService } from '../movie.service';
import Swal from 'sweetalert2';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Pipe, PipeTransform } from '@angular/core';



@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.css']
})
export class RecommendationComponent {

  constructor(private activateRoute: ActivatedRoute, private movie: MovieService) {
  }

  API_KEY = 'api_key=fafbf863cca0b32b03435f4b8fc46490';
  BASE_URL = 'https://api.themoviedb.org/3';
  API_URL = this.BASE_URL + '/discover/movie?sort_by=popularity.desc&' + this.API_KEY;
  IMG_URL = 'https://image.tmdb.org/t/p/w500';
  searchURL = this.BASE_URL + '/search/movie?' + this.API_KEY;

  movieList: any;
  movieId = "";
  recommendedList: any
  recommendMovie: any = []
  movieKey: any
  movieUrl: any
  getMovieCastResult: [] | any = []

  ngOnInit(): void {
    this.activateRoute.paramMap.subscribe(param => {
      let id = param.get("id") ?? ""
      this.movieId = id;
      console.log("m id: " + id);
      this.getMovie(id);
      this.getRecommendedMovies(id);
      this.getVideo(id);
      this.getMovieCast(id);
    });
  }



  //using movieID we are fetching movie Object
  getMovie(movieId: any) {
    console.log("movie id :" + movieId);
    let favMovieUrl = this.BASE_URL + "/movie/" + movieId + "?" + this.API_KEY + "&language=en-US";
    this.movieList = fetch(favMovieUrl).then(res => res.json()).then(data => {
      this.movieList = data;
      console.log("results " + data);
      console.log("title " + this.movieList);
    })
  }


  getRecommendedMovies(movieId: any) {
    let favMovieUrl = this.BASE_URL + "/movie/" + movieId + "/recommendations?" + this.API_KEY + "&language=en-US&page=1";
    fetch(favMovieUrl).then(res => res.json()).then(data => {
      this.recommendedList = data.results;
      console.log("Recommended data :" + this.recommendedList[0].id);


      data.results.forEach((element: any) => {
        console.log(element.id);
        let urlrem;
        this.movie.getMovieVideo(element.id).subscribe({
          next: (result) => {
            result.results.forEach((elements: any) => {
              if (elements.type == "Trailer") {
                urlrem = "https://www.youtube.com/embed/" + elements.key;
                this.recommendMovie.push({ id: element.id, title: element.title, poster_path: element.poster_path, url: urlrem })
              }
            });
          }
        });
      });
      console.log(this.recommendMovie);
    })
  }


  //selected movie youtube link
  getVideo(id: any) {
    this.movie.getMovieVideo(id).subscribe({
      next: (result) => {
        result.results.forEach((element: any) => {
          if (element.type == "Trailer") {
            this.movieKey = "https://www.youtube.com/embed/" + element.key;
            //this.movieUrl = "https://www.youtube.com/embed/" + element.key;

          }
        });
      }, error: (error) => { Swal.fire('Sorry', `${error}`, 'warning') }
    });
  }


  getMovieCast(id: any) {
    this.movie.getMovieCast(id).subscribe((result) => {
      console.log(result, 'movieCast#');
      console.log('result.cast', result.cast[0]);
      for (let i = 0; i < 5; i++) {
        console.log('result.cast' + i + result.cast[i]);
        this.getMovieCastResult[i] = result.cast[i];
      }
      console.log("getMovieCastResult     :  ", this.getMovieCastResult);
    });
  }


}
