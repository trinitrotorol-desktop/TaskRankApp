package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TaskEvaluation;

@Repository
public class TaskEvaluationRepository {

	private final JdbcTemplate jdbcTemplate;

	public TaskEvaluationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void insertTaskEvaluation(TaskEvaluation taskEvaluation) {
		String sql = "INSERT INTO TaskEvaluation (TaskID, EvaluationID) VALUES (?, ?)";
		jdbcTemplate.update(sql, taskEvaluation.getTaskId(), taskEvaluation.getEvaluationId());
	}
}
