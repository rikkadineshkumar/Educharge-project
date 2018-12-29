package com.Educharge.EduchargeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

//@SpringBootApplication
//@Configuration
//@EnableWebMvc
//@ComponentScan
//@EnableMongoRepositories(basePackages = "com.Educharge.EduchargeAPI.Repository")
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
//@PropertySource("classpath:application.properties")
@SuppressWarnings("deprecation")
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class EduchargeApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EduchargeApiApplication.class, args);
	}   
	
/*	@Bean
	CommandLineRunner init(ProfileRepository profileRepository,PostRepository postRepository) {
		//int c=profileRepository.count();
		//profileRepository.save(new Profile(c+1, "ganesh", "kumar", "male", new Date(19,4,2009), "danialdinesh333@gmail.com", "password","admin"));
		//System.out.println(profileRepository.findAll());
		Profile p = new Profile((int)profileRepository.count()+1,"ram", "Krishna", "male", new Date(19,4,2009), "danialdinesh333@gmail.com", "password","admin");
		int like[]={};
		Comment c1= new Comment(2,1,"hi!",new Date());
		Comment c2= new Comment(3,1,"hahahha!",new Date());
		Comment cmnt[]={c1,c2};
		Post post = new Post(1,1,"Hello!",like,cmnt,new Date());
		profileRepository.save(p);
		postRepository.save(post);
		return null;
	}
*/	
	//Code to remove extra column _class n mongoDB 
		
		public @Bean MongoDbFactory mongoDbFactory() throws Exception {
			//MongoURI uri = new MongoURI("mongodb://52.15.64.17:27017/educharge");
			MongoURI uri = new MongoURI("mongodb://dinesh:superuser@52.15.64.17:27017/educharge");
			return new SimpleMongoDbFactory(new Mongo(uri), "educharge");
		}

		public @Bean MongoTemplate mongoTemplate() throws Exception {

			// remove _class
			MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
			converter.setTypeMapper(new DefaultMongoTypeMapper(null));

			MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);

			return mongoTemplate;

		}

}
