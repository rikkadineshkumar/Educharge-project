package com.Educharge.EduchargeAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Educharge.EduchargeAPI.Model.Profile;
import com.Educharge.EduchargeAPI.Repository.ProfileRepository.ProfileRepository;

@Service
public class ProfileServices {

	@Autowired
	ProfileRepository profileRepository;

	public Profile addProfile(Profile p) {
		p.setUid(((int)profileRepository.count())+1);
		profileRepository.save(p);
		return  p;
	}

	public Profile editProfile(Profile p) {
		profileRepository.insert(p);
		return p;
	}

	public Profile showProfile(int id) {
		return profileRepository.findOne(id);
	}

	public Profile deleteProfile(int uid) {
		Profile p = profileRepository.findOne(uid);
		profileRepository.delete(uid);
		return p;
	} 
	
	
}
