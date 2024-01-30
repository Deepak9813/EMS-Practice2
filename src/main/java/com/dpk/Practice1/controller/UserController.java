package com.dpk.Practice1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dpk.Practice1.model.User;
import com.dpk.Practice1.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

@Log
@Controller
public class UserController {

	// private static final Logger log =
	// LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping({ "/", "/login" })
	public String getLogin() {

		return "LoginForm";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute User user, Model model, HttpSession session) {

		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		User usr = userService.userLogin(user.getEmail(), user.getPassword());

		if (usr != null) {

			log.info("=============== Login Success ======================");

			session.setAttribute("validUser", usr); // ("key", value)
			session.setMaxInactiveInterval(500);

			// model.addAttribute("uname", usr.getUserName()); // ("key", value)
			return "Home";
		}

		log.info("================== Login Failed ===================");

		model.addAttribute("error", "user does not exist!"); // ("key", value)
		return "LoginForm";
	}

	@GetMapping("/signup")
	public String getSignup() {

		return "SignupForm";
	}

	@PostMapping("/signup")
	public String postSignup(@ModelAttribute User user, @RequestParam String confirmPassword, Model model) {

		// yo direct front end ma garda hunxa javascript[or jQuery] baata
		// yo maile backend baata ni gareko[not important]
		if ((user.getPassword() != confirmPassword) && (!user.getPassword().equals(confirmPassword))) {

			model.addAttribute("message", "password doesn't match!..."); // ("key", value)
			return "SignupForm";

		}

		// check if user(email) already exist or not
		User usr = userService.getUserByEmail(user.getEmail());
		if (usr != null) {

			model.addAttribute("message", "Email(user) already exist!");
			return "SignupForm";
		}

		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userService.userSignup(user);

		// return "LoginForm";
		return "redirect:/login"; // redirect or return always indicate GetMapping
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate(); // session kill gareko

		log.info("================== User Logout ============");

		return "LoginForm";
	}

	@GetMapping("/profile")
	public String getProfile(HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

		return "Profile";
	}

//	================ Forgot Password ========================

	@GetMapping("/forgotPassword")
	public String getForgotPasswordForm() {

		return "ForgotPasswordForm";
	}

	@PostMapping("/forgotPassword")
	public String postForgotPasswordForm(@RequestParam String emailOrPhone, Model model) {

		User usr1 = userService.getUserByEmail(emailOrPhone);
		User usr2 = userService.getUserByPhone(emailOrPhone);

		if ((usr1 != null)) {

			// return "ChangePasswordForm";

			// ============== Password change garda id lagera(id controller ma lagera) jaane,
			// ani uta receive garne ==============

			return "redirect:/changePassword/" + usr1.getId();
			
		} 
		//if loop lagauda ni hunxa
		else if (usr2 != null) {

			// return "ChangePasswordForm";

			// ============== Password change garda id lagera(id controller ma lagera) jaane,
			// ani uta receive garne ==============

			return "redirect:/changePassword/" + usr2.getId();

		}

		model.addAttribute("message", "Invalid Email Or Phone...!"); // ("key", value)
		return "ForgotPasswordForm";
	}

	// ==== id receive garne ani usko(User ko) data lagera jaane ChangePasswordForm
	// ma
	@GetMapping("/changePassword/{id}")
	public String getChangePasswordForm(@PathVariable int id, Model model) {

		// yo id baata user find garne

		User user = userService.getUserById(id);

//		System.out.println("=========== user info ============ ");
//		System.out.println(user.getFname());
//		System.out.println(user.getLname());
//		System.out.println(user.getUserName());
//		System.out.println(user.getEmail());
//		System.out.println(user.getPassword());

		model.addAttribute("userModel", user); // ("key", value)
		// here, userModel = userObject (j) lekhda ni huxa because User ko object aauxa

		return "ChangePasswordForm";
	}

	@PostMapping("/changePassword")
	public String postChangePasswordForm(@ModelAttribute User user) {

		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userService.updateUser(user);

		// return "LoginForm";
		return "redirect:/login";
	}
}
