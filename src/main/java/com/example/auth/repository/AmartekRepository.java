package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.model.Amartek;
@Repository
public interface AmartekRepository extends JpaRepository<Amartek, Integer> {

}
