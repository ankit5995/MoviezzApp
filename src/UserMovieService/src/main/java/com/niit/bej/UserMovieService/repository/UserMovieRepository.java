package com.niit.bej.UserMovieService.repository;

import com.niit.bej.UserMovieService.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRepository extends MongoRepository<User, String> {
}
