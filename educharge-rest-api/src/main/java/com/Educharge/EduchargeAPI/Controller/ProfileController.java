package com.Educharge.EduchargeAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Educharge.EduchargeAPI.Model.Profile;
import com.Educharge.EduchargeAPI.Service.ProfileServices;

@RestController
@RequestMapping(value = "/Profiles")
public class ProfileController {

	@Autowired
	ProfileServices profileServices;
	
	@PostMapping("/createProfile")
	public Profile addProfile(@RequestBody Profile p){
		return profileServices.addProfile(p);
	}
	
	@PutMapping("/editProfile")
	public Profile editProfile(@RequestBody Profile p) {
		return profileServices.editProfile(p);
	}

	@GetMapping("/showProfile/{userid}")
	public Profile showProfile(@PathVariable("userid") int uid) {
		return profileServices.showProfile(uid);
	}

	@DeleteMapping("/deleteProfile/{userid}")
	public Profile deleteProfile(@PathVariable("userid") int uid){
		return profileServices.deleteProfile(uid);
	}
}
