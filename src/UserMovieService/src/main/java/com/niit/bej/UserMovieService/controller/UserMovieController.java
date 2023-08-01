package com.niit.bej.UserMovieService.controller;
import com.niit.bej.UserMovieService.domain.User;
import com.niit.bej.UserMovieService.exception.MovieAlreadyExistException;
import com.niit.bej.UserMovieService.exception.MovieDoesNotExistsException;
import com.niit.bej.UserMovieService.exception.UserAlreadyExistException;
import com.niit.bej.UserMovieService.exception.UserNotFoundException;
import com.niit.bej.UserMovieService.repository.UserMovieRepository;
import com.niit.bej.UserMovieService.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userMovie")

public class UserMovieController {
    private final UserMovieService userMovieService;
    private final UserMovieRepository userMovieRepository;

    @Autowired
    public UserMovieController(UserMovieService userMovieService, UserMovieRepository userMovieRepository) {
        this.userMovieService = userMovieService;
        this.userMovieRepository = userMovieRepository;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registerUser = userMovieService.registerUser(user);
            return new ResponseEntity<>(registerUser, HttpStatus.OK);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/user/addToFavourites/{movieId}")
    public ResponseEntity<?> addToFavourites(@PathVariable String movieId, HttpServletRequest httpServletRequest) {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        User user = userMovieRepository.findById(emailId).get();
        try {
            String movieObj = userMovieService.addMovieToFavourites(movieId, user);

            System.out.println("fav list "+movieObj);

            return new ResponseEntity<>(movieObj, HttpStatus.OK);
        } catch (MovieAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("user/favourite/deleteMovie/{movieId}")
    public  ResponseEntity<?> deleteFromFavourites(@PathVariable String movieId, HttpServletRequest httpServletRequest){
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        User user = userMovieRepository.findById(emailId).get();
        try {
            User user1 = userMovieService.deleteMovieFromFavourites(movieId, user);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (MovieDoesNotExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/user/addToWatchList/{movieId}")
    public ResponseEntity<?> addToWatchList(@PathVariable String movieId, HttpServletRequest httpServletRequest) {
        System.out.println("msg ");
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        User user = userMovieRepository.findById(emailId).get();
        try {
            String movieObj = userMovieService.addMovieToWishList(movieId, user);
            System.out.println("m obj "+movieObj);
            return new ResponseEntity<>(movieObj, HttpStatus.OK);
        } catch (MovieAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("user/watchlist/deleteMovie/{movieId}")
    public  ResponseEntity<?> deleteFromWatchList(@PathVariable String movieId, HttpServletRequest httpServletRequest){
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        User user = userMovieRepository.findById(emailId).get();
        try {
            User user1 = userMovieService.deleteMovieFromWishList(movieId, user);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (MovieDoesNotExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/userData")
    public  ResponseEntity<?> getUserData(HttpServletRequest httpServletRequest){
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        User user = userMovieRepository.findById(emailId).get();
        try {
           User user1= userMovieService.getUser(emailId);

            return new ResponseEntity<>(user1,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
