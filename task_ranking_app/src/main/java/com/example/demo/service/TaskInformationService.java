package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.TaskInformation;
import com.example.demo.repository.TaskInformationRepository;

@Service
public class TaskInformationService {

	private final TaskInformationRepository taskInformationRepository;

	public TaskInformationService(TaskInformationRepository taskInformationRepository) {
		this.taskInformationRepository = taskInformationRepository;
	}

	public int saveTask(TaskInformation task) {
		return taskInformationRepository.saveTask(task);
	}
	
	public void updateTaskInformation(int taskId, int evaluationPoints) {
		taskInformationRepository.updateTaskPointsAndEvaluationCount(taskId, evaluationPoints);
	}
	
	public void completeTask(int taskId) {
		taskInformationRepository.updateTaskProgress(taskId, true);
	}
}
