package com.artarkatesoft.artsfgspringrestclientexamples.services;

import com.artarkatesoft.api.domain.User;
import com.artarkatesoft.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        String query = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("limit", limit).toUriString();
        UserData userData = restTemplate
                .getForObject(query, UserData.class);
        return userData.getData();
    }
}
