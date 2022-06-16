package com.ruby;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ruby.category.CategoryService;
import com.ruby.common.entity.Category;

@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/product_index")
	public String viewProductPage(Model model) {
		List<Category> listCategories = categoryService.listNoChildrenCategories();
		model.addAttribute("listCategories", listCategories);

		return "product_index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}

	@GetMapping("/contact")
	public String viewContactPage() {
		return "contact";
	}

	@GetMapping("")
	public String viewHomePage(Model model) {
		return "index";
	}
}
