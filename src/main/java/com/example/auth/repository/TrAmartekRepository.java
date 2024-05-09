package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.model.TrAmartek;

@Repository
public interface TrAmartekRepository extends JpaRepository<TrAmartek, Integer> {

}
