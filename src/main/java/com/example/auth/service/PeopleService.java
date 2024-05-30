package com.example.auth.service;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.auth.config.FeignConfig;
import com.example.auth.dto.PeopleResponse;

@FeignClient(name = "starWarsService", url ="https://swapi.dev", configuration = FeignConfig.class)
public interface PeopleService {

    @GetMapping("/api/people")
    public PeopleResponse getPeople();
}
