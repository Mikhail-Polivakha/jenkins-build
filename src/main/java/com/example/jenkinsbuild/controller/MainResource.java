package com.example.jenkinsbuild.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainResource {

    @GetMapping(path = "/test")
    public ResponseEntity<String> test(@RequestParam("name") String name,
                                       @RequestParam("age") int age) {
        if (age < 0) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(name + ":" + age);
    }
}