package com.countryservice.demo.springbootcountryserviceproject.repositories;

import com.countryservice.demo.springbootcountryserviceproject.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CountryRepository extends JpaRepository<Country,Integer>
{
}
