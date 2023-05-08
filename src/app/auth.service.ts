import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn: boolean = false;

  constructor(private http: HttpClient, private route: Router) { }
  Url1: string = "http://localhost:9002/userMovie"
  Url2: string = "http://localhost:9002/userAuth"




  register(userData: any) {
    return this.http.post(this.Url1 + "/registerUser", userData)
  }


  loginCheck(logindata: any) {
    return this.http.post(this.Url2 + "/login", logindata);
  }


  public isLoggedin() {
    let JwtToken = localStorage.getItem("tokenGenerated");
    if (JwtToken == "" || JwtToken == undefined || JwtToken == null) {
      return false;
    } else {
      return true;
    }
  }
  public logOutUser() {
    localStorage.clear();
    this.isLoggedIn = false;
  }

}
