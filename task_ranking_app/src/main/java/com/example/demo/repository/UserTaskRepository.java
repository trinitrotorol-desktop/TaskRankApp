package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PriorityInformation;
import com.example.demo.model.TaskInformation;
import com.example.demo.model.UserTask;

@Repository
public class UserTaskRepository {

	private final JdbcTemplate jdbcTemplate;

	public UserTaskRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void saveUserTask(UserTask userTask) {
		String sql = "INSERT INTO UserTask (UserID, TaskID) VALUES (?, ?)";
		jdbcTemplate.update(sql, userTask.getUserId(), userTask.getTaskId());
	}
	
	private final RowMapper<TaskInformation> rowMapper = (rs, rowNum) -> {
		TaskInformation task = new TaskInformation();
		task.setTaskId(rs.getInt("TaskID"));
		task.setDeadline(rs.getTimestamp("Deadline").toLocalDateTime());
		task.setContent(rs.getString("Content"));
		task.setProgress(rs.getBoolean("Progress"));
		task.setTaskPoint(rs.getInt("TaskPoint"));
		task.setEvaluationCount(rs.getInt("EvaluationCount"));
		
		PriorityInformation priority = new PriorityInformation();
		priority.setPriorityId(rs.getInt("PriorityID"));
		priority.setPriority(rs.getString("Priority"));
		task.setPriority(priority);
		
		return task;
	};
	
	public List<TaskInformation> findTasksByUserId(int userId) {
		String sql =
			"""
				SELECT ti.*, pi.Priority
				FROM UserTask ut
				JOIN TaskInformation ti ON ut.TaskID = ti.TaskID
				JOIN PriorityInformation pi ON ti.PriorityID = pi.PriorityID
				WHERE ut.UserID = ?
			""";
		
		return jdbcTemplate.query(sql, rowMapper, userId);
	}
	
	public TaskInformation findRandomTaskExcludingUserId(int userId) {
		String sql = 
			"""
				SELECT ti.*, pi.Priority
				FROM UserTask ut
				JOIN TaskInformation ti ON ut.TaskID = ti.TaskID
				JOIN PriorityInformation pi ON ti.PriorityID = pi.PriorityID
				WHERE ut.UserID != ? AND ti.Progress = true
				ORDER BY RANDOM()
				LIMIT 1
			""";
		
		try {
			return jdbcTemplate.queryForObject(sql, rowMapper, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
	    }
	}

}
