package com.emir.securitydemo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping
public class PublicController {

	@GetMapping(value = {"/","/login"})
	public String login() {
		return "login/login-page";
	}

	@GetMapping("/register")
	public String register() {
		return "login/registerForm";
	}
}
