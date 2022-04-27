package com.ruby.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.ruby.common.entity.Role;
import com.ruby.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userThongTr = new User("thong@gmail.com", "thong1503", "Thong", "Truong");
		userThongTr.addRole(roleAdmin);

		User savedUser = repo.save(userThongTr);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userDuyKhanh = new User("kduy@gmail.com", "kduy1999", "Duy", "Khanh");

		Role roleEditor = new Role(4);
		Role roleAssistant = new Role(6);

		userDuyKhanh.addRole(roleEditor);
		userDuyKhanh.addRole(roleAssistant);

		User savedUser = repo.save(userDuyKhanh);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserById() {
		User userThong = repo.findById(1).get();
		System.out.println(userThong);
		assertThat(userThong).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userThong = repo.findById(1).get();
		userThong.setEnabled(true);
		userThong.setEmail("truonghuynhanhthong@gmail.com");

		repo.save(userThong);
	}

	@Test
	public void testUpdateUserRoles() {
		User userDuy = repo.findById(2).get();
		Role roleEditor = new Role(4);
		Role roleSales = new Role(3);
		userDuy.getRoles().remove(roleEditor);
		userDuy.addRole(roleSales);

		repo.save(userDuy);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}

	@Test
	public void testGetUserByEmail() {
		String email = "truonghuynhanhthong@gmail.com";
		User user = repo.getUserByEmail(email);

		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);

		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser() {
		Integer id = 8;
		repo.updateEnabledStatus(id, false);
	}

	@Test
	public void testEnabledUser() {
		Integer id = 8;
		repo.updateEnabledStatus(id, true);
	}

	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<User> page = repo.findAll(pageable);

		List<User> listUser = page.getContent();

		listUser.forEach(user -> System.out.println(user));

		assertThat(listUser.size()).isEqualTo(pageSize);
	}

	@Test
	public void testSearchUsers() {
		String keyword = "bruce";

		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<User> page = repo.findAll(keyword, pageable);

		List<User> listUser = page.getContent();

		listUser.forEach(user -> System.out.println(user));

		assertThat(listUser.size()).isGreaterThan(0);
	}

}
