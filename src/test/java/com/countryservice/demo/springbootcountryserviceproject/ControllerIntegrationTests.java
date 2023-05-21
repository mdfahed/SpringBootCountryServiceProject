package com.countryservice.demo.springbootcountryserviceproject;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {
    @Order(1)
    @Test
    public void getAllCountriesIntegrationTest() throws JSONException {
        String expected = "[{\"id\":1,\"countryName\":\"India\",\"countryCapital\":\"Delhi\"},{\"id\":2,\"countryName\":\"USA\",\"countryCapital\":\"Washington\"}]";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Order(2)
    @Test
    public void getcountryByIdIntegrationTest() throws JSONException {
        String expected = "{\"id\":1,\"countryName\":\"India\",\"countryCapital\":\"Delhi\"}";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries/1", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Order(3)
    @Test
    public void getcountryByNameIntegrationTest() throws JSONException {
        String expected = "{\"id\":1,\"countryName\":\"India\",\"countryCapital\":\"Delhi\"}";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries/countryname?name=India", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Order(4)
    @Test
    public void addNewCountryIntegrationTest() throws JSONException {
        Country country = new Country(3, "Italy", "Rome");
        String expected = "{\"id\":3,\"countryName\":\"Italy\",\"countryCapital\":\"Rome\"}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country, headers);
        ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:8080/addcountry", request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Order(5)
    @Test
    public void updateCountryIntegrationTest() throws JSONException {
        Country country = new Country(3, "Germany", "Berlin");
        String expected = "{\"id\":3,\"countryName\":\"Germany\",\"countryCapital\":\"Berlin\"}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country, headers);
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:8080/updatecountry/3", HttpMethod.PUT, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Order(6)
    @Test
    public void deleteCountryIntegrationTest() throws JSONException {
        Country country = new Country(3, "Germany", "Berlin");
        String expected = "{\"id\":3,\"countryName\":\"Germany\",\"countryCapital\":\"Berlin\"}";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country, headers);
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:8080/deletecountry/3", HttpMethod.DELETE, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


}
