package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controller.LoginController.LoginUser;
import com.example.demo.model.TaskInformation;
import com.example.demo.service.TaskInformationService;
import com.example.demo.service.UserTaskService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class TaskCreateController {
	
	private final TaskInformationService taskInformationService;
	private final UserTaskService userTaskService;
	
	public TaskCreateController(TaskInformationService taskInformationService, UserTaskService userTaskService) {
		this.taskInformationService = taskInformationService;
		this.userTaskService = userTaskService;
	}
	
	@PostMapping("/task/create")
	public String createTask(
			@RequestParam("deadlineDate") String deadlineDate,
			@RequestParam("deadlineTime") String deadlineTime,
			@RequestParam("content") String content,
			@RequestParam("priorityId") Integer priorityId,
			HttpSession session) {
		
		LoginUser loggedInUser = (LoginUser) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/Login.html";
		}
		
		LocalDate date = LocalDate.parse(deadlineDate);
		LocalTime time = LocalTime.parse(deadlineTime);
		LocalDateTime deadline = LocalDateTime.of(date, time);
		
		TaskInformation task = new TaskInformation();
		task.setDeadline(deadline);
		task.setContent(content);
		task.setProgress(false);
		task.setTaskPoint(0);
		task.setEvaluationCount(0);
		task.setPriorityId(priorityId);
		
		int taskId = taskInformationService.saveTask(task);
		
		userTaskService.saveUserTask(loggedInUser.getUserID(), taskId);
		
		return "redirect:/mypage";
    }
	
	@GetMapping("/taskcreate")
	private String taskcretate(){
		return "redirect:/TaskCreate.html";
	}
}
