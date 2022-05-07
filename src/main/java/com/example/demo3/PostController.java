package com.example.demo3;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo3.data.entity.Posts;
import com.example.demo3.data.repository.PostsRepository;
import com.example.demo3.form.PostForm;
import com.example.demo3.service.AccountUserDetails;

@Controller
public class PostController {
	
	@Autowired
	private PostsRepository postsRepository;
	
	@GetMapping("/posts")
	public String getNewPosts(Model model) {
        // 登録用インスタンス設定
		PostForm postForm = new PostForm();
		model.addAttribute("postForm", postForm);
		// 一覧表示
		List<Posts> posts = postsRepository.findAll();
		Collections.reverse(posts);
		model.addAttribute("posts", posts);
		return "posts";
	}
	
//	投稿登録メソッド
	@PostMapping("/posts/create")
	public String registerPost(@Validated PostForm postForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		if (bindingResult.hasErrors()) {
			List<Posts> posts = postsRepository.findAll();
			Collections.reverse(posts);
			model.addAttribute("posts", posts);
			model.addAttribute("postForm", postForm);
			return "/posts";
		}
		Posts post = new Posts();
		post.setName(user.getName());
		post.setTitle(postForm.getTitle());
		post.setText(postForm.getText());
		post.setDate(LocalDateTime.now());
		postsRepository.save(post);
		return "redirect:/posts";
	}
	
	// 削除メソッド
	@PostMapping("/posts/delete/{id}")
	public String deletePost(@PathVariable Integer id) {
		postsRepository.deleteById(id);
		return "redirect:/posts";
	}
}
