package com.artarkatesoft.artsfgspringrestclientexamples;

import com.artarkatesoft.api.domain.User;
import com.artarkatesoft.api.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;

    @Override
    public List<User> getUsers(Integer limit) {
        UserData userData = restTemplate
                .getForObject("http://private-211c3e-apifaketory.apiary-mock.com/api/user?limit=" + limit, UserData.class);
        return userData.getData();
    }
}
