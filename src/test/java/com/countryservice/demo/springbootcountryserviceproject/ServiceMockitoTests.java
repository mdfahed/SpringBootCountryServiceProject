package com.countryservice.demo.springbootcountryserviceproject;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import com.countryservice.demo.springbootcountryserviceproject.repositories.CountryRepository;
import com.countryservice.demo.springbootcountryserviceproject.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {

    @Mock
    CountryRepository countryRepository;
    @InjectMocks
    CountryService countryService;
    List<Country> myCountries;
    Country country;

    @Test
    @Order(1)
    public void test_getAllCountries() {

        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        when(countryRepository.findAll()).thenReturn(myCountries); //Mocking
        assertEquals(2, countryService.getAllCountries().size());

    }

    @Test
    @Order(2)
    public void test_getCountryById() {
        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        int id = 1;
        when(countryRepository.findAll()).thenReturn(myCountries); //Mocking
        assertEquals(id, countryService.getCountryById(id).getId());

    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        String countryName = "India";
        when(countryRepository.findAll()).thenReturn(myCountries); //Mocking
        assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        country = new Country(3, "Germany", "Berlin");
        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.addCountry(country));


    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        country = new Country(3, "Germany", "Berlin");
        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.updateCountry(country));

    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        country = new Country(3, "Germany", "Berlin");
        countryService.deleteCountry(country);
        verify(countryRepository, times(1)).delete(country);

    }


}
