import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) {

  }
  ngOnInit(): void {


  }

  API_KEY = 'api_key=fafbf863cca0b32b03435f4b8fc46490';
  key = 'fafbf863cca0b32b03435f4b8fc46490';
  BASE_URL = 'https://api.themoviedb.org/3';
  API_URL = this.BASE_URL + '/discover/movie?sort_by=popularity.desc&' + this.API_KEY;
  IMG_URL = 'https://image.tmdb.org/t/p/w500';
  searchURL = this.BASE_URL + '/search/movie?' + this.API_KEY;

  Url1: string = "http://localhost:9002/userMovie"
  Url2: string = "http://localhost:9002/userAuth"




  movies: any;
  getMovies() {
    this.movies = fetch(this.API_URL).then(res => res.json()).then(data => {
      console.log(data.results)
    })
    return this.movies;
  }

  watchLater(movieId: any): Observable<String> {
    let httpHeaders = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = { headers: httpHeaders }

    return this.http.post<String>(`${this.Url1}/user/addToWatchList/${movieId}`, movieId, requestOptions);

  }

  addToFavourites(movieId: any): Observable<String> {
    let httpHeaders = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = { headers: httpHeaders }
    return this.http.post<String>(`${this.Url1}/user/addToFavourites/${movieId}`, movieId, requestOptions);

  }

  getFavList(movieId: any): Observable<String> {
    let httpHeaders = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = { headers: httpHeaders }
    return this.http.post<String>(`${this.Url1}/user/addToFavourites/${movieId}`, movieId, requestOptions);

  }

  getUserDetails(): Observable<any> {
    let httpHeaders = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = { headers: httpHeaders }
    return this.http.get<any>(`${this.Url1}/user/userData`, requestOptions);

  }
  deleteMovieFromWatchList(movieId: any): Observable<any> {
    let httpHeaders = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = { headers: httpHeaders }
    return this.http.delete<any>(`${this.Url1}/user/watchlist/deleteMovie/${movieId}`, requestOptions);
  }
  deleteMovieFromFavList(movieId: any): Observable<any> {
    let httpHeaders = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = { headers: httpHeaders }
    return this.http.delete<any>(`${this.Url1}/user/favourite/deleteMovie/${movieId}`, requestOptions);
  }

  // getMovieVideo
  getMovieVideo(data: any): Observable<any> {
    return this.http.get(`${this.BASE_URL}/movie/${data}/videos?api_key=${this.key}`)
  }

  // getMovieCast
  getMovieCast(data: any): Observable<any> {
    return this.http.get(`${this.BASE_URL}/movie/${data}/credits?api_key=${this.key}`)
  }

  sendPaymentEmail(email:any):Observable<any>{
    let httpHeaders = new HttpHeaders({
      'Authorization' : 'Bearer ' + localStorage.getItem('jwt')
    });
    let requestOptions = {headers : httpHeaders}
    console.log("token pay :"+localStorage.getItem('jwt'));
    return this.http.post(this.Url1+"/user/mail/"+email,"s");

    }
}



