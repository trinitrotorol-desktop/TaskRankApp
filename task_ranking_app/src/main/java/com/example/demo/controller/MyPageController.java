package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.controller.LoginController.LoginUser;
import com.example.demo.model.TaskInformation;
import com.example.demo.model.UserInformation;
import com.example.demo.service.TaskInformationService;
import com.example.demo.service.UserInformationService;
import com.example.demo.service.UserTaskService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class MyPageController {
	private final UserInformationService userInformationService;
	private final UserTaskService userTaskService;
	private final TaskInformationService taskInformationService;
	
	public MyPageController(
			UserInformationService userInformationService,
			UserTaskService userTaskService,
			TaskInformationService taskInformationService
			) {
		this.userInformationService = userInformationService;
		this.userTaskService = userTaskService;
		this.taskInformationService = taskInformationService;
	}
	
	@GetMapping("/mypage")
	public String MyPage(HttpSession session, Model model) {
		LoginUser loggedInUser = (LoginUser) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/Login.html";
		}
		int userId = loggedInUser.getUserID();
		List<TaskInformation> tasks = userTaskService.getTasksForUser(userId);
		model.addAttribute("tasks", tasks);
		
		List<UserInformation> users = userInformationService.getTop20UsersByUserPoint();
		model.addAttribute("users", users);
		
		return "MyPage";
	}
	
	@PostMapping("/tasks/complete")
	public String completeTask(@RequestParam("taskId") int taskId, RedirectAttributes redirectAttributes) {
		taskInformationService.completeTask(taskId);
		
		return "redirect:/mypage";
	}
}
