package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.UserInformation;
import com.example.demo.repository.UserInformationRepository;

@Service
public class UserInformationService {

	private final UserInformationRepository userInformationRepository;

	public UserInformationService(UserInformationRepository userInformationRepository) {
		this.userInformationRepository = userInformationRepository;
	}

	public List<UserInformation> getTop20UsersByUserPoint() {
		return userInformationRepository.findTop20ByUserPoint();
	}
	
	public void increaseUserPoints(int taskId, int increment) {
		userInformationRepository.updateUserPointByTaskId(taskId, increment);
	}
}
