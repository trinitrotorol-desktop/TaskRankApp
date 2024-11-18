package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.TaskEvaluation;
import com.example.demo.repository.TaskEvaluationRepository;

@Service
public class TaskEvaluationService {

	private final TaskEvaluationRepository taskEvaluationRepository;

	public TaskEvaluationService(TaskEvaluationRepository taskEvaluationRepository) {
		this.taskEvaluationRepository = taskEvaluationRepository;
	}
	
	public void saveTaskEvaluation(int taskId, int evaluationId) {
		TaskEvaluation taskEvaluation = new TaskEvaluation();
		taskEvaluation.setTaskId(taskId);
		taskEvaluation.setEvaluationId(evaluationId);
		taskEvaluationRepository.insertTaskEvaluation(taskEvaluation);
	}
}
