package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskRankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskRankingAppApplication.class, args);
//		String url = "jdbc:postgresql://tokushima.data.ise.shibaura-it.ac.jp:5432/group1db";
//		String username = "al22095";
//		String password = "bond";
//		
//		try (Connection connection = DriverManager.getConnection(url, username, password)) {
//			System.out.println("Connected to the database!");
//			
//			String query = "SELECT * FROM t_test";
//			try (
//					Statement statement = connection.createStatement();
//					ResultSet resultSet = statement.executeQuery(query);
//				) {
//				
//				while (resultSet.next()) {
//					int id = resultSet.getInt("id");
//					String name = resultSet.getString("name");
//					double value = resultSet.getDouble("value");
//					System.out.println("ID: " + id + ", Name: " + name + ", Value: " + value);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
