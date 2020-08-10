package com.artarkatesoft.artsfgspringrestclientexamples.examples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

class RestTemplateExamplesTest {

    private final static String BASE_URL = "https://api.predic8.de:443/shop";
    private static RestTemplate restTemplate;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplateBuilder().build();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void getCategories_jsonNode() {
        JsonNode jsonNode = restTemplate.getForObject(BASE_URL + "/categories/", JsonNode.class);
        assertThat(jsonNode).isNotNull();
        JsonNode categories = jsonNode.get("categories");
        categories.forEach(System.out::println);
        List<JsonNode> jsonNodeList = StreamSupport.stream(categories.spliterator(), false).collect(Collectors.toList());
        assertThat(jsonNodeList).hasSizeGreaterThan(3);
    }

    @Test
    void getCategories_String() {
        String jsonString = restTemplate.getForObject(BASE_URL + "/categories/", String.class);
        System.out.println(jsonString);
        assertThat(jsonString).isNotEmpty();
    }

    @Test
    void getCategories_entity() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + "/categories/", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        String jsonString = responseEntity.getBody();
        System.out.println(jsonString);
        assertThat(jsonString).isNotEmpty();
    }

    @Test
    void getCustomers() {
        String url = fromHttpUrl(BASE_URL).pathSegment("customers").path("/").toUriString();
        JsonNode customersNode = restTemplate.getForObject(url, JsonNode.class);
        assertThat(customersNode).isNotNull();
        List<JsonNode> customers = StreamSupport.stream(customersNode.get("customers").spliterator(), false).collect(Collectors.toList());
        assertThat(customers).isNotNull().hasSizeGreaterThan(3);
        customers.forEach(System.out::println);
    }

    @Test
    void createCustomer() {
        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        System.out.println(savedCustomerNode);
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );
        //then find customer by id
        url = fromHttpUrl(BASE_URL).replacePath("/shop/customers/382").toUriString();
        String retrievedCustomer = restTemplate.getForObject(url, String.class);
        System.out.println(retrievedCustomer);
        assertThat(retrievedCustomer).isNotEmpty().contains("Art", "Shyshkin", "lastname", "firstname");
    }
}
