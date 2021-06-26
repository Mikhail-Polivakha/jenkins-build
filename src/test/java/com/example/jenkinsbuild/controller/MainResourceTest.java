package com.example.jenkinsbuild.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
class MainResourceTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = restTemplateBuilder.build();
    }

    @Value("${server.port}")
    private int serverPort;

    @Test
    void testWithoutAge() {
        String url = UriComponentsBuilder
                .fromUri(URI.create(String.format("http://localhost:%d/test", serverPort)))
                .queryParam("name", "Alex").toUriString();
        Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(url, String.class));
    }

    @Test
    void testInvalidAge() {
        String url = UriComponentsBuilder
                .fromUri(URI.create(String.format("http://localhost:%d/test", serverPort)))
                .queryParam("name", "Alex")
                .queryParam("age", -1).toUriString();
        Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(url, String.class));
    }

    @Test
    void testAllValid() {
        String url = UriComponentsBuilder
                .fromUri(URI.create(String.format("http://localhost:%d/test", serverPort)))
                .queryParam("name", "Alex")
                .queryParam("age", 11).toUriString();

        Assertions.assertEquals(restTemplate.getForEntity(url, String.class).getBody(), "Alex:11");
    }

    @Test
    void create() {
        String url = UriComponentsBuilder.fromUri(URI.create(String.format("http://localhost:%d", serverPort))).toUriString();
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate.postForEntity(url, new MainResource.Person("Mikhail", 19), Void.class).getStatusCode());
    }
}