package com.Educharge.EduchargeAPI.Repository.ProfileRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.Educharge.EduchargeAPI.Model.Profile;

public interface ProfileRepository_String extends MongoRepository<Profile, String> {

	@Query("{ 'email' : ?0 }")
	Profile findByEmail(String email);

}
