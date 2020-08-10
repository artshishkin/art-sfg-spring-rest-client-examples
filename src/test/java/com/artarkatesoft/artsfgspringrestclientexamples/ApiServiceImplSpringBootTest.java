package com.artarkatesoft.artsfgspringrestclientexamples;

import com.artarkatesoft.api.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ApiServiceImplSpringBootTest {

    @Autowired
    ApiService apiService;

    @Test
    void getUsers() {
        List<User> users = apiService.getUsers(1);
        assertThat(users).hasSize(1);
        User actualUser = users.get(0);
        assertThat(actualUser).hasFieldOrPropertyWithValue("gender", "female");
        log.info("User is " + actualUser);
    }
}