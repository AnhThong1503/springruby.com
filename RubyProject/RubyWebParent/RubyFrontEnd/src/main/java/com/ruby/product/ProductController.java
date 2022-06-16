package com.ruby.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ruby.ControllerHelper;
import com.ruby.category.CategoryService;
import com.ruby.common.entity.Category;
import com.ruby.common.entity.Customer;
import com.ruby.common.entity.Review;
import com.ruby.common.entity.product.Product;
import com.ruby.common.exception.CategoryNotFoundException;
import com.ruby.common.exception.ProductNotFoundException;
import com.ruby.review.ReviewService;
import com.ruby.review.vote.ReviewVoteService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ReviewVoteService voteService;
	@Autowired
	private ControllerHelper controllerHelper;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
		return viewCategoryByPage(alias, model, 1);
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias, Model model,
			@PathVariable("pageNum") int pageNum) {

		try {
			Category category = categoryService.getCategory(alias);

			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());

			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;

			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPage", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);
			return "product/products_by_category";

		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model,
			HttpServletRequest request) {
		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			Page<Review> listReviews = reviewService.list3MostVotedReviewsByProduct(product);

			Customer customer = controllerHelper.getAuthenticatedCustomer(request);

			if (customer != null) {
				boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
				voteService.markReviewsVotedForProductByCustomer(listReviews.getContent(), product.getId(),
						customer.getId());

				if (customerReviewed) {
					model.addAttribute("customerReviewed", customerReviewed);
				} else {
					boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
					model.addAttribute("customerCanReview", customerCanReview);
				}
			}

			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getName());

			return "product/products_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}

	@GetMapping("/search")
	public String searchFirstPage(@Param("keyword") String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}

	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@Param("keyword") String keyword, @PathVariable("pageNum") int pageNum, Model model) {
		Page<Product> pageProducts = productService.search(keyword, pageNum);
		List<Product> listResult = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;

		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);

		return "product/search_result";
	}
}
