package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.TaskInformation;
import com.example.demo.model.UserTask;
import com.example.demo.repository.UserTaskRepository;

@Service
public class UserTaskService {

	private final UserTaskRepository userTaskRepository;

	public UserTaskService(UserTaskRepository userTaskRepository) {
		this.userTaskRepository = userTaskRepository;
	}

	public void saveUserTask(Integer userId, Integer taskId) {
		UserTask userTask = new UserTask();
		userTask.setUserId(userId);
		userTask.setTaskId(taskId);
		userTaskRepository.saveUserTask(userTask);
	}
	
	public List<TaskInformation> getTasksForUser(int userId) {
		return userTaskRepository.findTasksByUserId(userId);
	}
	
	public TaskInformation getRandomTaskExcludingUser(int userId) {
		return userTaskRepository.findRandomTaskExcludingUserId(userId);
	}
}
