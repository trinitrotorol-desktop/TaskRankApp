package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/")
@Controller
public class LoginController {
	
	@GetMapping("/login")
	private String Login(){
		return "/Login.html";
	}
	
	@PostMapping("/authenticate")
    private String authenticate(@RequestParam("MailAddress") String MailAddress, @RequestParam("Password") String Password) {
        // Userクラスをインスタンス化してユーザー名とパスワードをセット
        LoginUser user = new LoginUser(MailAddress, Password);
        
        // ここでユーザーをデータベースと照合する処理を実装する
        // 仮にデータベース接続がある場合は、userを使って認証処理を行います。

     // 認証処理
        if (checkUserCredentials(user)) {
            return "redirect:/MyPage.html"; // 認証成功
        } else {
            return "redirect:/login?error=true"; // 認証失敗
        }
    }
	
	// 資格情報をチェックするメソッド
    private boolean checkUserCredentials(LoginUser user) {
        String dbUrl = "jdbc:postgresql://tokushima.data.ise.shibaura-it.ac.jp:5432/group1db";
        String dbUsername = "al22095";
        String dbPassword = "bond";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT COUNT(*) FROM UserInformation WHERE MailAddress = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // ユーザーが存在する場合はtrueを返す
            }
        } catch (Exception e) {
            e.printStackTrace(); // エラーログの出力
        }
        return false;
    }
	
	public class LoginUser {
		private String MailAddress;
		private String Password;
		
		public LoginUser(String email, String password) {
			this.MailAddress = email;
			this.Password = password;
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


