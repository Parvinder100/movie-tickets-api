package com.github.my.movie.tickets.integration;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieTicketsCostIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Value(value="${local.server.port}")
    private int port;
    private String url;
    @Before
    public void setup() {
        url = "http://localhost:" + port;
    }

    @Test
    public void testGetMovieCost_scenario_1() throws IOException, InterruptedException {
        HttpEntity<String> transactionRequest = prepareTransactionRequest("data/request/request_1.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_1.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    @Test
    public void testGetMovieCost_scenario_2() throws IOException {
        HttpEntity<String> transactionRequest = prepareTransactionRequest("data/request/request_2.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_2.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    @Test
    public void testGetMovieCost_scenario_3() throws IOException {
        HttpEntity<String> transactionRequest = prepareTransactionRequest("data/request/request_3.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_3.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    @Test
    public void testGetMovieCost_whenEmptyCustomerRequest_ReturnEmptyTicketResponse() throws IOException {
        HttpEntity<String> transactionRequest = prepareTransactionRequest("data/request/request_empty_customer_list_4.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_empty_customer_list_4.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    private static HttpEntity<String> prepareTransactionRequest(String path) throws IOException {
        String jsonStringRequest = getJsonStringFromFile(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<String>(jsonStringRequest, headers);
    }

    private static String getJsonStringFromFile(String path) throws IOException {
        String jsonString = StreamUtils.copyToString((new ClassPathResource(path)).getInputStream(), Charset.defaultCharset());
        jsonString = jsonString.replaceAll("\\r\\n", "").replaceAll(" ", "");
        jsonString = StringEscapeUtils.unescapeJava(jsonString);
        return jsonString;
    }
}