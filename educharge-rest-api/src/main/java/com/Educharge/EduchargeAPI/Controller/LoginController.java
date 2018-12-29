package com.Educharge.EduchargeAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Educharge.EduchargeAPI.Service.LoginServices;

@RestController
@RequestMapping(value = "/Login")
public class LoginController {

	@Autowired
	LoginServices loginServices;
	
	@GetMapping("/{email}/{pass}")
	public String validateLogin(@PathVariable("email") String email , @PathVariable("pass") String pass){
		return loginServices.validateLogin(email,pass);
	}
}
