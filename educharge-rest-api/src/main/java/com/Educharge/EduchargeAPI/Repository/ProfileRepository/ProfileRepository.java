package com.Educharge.EduchargeAPI.Repository.ProfileRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Educharge.EduchargeAPI.Model.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Integer>{

}
