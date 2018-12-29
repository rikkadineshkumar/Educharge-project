package com.Educharge.EduchargeAPI.Repository.PostRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Educharge.EduchargeAPI.Model.Post;


public interface PostRepository extends MongoRepository<Post, Integer> {

}
