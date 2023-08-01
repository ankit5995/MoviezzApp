package com.niit.bej.UserMovieService.service;

import com.niit.bej.UserMovieService.config.MovieDTO;
import com.niit.bej.UserMovieService.domain.User;
import com.niit.bej.UserMovieService.exception.MovieAlreadyExistException;
import com.niit.bej.UserMovieService.exception.MovieDoesNotExistsException;
import com.niit.bej.UserMovieService.exception.UserAlreadyExistException;
import com.niit.bej.UserMovieService.exception.UserNotFoundException;
import com.niit.bej.UserMovieService.proxy.UserProxy;
import com.niit.bej.UserMovieService.repository.UserMovieRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMovieServiceImpl implements UserMovieService {
    private final UserMovieRepository userMovieRepository;

    private final UserProxy userProxy;

    private final RabbitTemplate rabbitTemplate;
    private  final DirectExchange directExchange;

    @Autowired
    public UserMovieServiceImpl(UserMovieRepository userMovieRepository, UserProxy userProxy, RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.userMovieRepository = userMovieRepository;
        this.userProxy = userProxy;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        System.out.println("user data  :"+user);
        if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistException("User is Already Exist!");
        } else {
            User savedUser = userMovieRepository.save(user);
            if (!savedUser.getUserName().isEmpty()){
                userProxy.registerProxyUser(savedUser);

                if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("registered ", true);
                    jsonObject.put("emailId",user.getEmailId());

                    MovieDTO movieDTO = new MovieDTO();
                    movieDTO.setJsonObject(jsonObject);
                    rabbitTemplate.convertAndSend(directExchange.getName(),"movie-routing", movieDTO);
                }
            }
            return savedUser;
        }
    }

    @Override
    public String addMovieToFavourites(String movieId, User user) throws MovieAlreadyExistException {
        if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
            User loggedInUser = userMovieRepository.findById(user.getEmailId()).get();
            List<String> userFavouriteMovieList = new ArrayList<>();
            userFavouriteMovieList = loggedInUser.getFavouriteMovies();
            if (loggedInUser.getFavouriteMovies() == null) {
                userFavouriteMovieList = new ArrayList<>();
                userFavouriteMovieList.add(movieId);
                loggedInUser.setFavouriteMovies(userFavouriteMovieList);
            }
            else{
                boolean flag = false;
                for (String movieid : userFavouriteMovieList) {  //[12454,874465,116544]
                    if (movieid.equals(movieId)) {
                        flag = true;
                        break;
                    }
                }

                if (flag)
                    throw new MovieAlreadyExistException("movie is already Exist!");
                else {
                    userFavouriteMovieList.add(movieId);

                }
            }
            user.setFavouriteMovies(userFavouriteMovieList);
            userMovieRepository.save(user);
        }
        return movieId;
    }

    @Override
    public User deleteMovieFromFavourites(String movieId, User user) throws MovieDoesNotExistsException {
        if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
            User loggedInUser = userMovieRepository.findById(user.getEmailId()).get();

            List<String> userFavouriteMovieList = loggedInUser.getFavouriteMovies();
            if (userFavouriteMovieList.size() == 0) {
                throw new MovieDoesNotExistsException("You have no favourite movies");
            }
            else {
                int index = 0;
                boolean flag = false;

                for (String movie_id : userFavouriteMovieList){
                    if (movie_id.equals(movieId)){
                        flag = true;
                        break;
                    }
                     index +=1;
                }
                userFavouriteMovieList.remove(movieId);
                user.setFavouriteMovies(userFavouriteMovieList);
                userMovieRepository.save(user);
            }
        }
        return user;
    }

    @Override
    public String addMovieToWishList(String movieId, User user) throws MovieAlreadyExistException {
        if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
            User loggedInUser = userMovieRepository.findById(user.getEmailId()).get();
            List<String> userWatchMovieList = new ArrayList<>();
            userWatchMovieList = loggedInUser.getWatchList();
            if (loggedInUser.getWatchList() == null) {
                userWatchMovieList = new ArrayList<>();
                userWatchMovieList.add(movieId);
                loggedInUser.setWatchList(userWatchMovieList);
            }
            else{
                boolean flag = false;
                for (String movieid : userWatchMovieList) {  //[12454,874465,116544]
                    if (movieid.equals(movieId)) {
                        flag = true;
                        break;
                    }
                }

                if (flag)
                    throw new MovieAlreadyExistException("movie is already Exist!");
                else {
                    userWatchMovieList.add(movieId);
                }
            }
            user.setWatchList(userWatchMovieList);
            userMovieRepository.save(user);
        }
        return movieId;
    }

    @Override
    public User deleteMovieFromWishList(String movieId, User user) throws MovieDoesNotExistsException {
        if (userMovieRepository.findById(user.getEmailId()).isPresent()) {
            User loggedInUser = userMovieRepository.findById(user.getEmailId()).get();

            List<String> userWatchMovieList = loggedInUser.getWatchList();
            if (userWatchMovieList.size() == 0) {
                throw new MovieDoesNotExistsException("You have no favourite movies");
            }
            else {
                int index = 0;
                boolean flag = false;

                for (String movie_id : userWatchMovieList){
                    if (movie_id.equals(movieId)){
                        flag = true;
                        break;
                    }
                    index +=1;
                }
                userWatchMovieList.remove(movieId);
                user.setWatchList(userWatchMovieList);
                userMovieRepository.save(user);
            }
        }
        return user;
    }

    @Override
    public User getUser(String emailId) throws UserNotFoundException {
        return userMovieRepository.findById(emailId).get();
    }
}
