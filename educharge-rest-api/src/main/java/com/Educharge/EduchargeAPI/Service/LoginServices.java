package com.Educharge.EduchargeAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Educharge.EduchargeAPI.Model.Profile;
import com.Educharge.EduchargeAPI.Repository.ProfileRepository.ProfileRepository_String;

@Service
public class LoginServices {

   @Autowired
   ProfileRepository_String profileRepository_string;
  
   Profile profile;
   
   public String validateLogin(String email,String pass){
	   
	   profile = profileRepository_string.findByEmail(email);
	   try{
			if(profile.getPassword().toString().equals(pass))
				   return ""+profile.getUid();
			else
				   return "Incorrect Password";
	   }catch(Exception e){
		   return "incorrect email";
	   }
   }
 
}
