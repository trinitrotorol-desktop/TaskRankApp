package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TaskInformation;

@Repository
public class TaskInformationRepository {

	private final JdbcTemplate jdbcTemplate;

	public TaskInformationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int saveTask(TaskInformation task) {
		String sql = "INSERT INTO TaskInformation (Deadline, Content, Progress, TaskPoint, EvaluationCount, PriorityID)"
				+ " VALUES (?, ?, ?, ?, ?, ?) RETURNING TaskID";
		
		return jdbcTemplate.queryForObject(
			sql, 
			Integer.class, 
			task.getDeadline(), 
			task.getContent(), 
			task.getProgress(), 
			task.getTaskPoint(), 
			task.getEvaluationCount(), 
			task.getPriorityId()
		);
	}
	
	public void updateTaskPointsAndEvaluationCount(int taskId, int evaluationPoints) {
		String sql =
			"""
				UPDATE TaskInformation
				SET TaskPoint = COALESCE(TaskPoint, 0) + ?, 
					EvaluationCount = COALESCE(EvaluationCount, 0) + 1
				WHERE TaskID = ?
			""";
		jdbcTemplate.update(sql, evaluationPoints, taskId);
	}
	
	public void updateTaskProgress(int taskId, boolean progress) {
		String sql = "UPDATE TaskInformation SET Progress = ? WHERE TaskID = ?";
		jdbcTemplate.update(sql, progress, taskId);
    }
}
