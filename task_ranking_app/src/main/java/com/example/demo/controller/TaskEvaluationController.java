package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controller.LoginController.LoginUser;
import com.example.demo.model.TaskInformation;
import com.example.demo.service.EvaluationInformationService;
import com.example.demo.service.TaskEvaluationService;
import com.example.demo.service.TaskInformationService;
import com.example.demo.service.UserInformationService;
import com.example.demo.service.UserTaskService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class TaskEvaluationController {
	private final UserTaskService userTaskService;
	private final EvaluationInformationService evaluationInformationService;
    private final TaskEvaluationService taskEvaluationService;
    private final TaskInformationService taskInformationService;
    private final UserInformationService userInformationService;
	
	public TaskEvaluationController(
			UserTaskService userTaskService,
			EvaluationInformationService evaluationInformationService,
			TaskEvaluationService taskEvaluationService,
			TaskInformationService taskInformationService,
			UserInformationService userInformationService
			) {
		this.userTaskService = userTaskService;
		this.evaluationInformationService = evaluationInformationService;
		this.taskEvaluationService = taskEvaluationService;
		this.taskInformationService = taskInformationService;
		this.userInformationService = userInformationService;
	}
	
	@GetMapping("/taskevaluation")
	private String taskevaluation(HttpSession session, Model model){
		LoginUser loggedInUser = (LoginUser) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/Login.html";
		}
		int userId = loggedInUser.getUserID();
		TaskInformation randomTask = userTaskService.getRandomTaskExcludingUser(userId);
		model.addAttribute("task", randomTask);
		
		return "TaskEvaluation";
	}
	
	@PostMapping("/submit-evaluation")
	public String submitEvaluation(
			@RequestParam("taskId") int taskId,
			@RequestParam("evaluationPoints") int evaluationPoints,
			HttpSession session) {
		
		LoginUser loggedInUser = (LoginUser) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/Login.html";
		}
		
		int evaluationId = evaluationInformationService.saveEvaluation(evaluationPoints);
		
		taskEvaluationService.saveTaskEvaluation(taskId, evaluationId);
		
		taskInformationService.updateTaskInformation(taskId, evaluationPoints);
		
		userInformationService.increaseUserPoints(taskId, evaluationPoints);
		
		return "redirect:/mypage";
	}
}
