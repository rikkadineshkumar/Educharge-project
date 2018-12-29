package com.Educharge.EduchargeAPI.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Educharge.EduchargeAPI.Model.Post;
import com.Educharge.EduchargeAPI.Repository.PostRepository.PostRepository;

@Service
public class PostServices {

	@Autowired
	PostRepository postRepository;

	@Autowired
	Post post;

	public Post createPost(Post p) {
		p.setPid((int) postRepository.count() + 1);
		ArrayList<Object> al = new ArrayList<Object>();
		p.setLikes(al.toArray());
		postRepository.save(p);
		return null;
	}

	public Post editPost(Post p) {
		postRepository.save(p);
		return null;
	}

	public Post showPost(int pid) {
		return postRepository.findOne(pid);
	}

	public Post deletePost(int pid) {
		postRepository.delete(pid);
		return null;
	}
	
	public List<Post> showAllPost(){
		return postRepository.findAll();
	}

	public Post likePost(int pid, int uid) {
		post = postRepository.findOne(pid);
		Object[] likes = post.getLikes();
		
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(likes));
		if(!(temp.contains(uid)))
		temp.add(uid);

		post.setLikes(temp.toArray());
		postRepository.save(post);
		return post;
	}

	public Post dislikePost(int pid, int uid) {
		post = postRepository.findOne(pid);
		Object[] likes = post.getLikes();

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(likes));
		temp.remove((Object)uid);

		post.setLikes(temp.toArray());
		postRepository.save(post);
		return post;
	}

	public Post commentPost(int pid, int uid, String cmnt) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
