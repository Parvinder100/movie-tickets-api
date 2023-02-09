package com.github.my.movie.tickets.integration;

import com.github.my.movie.tickets.dto.TransactionRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.After;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovieTicketsIntegratoinTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value="${local.server.port}")
    private int port;

    private String url;

    @Before
    public void setup() {
        url = "http://localhost:" + port;
    }

    @After
    public void teardown() {
    }

    @Test
    public void testGetMovieCoste_scenario_1() throws IOException {
        TransactionRequest transactionRequest = prepareTransactionRequest("data/request/request_1.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_1.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    @Test
    public void testGetMovieCoste_scenario_2() throws IOException {
        TransactionRequest transactionRequest = prepareTransactionRequest("data/request/request_2.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_2.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    @Test
    public void testGetMovieCoste_scenario_3() throws IOException {
        TransactionRequest transactionRequest = prepareTransactionRequest("data/request/request_3.json");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(url + "/getMovieCost", transactionRequest, String.class);
        String expectedJsonResponse = getJsonStringFromFile("data/response/response_3.json");
        Assert.assertEquals(expectedJsonResponse, stringResponseEntity.getBody());
    }

    private static TransactionRequest prepareTransactionRequest(String path) throws IOException {
        String jsonStringRequest = getJsonStringFromFile(path);
        Gson gson = new Gson();
        TransactionRequest transactionRequest = gson.fromJson(jsonStringRequest, TransactionRequest.class);
        return transactionRequest;
    }

    private static String getJsonStringFromFile(String path) throws IOException {
        String jsonString = StreamUtils.copyToString((new ClassPathResource(path)).getInputStream(), Charset.defaultCharset());
        jsonString = jsonString.replaceAll("\\r\\n", "").replaceAll(" ", "");
        jsonString = StringEscapeUtils.unescapeJava(jsonString);
        return jsonString;
    }
}