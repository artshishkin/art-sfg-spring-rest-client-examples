package com.artarkatesoft.artsfgspringrestclientexamples;

import com.artarkatesoft.api.domain.User;

import java.util.List;

public interface ApiService {
    List<User> getUsers(Integer limit);
}
