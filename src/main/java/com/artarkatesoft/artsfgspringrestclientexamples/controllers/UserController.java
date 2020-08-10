package com.artarkatesoft.artsfgspringrestclientexamples.controllers;

import com.artarkatesoft.artsfgspringrestclientexamples.services.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final ApiService apiService;

    @PostMapping
    public Mono<String> getAllUsers(Model model, ServerWebExchange serverWebExchange) {
        Mono<Integer> limitMono = serverWebExchange.getFormData()
                .map(multiValueMap -> multiValueMap.getFirst("limit"))
                .log()
                .map(Integer::valueOf);
        return limitMono.map(apiService::getUsers)
                .doOnNext(users -> model.addAttribute("users", users))
                .then(Mono.just("users"));
    }
}
