package com.artarkatesoft.artsfgspringrestclientexamples.services;

import com.artarkatesoft.api.domain.User;
import com.artarkatesoft.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final WebClient webClient;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        webClient = WebClient.builder().baseUrl(this.apiUrl).build();
    }

    @Override
    public List<User> getUsers(Integer limit) {
        String query = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("limit", limit).toUriString();
        UserData userData = restTemplate
                .getForObject(query, UserData.class);
        return userData.getData();
    }

    @Override
    public Flux<User> getUsers(Mono<Integer> limitMono) {
        return limitMono
                .log(" --- -getUsers ------------------ ")
                .flatMap(limit -> WebClient.create(UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("limit", limit).toUriString()).get()
                        .retrieve()
                        .bodyToMono(UserData.class))
                .log(" --- -getUsers -------UserData----------- ")
                .doOnError(ex -> ex.printStackTrace())
                .flatMapMany(userData -> Flux.fromIterable(userData.getData()));
    }
}
