package com.ruby.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruby.common.entity.Country;
import com.ruby.common.entity.Customer;
import com.ruby.setting.CountryRepository;

@Service
public class CustomerService {

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private CustomerRepository customerRepo;

	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}

	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
}
