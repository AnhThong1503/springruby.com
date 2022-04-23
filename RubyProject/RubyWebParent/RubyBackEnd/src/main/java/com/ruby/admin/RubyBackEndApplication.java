package com.ruby.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.ruby.common.entity", "com.ruby.admin.user"})
public class RubyBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(RubyBackEndApplication.class, args);
	}

}
