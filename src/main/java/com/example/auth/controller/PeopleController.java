package com.example.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.PeopleResponse;
import com.example.auth.handler.CustomResponse;
import com.example.auth.service.impl.StarWarsService;

// @RestController
@Controller
@RequestMapping("api")
public class PeopleController {
    @Autowired
    private StarWarsService starWarsService;

    @GetMapping("/people")
    public ResponseEntity<Object>  getPeople() {
        PeopleResponse peopleResponse=starWarsService.fetchPeople();
        return  CustomResponse.generate(HttpStatus.OK, "success", peopleResponse);
    }
}
