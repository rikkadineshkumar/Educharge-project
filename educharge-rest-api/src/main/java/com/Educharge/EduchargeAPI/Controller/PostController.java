package com.Educharge.EduchargeAPI.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Educharge.EduchargeAPI.Model.Post;
import com.Educharge.EduchargeAPI.Service.PostServices;

@RestController
@RequestMapping(value = "/Posts")
public class PostController {

	@Autowired
	PostServices postServices;

	@PostMapping("/createPost")
	public Post createPost(@RequestBody Post p) {
		return postServices.createPost(p);
	}

	@PutMapping("/editPost")
	public Post editPost(@RequestBody Post p){
		return postServices.editPost(p);
	}

	@GetMapping("/showAllPost")
	public List<Post> showAllPost() {
		return postServices.showAllPost();
	}
	
	
	@GetMapping("/showPost/{postid}")
	public Post showPost(@PathVariable("postid") int pid) {
		return postServices.showPost(pid);
	}

	@DeleteMapping("/deletePost/{postid}")
	public Post deletePost(@PathVariable("postid") int pid) {
		return postServices.deletePost(pid);
	}

	@GetMapping("{postid}/like/{userid}")
	public Post likePost(@PathVariable("postid") int pid, @PathVariable("userid") int uid) {
		return postServices.likePost(pid,uid);
	}

	@GetMapping("{postid}/dislike/{userid}")
	public Post dislikePost(@PathVariable("postid") int pid, @PathVariable("userid") int uid) {
		return postServices.dislikePost(pid,uid);
	}

	@PutMapping("{postid}/comment/{userid}")
	// @Consumes(MediaType.TEXT_PLAIN)
	public Post commentPost(@RequestBody String cmnt, @PathVariable("postid") int pid, @PathVariable("userid") int uid) {
		return postServices.commentPost(pid,uid,cmnt);
	}

}

/*
 * http://localhost/webapi/webapi/Services/createProfile (post)
 * http://localhost/webapi/webapi/Services/editProfile/1 (put)
 * http://localhost/webapi/webapi/Services/showProfile/1 (get)
 * http://localhost/webapi/webapi/Services/deleteProfile/1 (delete)
 * http://localhost/webapi/webapi/Services/createPost (post)
 * http://localhost/webapi/webapi/Services/editPost/18 (put)
 * http://localhost/webapi/webapi/Services/showPost/18 (get)
 * http://localhost/webapi/webapi/Services/deletePost/18 (delete)
 * http://localhost/webapi/webapi/Services/editPost/18/like/1 (get)
 * http://localhost/webapi/webapi/Services/editPost/18/dislike/1 (get)
 * http://localhost/webapi/webapi/Services/editPost/18/comment (put)
 */