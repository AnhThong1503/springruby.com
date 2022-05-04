package com.ruby.admin.product;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ruby.common.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

}
