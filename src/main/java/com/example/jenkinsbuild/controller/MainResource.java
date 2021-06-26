package com.example.jenkinsbuild.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
public class MainResource {

    @GetMapping(path = "/test")
    public ResponseEntity<String> test(@RequestParam("name") String name,
                                       @RequestParam("age") int age) {
        if (age < 0) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(name + ":" + age);
    }

    @PostMapping
    public void create(@RequestBody Person person, HttpServletResponse response) {
        if (requestIsNotValid(person)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    private boolean requestIsNotValid(Person person) {
        return isNull(person) || isNull(person.getAge()) || isNull(person.getName());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Person {
        private String name;
        private Integer age;
    }
}