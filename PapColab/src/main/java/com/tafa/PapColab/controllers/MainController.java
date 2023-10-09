package com.tafa.PapColab.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tafa.PapColab.models.Comment;
import com.tafa.PapColab.models.Post;
import com.tafa.PapColab.models.User;
import com.tafa.PapColab.repository.CommentRepository;
import com.tafa.PapColab.repository.UserRepository;
import com.tafa.PapColab.services.PostServiceImpl;
import com.tafa.PapColab.services.UserServiceImpl;
import com.tafa.PapColab.services.apiservice;

@Controller
public class MainController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	PostServiceImpl postServiceImpl;

	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/")
	public String home() {
		return "main";
	}

	@GetMapping("/user/login")
	public String login() {
		return "login";
	}

	@GetMapping("/admin/login")
	public String adminlogin() {
		return "adminlogin";
	}

	@GetMapping("/user/feed")
	public String feed(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(auth.getName());
		model.addAttribute("user", user);
		List<Post> posts = postServiceImpl.getPostsByUserId(user.getId());
		posts.addAll(postServiceImpl.getFeed(user));
		for (Long collabId : user.getCollaboratorIds()) {
			posts.addAll(postServiceImpl.getPostsByUserId(collabId));
		}
		model.addAttribute("posts", posts);
		return "feed";
	}

	@GetMapping("/adminfeed")
	public String adminfeed() {
		return "adminfeed";
	}

	@GetMapping("/user/post")
	public String post() {
		return "post";
	}

	
	@GetMapping("/stat")
	public String stat() {
		return "stat";
	}

	@GetMapping("/statistics/{id}")
	public String statistics(@PathVariable Long id, Model model){
		Long postId = Long.valueOf(id);
		List<Comment> comments = commentRepository.findByPostId(postId);
			ArrayList<String> inputs = new ArrayList<String>();
			for (Comment comment : comments) {
				inputs.add(comment.getComment());			
			}
			JSONArray array = new JSONArray(apiservice.performSentimentAnalysis(inputs)); 
			// String[] lables = new String[5];
			// Float[] scores = new Float[5];
			ArrayList<String> labels = new ArrayList<String>();
			ArrayList<Float> scores = new ArrayList<Float>();
			for(int i=0; i < array.length(); i++){
				JSONArray array2 = array.getJSONArray(i);
				for (int j=0; j < array2.length(); j++){
					JSONObject object = array2.getJSONObject(j);
					System.out.println("________________________________________");
					System.out.println(object.get("label"));
					String label = (object.get("label").toString()).charAt(0) + "";
					labels.add(label);
					System.out.println(object.get("score"));
					scores.add(Float.parseFloat(object.get("score").toString())*100) ;
					System.out.println("________________________________________");
				}
			}
		JSONArray jslabel = new JSONArray(labels);
		JSONArray jsScore = new JSONArray(scores);
		model.addAttribute("id", postId);
		model.addAttribute("labels", jslabel);
		model.addAttribute("scores", jsScore);
		return "statistics";
	}

	

}