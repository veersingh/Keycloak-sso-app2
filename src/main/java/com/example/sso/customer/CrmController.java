package com.example.sso.customer;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class CrmController {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private KeycloakSecurityContext securityContext;
	@Autowired
	private AccessToken accessToken;

	@GetMapping
	public String home() {
		return "home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) throws Exception {
		request.logout();
		return "redirect:/";
	}

	@GetMapping("/customers")
    public String customers(Model model, Principal principal) {
        System.out.println("AccessToken: " + securityContext.getTokenString());
        System.out.println("PreferredUsername:" + accessToken.getPreferredUsername()+"Username:"+ accessToken.getName());
        System.out.println("Principal:"+ principal.getName());
        model.addAttribute(customerRepository.findAll());
        return "customers";
    }

	@GetMapping("/customers/{id}")
	public String customer(@PathVariable("id") Long id, Model model) {
		model.addAttribute(customerRepository.findOne(id));
		return "customer";
	}

}
