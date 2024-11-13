package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/")
@Controller
public class SignupController {
	@GetMapping("/signup")
	private String signup() {
		return "/Signup.html";
	}

	@PostMapping("/register")
	public String registerUser(
			@RequestParam("Username") String username,
			@RequestParam("MailAddress") String mailAddress,
			@RequestParam("Password") String password,
			@RequestParam("RePassword") String rePassword) {

		// パスワードと再入力パスワードの一致を確認
		if (!password.equals(rePassword)) {
			return "/Signup.html"; // 一致しない場合はエラーメッセージ付きで登録ページに戻る
		}

		// パスワードが一致する場合のみデータベースに挿入
		SignupUser user = new SignupUser();
		user.setUsername(username);
		user.setMailAddress(mailAddress);
		user.setPassword(password);

		insertUser(user);
		return "redirect:/Login.html"; // 登録成功時にログイン画面にリダイレクト
	}

	public void insertUser(SignupUser user) {
		String sqlGetMaxId = "SELECT MAX(userid) FROM userinformation"; // 最大のuseridを取得
	    String sqlInsert = "INSERT INTO userinformation (userid, username, mailAddress, password) VALUES (?, ?, ?, ?)";
	    String dbUrl = "jdbc:postgresql://tokushima.data.ise.shibaura-it.ac.jp:5432/group1db";
	    String dbUsername = "al22095";
	    String dbPassword = "bond";

	    try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	         PreparedStatement stmtGetMaxId = conn.prepareStatement(sqlGetMaxId);
	         PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

	        // 最大のuseridを取得
	        int maxUserId = 0;
	        var rs = stmtGetMaxId.executeQuery();
	        if (rs.next()) {
	            maxUserId = rs.getInt(1); // 最大のuserid
	        }

	        // 新しいuseridをインクリメント
	        int newUserId = maxUserId + 1; // インクリメント

	        // 新しいuseridをセット
	        user.setUserId(newUserId);

	        // ユーザー情報を挿入
	        stmtInsert.setInt(1, user.getUserId()); // userid
	        stmtInsert.setString(2, user.getUsername()); // username
	        stmtInsert.setString(3, user.getMailAddress()); // mailaddress
	        stmtInsert.setString(4, user.getPassword()); // password

	        stmtInsert.executeUpdate(); // 挿入を実行

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public class SignupUser {
		private Integer UserId;
		private String Username;
		private String MailAddress;
		private String Password;
		private String RePassword;

		public Integer getUserId() {
			return UserId;
		}

		public String getUsername() {
			return Username;
		}

		public String getMailAddress() {
			return MailAddress;
		}

		public String getPassword() {
			return Password;
		}

		public String getRePassword() {
			return RePassword;
		}

		public void setUserId(Integer userid) {
			this.UserId = userid;
		}

		public void setUsername(String username) {
			this.Username = username;
		}

		public void setMailAddress(String mailaddress) {
			this.MailAddress = mailaddress;
		}

		public void setPassword(String password) {
			this.Password = password;
		}

		public void setRePassword(String repassword) {
			this.RePassword = repassword;
		}

	}
}
