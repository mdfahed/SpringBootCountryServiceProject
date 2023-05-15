package com.countryservice.demo.springbootcountryserviceproject;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import com.countryservice.demo.springbootcountryserviceproject.controllers.CountryController;
import com.countryservice.demo.springbootcountryserviceproject.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryController countryController;

    List<Country> myCountries;
    Country country;

    @Test
    @Order(1)
    public void test_getAllCountries() {
        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        when(countryService.getAllCountries()).thenReturn(myCountries); //Mocking
        ResponseEntity<List<Country>> res = countryController.getCountries();
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(2, res.getBody().size());

    }

    @Test
    @Order(2)
    public void test_getCountryById() {
        country = new Country(2, "USA", "Washington");
        int id = 2;
        when(countryService.getCountryById(id)).thenReturn(country);
        ResponseEntity<Country> res = countryController.getCountryById(id);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(id, res.getBody().getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        ResponseEntity<Country> res = countryController.getCountryByName(countryName);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(countryName, res.getBody().getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        country = new Country(3, "Germany", "Berlin");
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> res = countryController.addCountry(country);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(country, res.getBody());
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        country = new Country(3, "Japan", "Tokyo");
        int id = 3;
        when(countryService.getCountryById(id)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> res = countryController.updateCountry(id, country);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(id, res.getBody().getId());
        assertEquals("Japan", res.getBody().getCountryName());
        assertEquals("Tokyo", res.getBody().getCountryCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        country = new Country(3, "Japan", "Tokyo");
        int id = 3;
        when(countryService.getCountryById(id)).thenReturn(country);
        ResponseEntity<Country> res = countryController.deleteCountry(id);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }


}
