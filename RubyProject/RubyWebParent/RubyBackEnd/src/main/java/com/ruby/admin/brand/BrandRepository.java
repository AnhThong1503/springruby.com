package com.ruby.admin.brand;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ruby.admin.paging.SearchRepository;
import com.ruby.common.entity.Brand;

public interface BrandRepository extends SearchRepository<Brand, Integer> {

	public Long countById(Integer id);

	public Brand findByName(String name);

	@Override
	@Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
	public Page<Brand> findAll(String keyword, Pageable pageable);

	@Override
	@Query("SELECT NEW Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
	public List<Brand> findAll();
}
