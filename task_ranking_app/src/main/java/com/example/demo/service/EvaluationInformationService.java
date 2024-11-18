package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.EvaluationInformation;
import com.example.demo.repository.EvaluationInformationRepository;

@Service
public class EvaluationInformationService {

	private final EvaluationInformationRepository evaluationRepository;

	public EvaluationInformationService(EvaluationInformationRepository evaluationRepository) {
		this.evaluationRepository = evaluationRepository;
	}
	
	public int saveEvaluation(int evaluationPoints) {
		EvaluationInformation evaluation = new EvaluationInformation();
		evaluation.setEvaluationPoint(evaluationPoints);
		return evaluationRepository.insertEvaluation(evaluation);
	}
}
