package com.ruby.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ruby.common.entity.Brand;
import com.ruby.common.entity.Category;
import com.ruby.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 2);
		Category category = entityManager.find(Category.class, 2);

		Product product = new Product();
		product.setName("Chi CHi CHI");
		product.setAlias("ChiChi");
		product.setShortDescription("chi chi");
		product.setFullDescription("chi chi go super saiyans");

		product.setBrand(brand);
		product.setCategory(category);

		product.setPrice(444);
		product.setCost(406);
		product.setEnabled(true);
		product.setInStock(true);

		product.setCreatedTime(new Date());
		product.setUpdateTime(new Date());

		Product savedProduct = repo.save(product);

		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllProducts() {
		Iterable<Product> iterable = repo.findAll();

		iterable.forEach(System.out::println);
	}

	@Test
	public void testGetProduct() {
		Integer id = 2;
		Product product = repo.findById(id).get();
		System.out.println(product);

		assertThat(product).isNotNull();
	}

	@Test
	public void testUpdateProduct() {
		Integer id = 1;
		Product product = repo.findById(id).get();
		product.setName("JaJaJoJo");

		repo.save(product);

		Product updateProduct = entityManager.find(Product.class, id);

		assertThat(updateProduct.getName()).isEqualTo("JaJaJoJo");
	}

	@Test
	public void testDeleteProduct() {
		Integer id = 1;
		repo.deleteById(id);

		Optional<Product> result = repo.findById(id);

		assertThat(!result.isPresent());
	}

	@Test
	public void testSaveProductWithImages() {
		Integer productId = 3;
		Product product = repo.findById(productId).get();

		product.setMainImage("Main image.png");
		product.addExtraImage("extra 1.png");
		product.addExtraImage("extra 2.png");
		product.addExtraImage("extra 3.png");

		Product savedProduct = repo.save(product);

		assertThat(savedProduct.getImage().size()).isEqualTo(3);

	}

	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 9;
		Product product = repo.findById(productId).get();

		product.addDetail("aaaa", "aaaa");
		product.addDetail("bbbb", "bbbb");
		product.addDetail("cccc", "cccc");

		Product savedProduct = repo.save(product);

		assertThat(savedProduct.getDetails()).isNotEmpty();
	}

}
