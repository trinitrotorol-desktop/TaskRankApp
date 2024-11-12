package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class TaskEvaluationController {
	@GetMapping("/taskevaluation")
	private String taskevaluation(){
		return "/TaskEvaluation.html";
	}
}
