package com.emir.securitydemo.controller;

import com.emir.securitydemo.repository.EmployeeRepository;
import com.emir.securitydemo.security.auth.AuthenticationRequest;
import com.emir.securitydemo.security.auth.RegisterRequest;
import com.emir.securitydemo.service.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final EmployeeRepository empRep;

    @PostMapping("/authenticate")
    public String authenticate(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody MultiValueMap<String, String> formData) {
        String token = authenticationService.authenticate(new AuthenticationRequest(
                formData.getFirst("username"),
                formData.getFirst("password")
                ));
        if (StringUtils.isEmpty(token)) {
            return "redirect:/login?error";
        } else {
            /*Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/employees");
            response.addCookie(cookie);*/
            request.getSession().setAttribute("Authorization", token);
            return "redirect:/employees";
        }
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("employee") RegisterRequest request) {
        if (empRep.findByUsername(request.getUsername()).isEmpty()) {
            authenticationService.register(request);
            return "redirect:/login?registered";
        } else return "redirect:/login/register?error";
    }


   /* @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));

    }*/
}
