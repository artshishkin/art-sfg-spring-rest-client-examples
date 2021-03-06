package com.artarkatesoft.artsfgspringrestclientexamples.controllers;

import com.artarkatesoft.api.domain.UserData;
import com.artarkatesoft.artsfgspringrestclientexamples.services.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@WebFluxTest(UserController.class)
class UserControllerWebFluxTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    ApiService apiService;

    @Captor
    ArgumentCaptor<Mono<Integer>> monoCaptor;

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
    void testPost_withMapperFromJackson2JsonDecoder() throws IOException {
        //given
        Integer limit = 3;
        Resource resource = new ClassPathResource("/three_persons.json");

        Jackson2JsonDecoder jackson2JsonDecoder = new Jackson2JsonDecoder();
        ObjectMapper objectMapper;
        objectMapper = jackson2JsonDecoder.getObjectMapper();

        UserData userData = objectMapper.readValue(resource.getFile(), UserData.class);
        given(apiService.getUsers(any(Integer.class))).willReturn(userData.getData());

        //when
        webTestClient.post().uri("/users")
                .body(BodyInserters.fromFormData("limit", limit.toString()))
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(content -> assertAll(
                        () -> assertThat(content).contains("<h1>User Information</h1>"),
                        () -> assertThat(content).contains("<td>Molly</td>"),
                        () -> assertThat(content).contains("Molly 2"),
                        () -> assertThat(content).contains("Molly 3"),
                        () -> assertThat(content).contains("2016-09-29 01:39:53.000000")
                ));
        then(apiService).should().getUsers(eq(limit));
    }

    @Test
    void testPostReactive_withMapperFromJackson2JsonDecoder() throws IOException {
        //given
        Integer limit = 3;
        Resource resource = new ClassPathResource("/three_persons.json");

        Jackson2JsonDecoder jackson2JsonDecoder = new Jackson2JsonDecoder();
        ObjectMapper objectMapper;
        objectMapper = jackson2JsonDecoder.getObjectMapper();

        UserData userData = objectMapper.readValue(resource.getFile(), UserData.class);
        given(apiService.getUsers(any(Mono.class))).willReturn(Flux.fromIterable(userData.getData()));

        //when
        webTestClient.post().uri("/users/reactive")
                .body(BodyInserters.fromFormData("limit", limit.toString()))
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(content -> assertAll(
                        () -> assertThat(content).contains("<h1>User Information</h1>"),
                        () -> assertThat(content).contains("<td>Molly</td>"),
                        () -> assertThat(content).contains("Molly 2"),
                        () -> assertThat(content).contains("Molly 3"),
                        () -> assertThat(content).contains("2016-09-29 01:39:53.000000")
                ));
//        then(apiService).should().getUsers(eq(Mono.just(limit)));
        then(apiService).should().getUsers(monoCaptor.capture());
        Mono<Integer> limitMonoCaptorVal = monoCaptor.getValue();
        StepVerifier.create(limitMonoCaptorVal)
                .expectNext(limit)
                .verifyComplete();
    }
}