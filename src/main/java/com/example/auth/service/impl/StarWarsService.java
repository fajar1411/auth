package com.example.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.dto.PeopleResponse;
import com.example.auth.service.PeopleService;
@Service
public class StarWarsService {
    @Autowired
    private PeopleService peopleService;

    public PeopleResponse fetchPeople() {
        return peopleService.getPeople();
    }
}
