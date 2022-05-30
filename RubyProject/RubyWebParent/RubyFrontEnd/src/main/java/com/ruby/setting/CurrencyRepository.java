package com.ruby.setting;

import org.springframework.data.repository.CrudRepository;

import com.ruby.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}
