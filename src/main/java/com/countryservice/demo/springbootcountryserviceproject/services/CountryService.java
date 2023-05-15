package com.countryservice.demo.springbootcountryserviceproject.services;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import com.countryservice.demo.springbootcountryserviceproject.controllers.AddResponse;
import com.countryservice.demo.springbootcountryserviceproject.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(int id) {
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for (Country con : countries) {
            if (con.getId() == id) {
                country = con;
            }
        }
        return country;
    }

    public Country getCountryByName(String countryName) {
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for (Country con : countries) {
            if (con.getCountryName().equalsIgnoreCase(countryName)) {
                country = con;
                break;
            }
        }
        return country;

    }

    public Country addCountry(Country country) {
        country.setId(getMaxId());
        countryRepository.save(country);
        return country;
    }

    public int getMaxId() {
        return countryRepository.findAll().size() + 1;
    }

    public Country updateCountry(Country country) {
        countryRepository.save(country);
        return country;
    }

   /* public AddResponse deleteCountry(int id) {
        countryRepository.deleteById(id);
        AddResponse res = new AddResponse();
        res.setMsg("Country deleted");
        res.setId(id);
        return res;
    }*/

    public void deleteCountry(Country country) {
        countryRepository.delete(country);

    }
}
