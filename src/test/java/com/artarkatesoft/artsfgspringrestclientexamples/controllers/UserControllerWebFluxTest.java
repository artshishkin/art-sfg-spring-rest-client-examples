package com.artarkatesoft.artsfgspringrestclientexamples.controllers;

import com.artarkatesoft.api.domain.UserData;
import com.artarkatesoft.artsfgspringrestclientexamples.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testPost() throws IOException {
        //given
        Integer limit = 3;
        Resource resource = new ClassPathResource("/three_persons.json");

        Jackson2JsonDecoder jackson2JsonDecoder = new Jackson2JsonDecoder();
        ObjectMapper objectMapper;
        objectMapper = jackson2JsonDecoder.getObjectMapper();

        UserData userData = objectMapper.readValue(resource.getFile(), UserData.class);
        given(apiService.getUsers(any())).willReturn(userData.getData());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("limit", "3");


        //when
        webTestClient.post().uri("/users")
                .body(BodyInserters.fromFormData("limit", limit.toString()))
//                .body(BodyInserters.fromFormData(formData))
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(content -> assertThat(content).contains("<h1>User Information</h1>"));
        then(apiService).should().getUsers(eq(limit));
    }
}