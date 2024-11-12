package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MyPageController {
	@GetMapping("/mypage")
	private String MyPage(){
		return "/MyPage.html";
	}
}
