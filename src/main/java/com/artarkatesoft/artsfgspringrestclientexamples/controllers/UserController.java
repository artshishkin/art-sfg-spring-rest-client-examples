package com.artarkatesoft.artsfgspringrestclientexamples.controllers;

import com.artarkatesoft.api.domain.User;
import com.artarkatesoft.artsfgspringrestclientexamples.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final ApiService apiService;

    @PostMapping
    public String getAllUsers(Model model, ServerWebExchange serverWebExchange) {
        Integer limit = serverWebExchange.getAttribute("limit");
        if (limit == null || limit < 1) limit = 1;
        List<User> requestedUsers = apiService.getUsers(limit);
        model.addAttribute("users", requestedUsers);
        return "users";
    }
}
