package com.countryservice.demo.springbootcountryserviceproject.controllers;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import com.countryservice.demo.springbootcountryserviceproject.services.CountryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryService countryService;

    /*@GetMapping("/getcountries")
    public List<Country> getCountries() {
        return countryService.getAllCountries();
    }*/

    @GetMapping("/getcountries")
    public ResponseEntity<List<Country>> getCountries() {
        try {
            List<Country> countries = countryService.getAllCountries();
            return  new ResponseEntity<List<Country>>(countries,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<List<Country>>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") int id) {
        try {
            Country country = countryService.getCountryById(id);
            return new ResponseEntity<Country>(country, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getcountries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName) {
        try {
            Country country = countryService.getCountryByName(countryName);
            return new ResponseEntity<Country>(country, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        try {
            country=countryService.addCountry(country);
            return new ResponseEntity<Country>(country,HttpStatus.CREATED);
            }
        catch (Exception e)
        {
            return new ResponseEntity<Country>(HttpStatus.CONFLICT);
        }


    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country) {
        try {
            Country existCountry = countryService.getCountryById(id);
            existCountry.setCountryName(country.getCountryName());
            existCountry.setCountryCapital(country.getCountryCapital());
            Country updatedCountry = countryService.updateCountry(existCountry);
            return new ResponseEntity<Country>(updatedCountry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Country>(HttpStatus.CONFLICT);
        }
    }

    /*@DeleteMapping("/deletecountry/{id}")
    public AddResponse deleteCountry(@PathVariable(value = "id") int id) {
        return countryService.deleteCountry(id);
    }*/

    /*@DeleteMapping("/deletecountry")
    public void deleteCountry(@RequestBody Country country) {
        countryService.deleteCountry(country);
    }*/

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") int id) {
        Country country=null;
        try {
                country=countryService.getCountryById(id);
                countryService.deleteCountry(country);

        }
        catch(Exception e)
        {
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country,HttpStatus.OK);

    }

}
