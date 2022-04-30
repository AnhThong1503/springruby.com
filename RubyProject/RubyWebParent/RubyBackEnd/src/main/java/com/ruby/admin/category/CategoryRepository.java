package com.ruby.admin.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ruby.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
