package com.example.demo3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo3.data.entity.Posts;
import com.example.demo3.data.repository.PostsRepository;

@Controller
public class PostController {
	
	@Autowired
	private PostsRepository postsRepository;
	
	@GetMapping("/posts")
	public String getPosts(Model model) {
		List<Posts> posts = postsRepository.findAll();
		model.addAttribute("posts", posts);
		return "posts";
	}
	
	
	@PostMapping("/posts")
	public String registerPost() {
		return "redirect: /posts";
	}
}
