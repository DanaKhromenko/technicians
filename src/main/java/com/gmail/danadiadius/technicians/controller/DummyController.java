package com.gmail.danadiadius.technicians.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/")
    public String greeting() {
        return "This page will show you the best IT technicians... soon :-)";
    }
}
