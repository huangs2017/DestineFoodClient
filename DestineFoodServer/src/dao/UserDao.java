package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bean.User;
import util.DButil;

public class UserDao {

	public int login(String user, String psw) {
		String sql = "select * from user where userName=? and userPassword=?";
		Connection conn = DButil.getConnection();
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			pstate.setString(1, user);
			pstate.setString(2, psw);
			System.out.println("查寻user表");
			ResultSet rs = pstate.executeQuery();
			if (rs.next()) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int addUser(User user) {
		Connection conn = DButil.getConnection();
		String sql = "insert into user(userName, userPassword) values(?, ?)";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			pstate.setString(1, user.getName());
			pstate.setString(2, user.getPassword());
			pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
