package com.countryservice.demo.springbootcountryserviceproject;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import com.countryservice.demo.springbootcountryserviceproject.controllers.CountryController;
import com.countryservice.demo.springbootcountryserviceproject.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.countryservice.demo.springbootcountryserviceproject")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMvcTests.class})
public class ControllerMockMvcTests {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryController countryController;
    List<Country> myCountries;
    Country country;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception {
        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));
        when(countryService.getAllCountries()).thenReturn(myCountries);

        this.mockMvc.perform(get("/getcountries"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void test_getCountryById() throws Exception {
        country = new Country(2, "USA", "Washington");
        int id = 2;
        when(countryService.getCountryById(id)).thenReturn(country);

        this.mockMvc.perform(get("/getcountries/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception {
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";
        when(countryService.getCountryByName(countryName)).thenReturn(country);

        this.mockMvc.perform(get("/getcountries/countryname").param("name", "USA"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception {
        country = new Country(3, "Germany", "Berlin");
        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);
        this.mockMvc.perform(post("/addcountry").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void test_updateCountry() throws Exception {
        country = new Country(3, "Japan", "Tokyo");
        int id = 3;
        when(countryService.getCountryById(id)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);
        this.mockMvc.perform(put("/updatecountry/{id}", id).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
                .andDo(print());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        country = new Country(3, "Japan", "Tokyo");
        int id = 3;
        when(countryService.getCountryById(id)).thenReturn(country);

        this.mockMvc.perform(delete("/deletecountry/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
