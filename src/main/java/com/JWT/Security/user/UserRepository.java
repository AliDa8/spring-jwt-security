package com.JWT.Security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// is an interface provided by Spring that already includes a lot of common database operations (CRUD).
public interface UserRepository extends JpaRepository<User, Integer>
{

    Optional<User> findByEmail(String email);


}
