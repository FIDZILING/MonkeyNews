package com.DBDao;

import java.sql.*;
import com.DBLink.*;

public class UserDAO {
	// 邮箱登陆
	public String[] userLoginbyEmail(String email, String password) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String[] res = new String[6];
		res[0] = "false";
		try {
			String sql = "select * from userinfo where email = '" + email + "'";// SQL语句,
			state = conn.createStatement();

			rs = state.executeQuery(sql);
			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					res[0] = Integer.toString(rs.getInt("Id"));
					res[1] = rs.getString("username");
					res[2] = rs.getString("email");
					res[3] = rs.getString("telephone");
					res[4] = rs.getString("phourl");
					res[5] = rs.getString("status");
					return res;
				} else {
					return res;
				}
			} else {
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 手机号登陆
	public String[] userLoginbyTelephone(String telephone, String password) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String[] res = new String[6];
		res[0] = "false";
		try {
			String sql = "select * from userinfo where telephone = '" + telephone + "'";// SQL语句,
			state = conn.createStatement();

			rs = state.executeQuery(sql);
			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					res[0] = Integer.toString(rs.getInt("Id"));
					res[1] = rs.getString("username");
					res[2] = rs.getString("email");
					res[3] = rs.getString("telephone");
					res[4] = rs.getString("phourl");
					res[5] = rs.getString("status");
					return res;
				} else {
					return res;
				}
			} else {
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 注册
	public int userSignup(String username, String password, String email, String telephone) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		boolean checkinsert = true;
		try {
			// 第一步验证邮箱
			// 存在相同邮箱返回1
			String sql = "select * from userinfo where email = '" + email + "'";// SQL语句
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				res = 1;
				checkinsert = false;
				return res;
			}
			state.close();
			rs.close();
			// 第二步验证手机号
			// 存在返回2
			sql = "select * from userinfo where telephone = '" + telephone + "'";// SQL语句
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				res = 2;
				checkinsert = false;
				return res;
			}
			state.close();
			rs.close();
			// 第三部都不存在的时候添加用户信息
			// 成功返回1
			// 失败返回-1
			if (checkinsert == true) {
				sql = "insert into userinfo(username,password,email,telephone) values(?,?,?,?)";// SQL语句,
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, password);
				stmt.setString(3, email);
				stmt.setString(4, telephone);
				int i = stmt.executeUpdate();
				if (i == 1) {
					res = 0;
					return res;
				} else {
					res = -1;
					return res;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 修改信息
	public int userUpdateinfo(int Id, String username, String email, String telephone) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		boolean checkupdate = true;
		try {
			// 第一步验证邮箱
			// 存在相同邮箱返回1
			String sql = "select * from userinfo where email = '" + email + "'";// SQL语句
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				if (Id != rs.getInt("Id")) {
					res = 1;
					checkupdate = false;
					return res;
				}
			}
			state.close();
			rs.close();
			// 第二步验证手机号
			// 存在返回2
			sql = "select * from userinfo where telephone = '" + telephone + "'";// SQL语句
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				if (Id != rs.getInt("Id")) {
					res = 2;
					checkupdate = false;
					return res;
				}
			}
			state.close();
			rs.close();
			if (checkupdate == true) {
				sql = "update userinfo set username=?,email=?,telephone=? where Id=?";// SQL语句
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, email);
				stmt.setString(3, telephone);
				stmt.setInt(4, Id);
				int i = stmt.executeUpdate();
				if (i == 1) {
					res = 0;
					return res;
				} else {
					return res;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 修改密码
	public boolean userUpdatepass(int Id, String password) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update userinfo set password=? where Id=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, password);
			stmt.setInt(2, Id);
			int i = stmt.executeUpdate();
			if (i == 1) {
				res = true;
				return res;
			} else {
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}
}
