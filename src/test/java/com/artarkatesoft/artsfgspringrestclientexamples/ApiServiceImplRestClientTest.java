package com.artarkatesoft.artsfgspringrestclientexamples;

import com.artarkatesoft.api.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Slf4j
@RestClientTest(ApiServiceImpl.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class ApiServiceImplRestClientTest {

    @Autowired
    ApiService apiService;

    @Autowired
    MockRestServiceServer mockServer;

    @Test
    void getUsers_onePerson() {
        //given
        Integer limit = 1;
        mockServer.expect(once(), requestTo("http://private-211c3e-apifaketory.apiary-mock.com/api/user?limit=" + limit)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ClassPathResource("/one_person.json"), MediaType.APPLICATION_JSON));

        //when
        List<User> users = apiService.getUsers(limit);
        //then
        assertThat(users).hasSize(1);
        User actualUser = users.get(0);
        assertThat(actualUser).hasFieldOrPropertyWithValue("gender", "female");
        log.info("User is " + actualUser);
        mockServer.verify();
    }

    @Test
    void getUsers_threePersons() {
        //given
        Integer limit = 3;
        mockServer.expect(once(), requestTo("http://private-211c3e-apifaketory.apiary-mock.com/api/user?limit=" + limit)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ClassPathResource("/three_persons.json"), MediaType.APPLICATION_JSON));

        //when
        List<User> users = apiService.getUsers(limit);
        //then
        assertThat(users).hasSize(3);
        User actualUser = users.get(2);
        assertThat(actualUser).hasFieldOrPropertyWithValue("gender", "female");
        assertThat(actualUser.getName()).hasFieldOrPropertyWithValue("first", "Molly 3");
        assertThat(actualUser.getName()).hasFieldOrPropertyWithValue("last", "Robel 3");
        log.info("User is " + actualUser);
        mockServer.verify();
    }
}