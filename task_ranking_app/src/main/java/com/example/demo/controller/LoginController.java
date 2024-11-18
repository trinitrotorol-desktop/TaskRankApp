package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class LoginController {
	
	@GetMapping("/login")
	private String Login(){
		return "redirect:/Login.html";
	}
	
	@PostMapping("/authenticate")
    private String authenticate(@RequestParam("MailAddress") String MailAddress, @RequestParam("Password") String Password, HttpSession session) {
        // Userクラスをインスタンス化してユーザー名とパスワードをセット
        
        // ここでユーザーをデータベースと照合する処理を実装する
        // 仮にデータベース接続がある場合は、userを使って認証処理を行います。
        Integer UserID = checkUserCredentials(MailAddress, Password);

     // 認証処理
        if (UserID != null) {
        	LoginUser user = new LoginUser(UserID, MailAddress, Password);
        	session.setAttribute("loggedInUser", user);
            return "redirect:/mypage"; // 認証成功
        } else {
            return "redirect:/login?error=true"; // 認証失敗
        }
    }
	
	// 資格情報をチェックするメソッド
    private Integer checkUserCredentials(String mailAddress, String password) {
        String dbUrl = "jdbc:postgresql://tokushima.data.ise.shibaura-it.ac.jp:5432/group1db";
        String dbUsername = "al22095";
        String dbPassword = "bond";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT UserID FROM UserInformation WHERE MailAddress = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mailAddress);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("UserID"); // ユーザーが存在する場合はIDを返す
            }
        } catch (Exception e) {
            e.printStackTrace(); // エラーログの出力
        }
        return null;
    }
	
	public class LoginUser {
		private Integer UserID;
		private String MailAddress;
		private String Password;
		
		public LoginUser(Integer UserID, String email, String password) {
			this.UserID = UserID;
			this.MailAddress = email;
			this.Password = password;
		}
		public Integer getUserID() {
			return UserID;
		}
		
		public void setUserID(Integer UserID) {
			this.UserID = UserID;
		}
		
		public String getEmail() {
			return MailAddress;
		}
		
		public void setEmail(String email) {
			this.MailAddress = email;
		}
		
		public String getPassword() {
			return Password;
		}
		
		public void setPassword(String password) {
			this.Password = password;
		}
		
	}
}


