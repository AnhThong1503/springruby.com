package com.ruby.shipping;

import org.springframework.data.repository.CrudRepository;

import com.ruby.common.entity.Country;
import com.ruby.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

	public ShippingRate findByCountryAndState(Country country, String state);
}