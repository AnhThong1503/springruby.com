package com.ruby.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ruby.common.entity.Brand;
import com.ruby.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

	@Autowired
	private BrandRepository repo;

	@Test
	public void testCreateBrand() {
		Category menShirt = new Category(6);
		Brand bobo = new Brand("BoBo");

		bobo.getCategories().add(menShirt);

		Brand savedBrand = repo.save(bobo);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateBrand2() {
		Category wonenShirt = new Category(2);
		Category womenHoodie = new Category(3);

		Brand hello = new Brand("Hello");
		hello.getCategories().add(womenHoodie);
		hello.getCategories().add(wonenShirt);

		Brand savedBrand = repo.save(hello);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);

	}

	@Test
	public void testCreateBrand3() {
		Brand Alo = new Brand("Alo");

		Alo.getCategories().add(new Category(6));
		Alo.getCategories().add(new Category(4));

		Brand savedBrand = repo.save(Alo);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}

	@Test
	public void testfindAll() {
		Iterable<Brand> brand = repo.findAll();
		brand.forEach(System.out::println);

		assertThat(brand).isNotEmpty();
	}

	@Test
	public void testGetById() {
		Brand brand = repo.findById(1).get();

		assertThat(brand.getName()).isEqualTo("HaHA");
	}

	@Test
	public void testUpdateName() {
		String newName = "HaHA";
		Brand haha = repo.findById(1).get();
		haha.setName(newName);

		Brand savedBrand = repo.save(haha);
		assertThat(savedBrand.getName()).isEqualTo(newName);
	}

	public void testDelete() {
		Integer id = 1;
		repo.deleteById(id);

		Optional<Brand> result = repo.findById(id);

		assertThat(result.isEmpty());
	}
}
