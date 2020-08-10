package com.artarkatesoft.artsfgspringrestclientexamples.examples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        String customer_url = savedCustomerNode.get("customer_url").asText();
        url = fromHttpUrl(BASE_URL).replacePath(customer_url).toUriString();
        String retrievedCustomer = restTemplate.getForObject(url, String.class);
        System.out.println(retrievedCustomer);
        assertThat(retrievedCustomer).isNotEmpty().contains("Art", "Shyshkin", "lastname", "firstname");
    }

    @Test
    void updateCustomer_usingPut() {
        //given
        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );
        //when update customer by id
        String customerUrl = savedCustomerNode.get("customer_url").asText();

        url = fromHttpUrl(BASE_URL).replacePath(customerUrl).toUriString();

        String customerModifiedJson = "{\"firstname\": \"Art 2\",\"lastname\": \"Shyshkin 2\"}";
        restTemplate.put(url, customerModifiedJson);

        //then find customer by id
        String retrievedCustomer = restTemplate.getForObject(url, String.class);
        System.out.println(retrievedCustomer);
        assertThat(retrievedCustomer).isNotEmpty().contains("Art 2", "Shyshkin 2", "lastname", "firstname");
    }

    @Test
    void updateCustomer_usingExchangePut() {
        //given
        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );
        //when update customer by id
        String customerUrl = savedCustomerNode.get("customer_url").asText();

        url = fromHttpUrl(BASE_URL).replacePath(customerUrl).toUriString();

        String customerModifiedJson = "{\"firstname\": \"Art 2\",\"lastname\": \"Shyshkin 2\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(customerModifiedJson);
        ResponseEntity<String> stringResponseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        //then
        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(OK);

        String updatedCustomerString = stringResponseEntity.getBody();
        System.out.println(updatedCustomerString);
        assertThat(updatedCustomerString)
                .isNotEmpty()
                .contains("Art 2", "Shyshkin 2", "lastname", "firstname")
                .doesNotContain("/shop/customers/", "customer_url");
    }

    @Test
    @Disabled("The standard JDK HTTP library does not support HTTP PATCH")
//    org.springframework.web.client.ResourceAccessException: I/O error on PATCH request for "https://api.predic8.de:443/shop/customers/386": Invalid HTTP method: PATCH; nested exception is java.net.ProtocolException: Invalid HTTP method: PATCH
    void updateCustomer_usingExchangePatch() {
        //given
        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );
        //when update customer by id
        String customerUrl = savedCustomerNode.get("customer_url").asText();

        url = fromHttpUrl(BASE_URL).replacePath(customerUrl).toUriString();

        String customerModifiedJson = "{\"firstname\": \"Nazar\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(customerModifiedJson);
        String finalUrl = url;
        assertThrows(ResourceAccessException.class, () -> restTemplate.exchange(finalUrl, HttpMethod.PATCH, requestEntity, String.class));
    }

    @Test
    @Disabled("The standard JDK HTTP library does not support HTTP PATCH")
//    org.springframework.web.client.ResourceAccessException: I/O error on PATCH request for "https://api.predic8.de:443/shop/customers/386": Invalid HTTP method: PATCH; nested exception is java.net.ProtocolException: Invalid HTTP method: PATCH
    void updateCustomer_usingPatch() {
        //given
        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );
        //when update customer by id
        String customerUrl = savedCustomerNode.get("customer_url").asText();

        url = fromHttpUrl(BASE_URL).replacePath(customerUrl).toUriString();

        String customerModifiedJson = "{\"firstname\": \"Nazar\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(customerModifiedJson);
        String finalUrl = url;
        assertThrows(ResourceAccessException.class, () -> {
            String modifiedCustomerString = restTemplate.patchForObject(finalUrl, customerModifiedJson, String.class);
            System.out.println(modifiedCustomerString);
        });
    }

    @Test
    void updateCustomer_usingExchangePatch_apache() {
        //given
        RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        int TIMEOUT = 500;
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);
        restTemplate.setRequestFactory(requestFactory);

        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );
        //when update customer by id
        String customerUrl = savedCustomerNode.get("customer_url").asText();

        url = fromHttpUrl(BASE_URL).replacePath(customerUrl).toUriString();

        String customerModifiedJson = "{\"firstname\": \"Nazar\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(customerModifiedJson);
        String finalUrl = url;
        ResponseEntity<String> stringResponseEntity = restTemplate.exchange(finalUrl, HttpMethod.PATCH, requestEntity, String.class);

        //then
        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(OK);

        String updatedCustomerString = stringResponseEntity.getBody();
        System.out.println(updatedCustomerString);
        assertThat(updatedCustomerString)
                .isNotEmpty()
                .contains("Nazar", "Shyshkin", "lastname", "firstname")
                .doesNotContain("/shop/customers/", "Art", "customer_url");
    }

    @Test
    void updateCustomer_usingPatch_apache() {
        //given
        RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        int TIMEOUT = 500;
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);
        restTemplate.setRequestFactory(requestFactory);

        String customerJson = "{\"firstname\": \"Art\",\"lastname\": \"Shyshkin\"}";
        String url = fromHttpUrl(BASE_URL).path("/customers/").toUriString();
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, customerJson, JsonNode.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        JsonNode savedCustomerNode = responseEntity.getBody();
        assertThat(savedCustomerNode).isNotNull();
        assertAll(
                () -> assertThat(savedCustomerNode.get("firstname").asText()).isEqualTo("Art"),
                () -> assertThat(savedCustomerNode.get("lastname").asText()).isEqualTo("Shyshkin"),
                () -> assertThat(savedCustomerNode.get("customer_url").asText()).startsWith("/shop/customers/")
        );

        System.out.println("----------------");
        System.out.println("----------------");
        System.out.println("----------------");
        System.out.println("----------------");
        //when update customer by id
        String customerUrl = savedCustomerNode.get("customer_url").asText();

        url = fromHttpUrl(BASE_URL).replacePath(customerUrl).toUriString();

        String customerModifiedJson = "{\"firstname\": \"Nazar\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(customerModifiedJson);
        String finalUrl = url;
        String updatedCustomerString = restTemplate.patchForObject(finalUrl, customerModifiedJson, String.class);

        //then
        System.out.println(updatedCustomerString);
        assertThat(updatedCustomerString)
                .isNotEmpty()
                .contains("Nazar", "Shyshkin", "lastname", "firstname")
                .doesNotContain("/shop/customers/", "Art", "customer_url");
    }
}
