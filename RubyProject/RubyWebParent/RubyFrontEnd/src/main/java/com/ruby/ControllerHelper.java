package com.ruby;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruby.common.entity.Customer;
import com.ruby.customer.CustomerService;

@Component
public class ControllerHelper {
	@Autowired
	private CustomerService customerService;

	public Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
}
