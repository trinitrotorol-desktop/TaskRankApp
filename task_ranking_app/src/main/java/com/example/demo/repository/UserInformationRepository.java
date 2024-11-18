package com.example.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInformation;

@Repository
public class UserInformationRepository {

	private final JdbcTemplate jdbcTemplate;

	public UserInformationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<UserInformation> rowMapper = (rs, rowNum) -> {
		UserInformation user = new UserInformation();
		user.setUserId(rs.getInt("userid"));
		user.setMailAddress(rs.getString("mailaddress"));
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setUserPoint(rs.getInt("userpoint"));
		return user;
	};

	public List<UserInformation> findTop20ByUserPoint() {
		String sql = "SELECT * FROM UserInformation"
				+ " WHERE UserPoint IS NOT NULL"
				+ " ORDER BY UserPoint DESC LIMIT 20";
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	public void updateUserPointByTaskId(int taskId, int increment) {
		String sql =
			"""	
				UPDATE userinformation
				SET userpoint = COALESCE(userpoint, 0) + ?
				WHERE userid = (
					SELECT userid
					FROM usertask
					WHERE taskid = ?
				)
			""";
		jdbcTemplate.update(sql, increment, taskId);
	}
}
