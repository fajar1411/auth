package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
//   @Query(value = "SELECT new com.example.testSpring.config.MyUserDetails(a.email, u.password, r.name) FROM User u JOIN u.trAmarteks tr JOIN u.amartek a JOIN tr.role r WHERE a.email = ?1")
//     public UserDetails authenticate(String email);

    @Query(value = "SELECT u from User u where u.verificationCode = ?1")
    public User cekToken(String token);

}
