package com.fitness.tracker.repository;

import com.fitness.tracker.model.User; //imports the user details/model

import java.util.List;
import java.util.Optional; //Handles null values

import org.springframework.data.jpa.repository.JpaRepository;  // JpaRepository interface
import org.springframework.stereotype.Repository;  //  Repository annotation

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // can add custom method queries here..
    Optional<User> findByUsername(String username);  //find by username
    Optional<User> findByEmail(String email);
    
    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);

}

/**
 *Jpa repository by default has other methods like
 *save()
 *findbyId()
 *findAll()
 *deletebyID()
 * So findbyUsername method made is optional and can add other custom database queries above
 * JPA creates the queries based on method names.  
 */
