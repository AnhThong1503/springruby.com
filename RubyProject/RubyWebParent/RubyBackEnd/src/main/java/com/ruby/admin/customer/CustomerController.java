package com.ruby.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ruby.admin.paging.PagingAndSortingHelper;
import com.ruby.admin.paging.PagingAndSortingParam;
import com.ruby.common.entity.Country;
import com.ruby.common.entity.Customer;
import com.ruby.common.exception.CustomerNotFoundException;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;

	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return "redirect:/customers/page/1?sortField=firstName&sortDir=asc";
	}

	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listCustomers", moduleURL = "/customers") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		service.listByPage(pageNum, helper);

		return "customers/customers";
	}

	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateCustomerEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateCustomerEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The customer ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/customers";
	}

	@GetMapping("/customers/detail/{id}")
	public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);

			model.addAttribute("customer", customer);

			return "customers/customer_detail_modal";
		} catch (CustomerNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/customers";
		}
	}

	@GetMapping("/customers/edit/{id}")
	public String editCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);
			List<Country> countries = service.listAllCountries();

			model.addAttribute("customer", customer);
			model.addAttribute("listCountries", countries);
			model.addAttribute("pageTitle", String.format("Edit Customer (ID: %d)", id));

			return "customers/customer_form";
		} catch (CustomerNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/customers";
		}
	}

	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, Model model, RedirectAttributes ra) {
		service.save(customer);
		ra.addAttribute("message", "The customer ID " + customer.getId() + " has been updated success fully");
		return "redirect:/customers";
	}

	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message", "The customer ID " + id + " has been deleted success fully");
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/customers";
	}
}
