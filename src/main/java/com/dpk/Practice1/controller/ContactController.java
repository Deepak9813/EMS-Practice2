package com.dpk.Practice1.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dpk.Practice1.utils.MailUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {
	
	@Autowired
	private MailUtil mailUtil;
	
	@GetMapping("/contact")
	public String getContactForm(HttpSession session) {
		
		if(session.getAttribute("validUser") == null) {
			
			return "LoginForm";
		}
		
		return "ContactForm";
	}
	
	
	@PostMapping("/contact")
	public String postContact(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String message, @RequestParam MultipartFile attachFile, Model model) throws MessagingException, IOException {
		
		//mailUtil.sendEmail(toEmail, subject, message);
		
//		mailUtil.sendEmailWithAttachment(toEmail, subject, message, attachFile);
//		return "ContactForm";
		
		//=========== Using Backend Validation	================
		
				if ((toEmail != null) && (!toEmail.equals(""))) {

					if (((subject != null) && (!subject.equals("")) && (!subject.isEmpty())) || ((message != null) && (!message.equals("")) && (!message.isEmpty())) || ((attachFile != null) && (!attachFile.equals("")) && (!attachFile.isEmpty()))) {

						mailUtil.sendEmailWithAttachment(toEmail, subject, message, attachFile);

						model.addAttribute("message", "message successfully send");
						return "ContactForm";
						//return "redirect:/contact";

					} else {
						model.addAttribute("errorMessage",
								"message sending fail because you must include at least subject or message or attach file");
						return "ContactForm";
						//return "redirect:/contact";
					}
				}

				model.addAttribute("errorMessage", "Please Enter the Receiver Email");
				return "ContactForm";	
				//return "redirect:/contact";
				
				
			}
	}

