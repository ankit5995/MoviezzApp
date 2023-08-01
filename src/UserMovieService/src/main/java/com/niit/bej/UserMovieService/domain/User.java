package com.niit.bej.UserMovieService.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class User {
    @MongoId
    private String emailId;
    private String password;
    private String userName;

    private String phoneNumber;
    private String profileUrl;
    private List<String> favouriteMovies;//--> this list will be movie ids.

    private List<String> watchList;



    public User(String emailId, String password, String userName, String phoneNumber, String profileUrl, List<String> favouriteMovies, List<String> watchList) {
        this.emailId = emailId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.profileUrl = profileUrl;
        this.favouriteMovies = favouriteMovies;
        this.watchList = watchList;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public List<String> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void setFavouriteMovies(List<String> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
    }

    public List<String> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<String> watchList) {
        this.watchList = watchList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(emailId, user.emailId) && Objects.equals(password, user.password) && Objects.equals(userName, user.userName) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(profileUrl, user.profileUrl) && Objects.equals(favouriteMovies, user.favouriteMovies) && Objects.equals(watchList, user.watchList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, password, userName, phoneNumber, profileUrl, favouriteMovies, watchList);
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", favouriteMovies=" + favouriteMovies +
                ", watchList=" + watchList +
                '}';
    }
}
