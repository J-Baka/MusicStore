package com.hcl.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hcl.entity.Order;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.service.CartService;
import com.hcl.service.OrderService;
import com.hcl.service.ProductService;
import com.hcl.service.UserService;

@Controller
public class ProductsController {
	@Autowired
	ProductService service;

	@Autowired
	UserService userService;

	@Autowired
	CartService cartService;

	@Autowired
	OrderService orderService;

	Logger logger = LoggerFactory.getLogger(ProductsController.class);

	String role;

	@GetMapping("/")
	String hello(ModelMap model) {
		logger.info("Mapping to index");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getAuthorities().forEach(a -> {
			role = a.getAuthority();
		});
		model.addAttribute("username", authentication.getName());
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {
			return "adminHome";
		} else if (role.equalsIgnoreCase("ROLE_USER")) {
			return "indexloggeduser";
		} else {
			return "index";
		}
	}

	@GetMapping("/search")
	public String getAllByQuery(@RequestParam(name = "search") String query, ModelMap model) {
		List<Product> productList = service.getAllByQuery(query, query, query);
		model.addAttribute("products", productList);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getAuthorities().forEach(a -> {
			role = a.getAuthority();
		});
		model.addAttribute("username", authentication.getName());
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {
			return "adminProduct";
		} else if (role.equalsIgnoreCase("ROLE_USER")) {
			return "productloggeduser";
		} else {
			return "product";
		}
	}

	// Gets Product View
	@GetMapping("/product")
	String getProductView(ModelMap model) {
		logger.info("Mapping to products");
		List<Product> products = service.getAllProducts();
		model.addAttribute("products", products);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getAuthorities().forEach(a -> {
			role = a.getAuthority();
		});
		model.addAttribute("username", authentication.getName());
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {
			return "adminProduct";
		} else if (role.equalsIgnoreCase("ROLE_USER")) {
			return "productloggeduser";
		} else {
			return "product";
		}
	}

	// Gets Product View
	@GetMapping("/cart")
	String cart(ModelMap model) {
		Long userCartId = getUserIdMethod();
		List<Order> order = cartService.cartMethod(userCartId);
		model.addAttribute("username", getUserName());
		model.addAttribute("orders", order);
		return "cart";
	}

	// Gets Product View
	@GetMapping("/logout")
	String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "logout";
	}

	public Long getUserIdMethod() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> usercheck = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			usercheck = userService.findByUserName(currentUserName);
		}
		return usercheck.get().getUserId();
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@RequestMapping(value = "/login")
	public String getLoginPage(@RequestParam(name = "error", required = false) String error, Model model,
			@RequestParam(name = "logout", required = false) String logout) {
		if (error != null) {
			model.addAttribute("error", "Invalid Username or Password");
		}
		if (logout != null) {
			model.addAttribute("logout", "You have Successfully Logged Out");
		}
		return "login";
	}

	// Gets register View
	@GetMapping("/register")
	String getRegisterView(ModelMap model) {
		return "register";
	}

	@PostMapping("/registerdone")
	public String registerUser(@ModelAttribute("user") User user, ModelMap model) {
		logger.info("registration page entered");
		Optional<User> usercheck = userService.findByUserName(user.getUserName());
		if (usercheck.isPresent()) {
			model.addAttribute("message", "Username Already Exists!Try a different one!");
			return "/register";
		} else {
			String save = saveUserMethod(user);
			if (save.equals("Saved")) {
				model.addAttribute("message", "Customer registered Successfully!");
				return "/registrationsuccess";
			} else {
				model.addAttribute("message", "Error Occured on Registration. Please Try again!");
				return "/register";
			}
		}
	}

	public String saveUserMethod(User user) {
		try {
			userService.saveMethod(user);
			return "Saved";
		} catch (Exception e) {
			return "Error";
		}
	}

	@PostMapping("/success")
	public String success(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getAuthorities().forEach(a -> {
			role = a.getAuthority();
		});
		model.addAttribute("username", authentication.getName());
		if(authentication.isAuthenticated() && role.equalsIgnoreCase("ROLE_USER")) {
			return "indexloggeduser";
		}else if(authentication.isAuthenticated() && role.equalsIgnoreCase("ROLE_ADMIN")){
			return "adminHome";
		}else {
			return "login";
		}
	}

	@GetMapping("/viewdetail/{id}")
	public String viewProductDetail(@PathVariable String id, ModelMap model) {
		logger.info("update task page entered");
		long productId;
		productId = Long.parseLong(id);
		Optional<Product> productEntity = service.findProductById(productId);
		Product newProductEntity = productEntity.get();
		if (productEntity.isPresent()) {
			model.addAttribute("id", newProductEntity.getId());
			model.addAttribute("category", newProductEntity.getCategory());
			model.addAttribute("condition", newProductEntity.getCondition());
			model.addAttribute("image", newProductEntity.getBase64image());
			model.addAttribute("name", newProductEntity.getName());
			model.addAttribute("price", newProductEntity.getPrice());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			authentication.getAuthorities().forEach(a -> {
				role = a.getAuthority();
			});
			model.addAttribute("username", authentication.getName());
			if (role.equalsIgnoreCase("ROLE_ADMIN")) {
				return "adminProduct";
			} else if (role.equalsIgnoreCase("ROLE_USER")) {
				return "viewDetailloggeduser";
			} else {
				return "viewDetail";
			}
		} else {
			List<Product> tasks = service.getAllProducts();
			model.addAttribute("products", tasks);
			model.addAttribute("message", "Product Not found! Error!");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			authentication.getAuthorities().forEach(a -> {
				role = a.getAuthority();
			});
			model.addAttribute("username", authentication.getName());
			if (role.equalsIgnoreCase("ROLE_ADMIN")) {
				return "adminProduct";
			} else if (role.equalsIgnoreCase("ROLE_USER")) {
				return "productloggeduser";
			} else {
				return "product";
			}
		}
	}

	@RequestMapping("/403")
	public String accessdenied() {
		return "403";
	}
	
	@PostMapping("/errorMapping")
	String errorMapping(ModelMap model) {
		logger.info("Mapping to index");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getAuthorities().forEach(a -> {
			role = a.getAuthority();
		});
		model.addAttribute("username", authentication.getName());
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {
			return "adminHome";
		} else if (role.equalsIgnoreCase("ROLE_USER")) {
			return "indexloggeduser";
		} else {
			return "index";
		}
	}

}
