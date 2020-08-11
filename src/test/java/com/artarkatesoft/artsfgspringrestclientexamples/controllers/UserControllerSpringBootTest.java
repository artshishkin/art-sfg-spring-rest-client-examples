package com.artarkatesoft.artsfgspringrestclientexamples.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class UserControllerSpringBootTest {

    @Autowired
    ApplicationContext applicationContext;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    void index() {
        //when
        webTestClient.get().uri("/")
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(content -> assertThat(content).contains("<h1>Home Page</h1>"));
    }

    @Test
    void testPost() {
        //given
        Integer limit = 2;
        //when
        webTestClient.post().uri("/users")
                .body(BodyInserters.fromFormData("limit", limit.toString()))
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(content -> assertThat(content).contains("<h1>User Information</h1>"));
    }

    @Test
    void testPost_reactive() {
        //given
        Integer limit = 2;
        //when
        webTestClient.post().uri("/users/reactive")
                .body(BodyInserters.fromFormData("limit", limit.toString()))
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(content -> assertAll(
                        () -> assertThat(content).contains("<h1>User Information</h1>"),
                        () -> assertThat(content).contains("male</td>")
                ));
    }
}