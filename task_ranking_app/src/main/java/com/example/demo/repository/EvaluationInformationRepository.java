package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EvaluationInformation;

@Repository
public class EvaluationInformationRepository {

	private final JdbcTemplate jdbcTemplate;

	public EvaluationInformationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int insertEvaluation(EvaluationInformation evaluation) {
		String sql = "INSERT INTO EvaluationInformation (EvaluationPoint) VALUES (?) RETURNING EvaluationID";
		return jdbcTemplate.queryForObject(sql, Integer.class, evaluation.getEvaluationPoint());
	}
}