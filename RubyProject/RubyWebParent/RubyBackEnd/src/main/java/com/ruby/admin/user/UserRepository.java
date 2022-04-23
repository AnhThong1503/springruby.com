package com.ruby.admin.user;

import org.springframework.data.repository.CrudRepository;

import com.ruby.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
