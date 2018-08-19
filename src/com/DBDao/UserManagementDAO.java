package com.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.DBLink.DBLink;

public class UserManagementDAO {
	// 查询全部用户列表
	public String[][] findAlluser() {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[][] = new String[5][1000];
		int i = 0;
		try {
			String sql = "select Id,username,email,telephone,status from userinfo";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[0][i] = Integer.toString(rs.getInt("Id"));
				res[1][i] = rs.getString("username");
				res[2][i] = rs.getString("email");
				res[3][i] = rs.getString("telephone");
				res[4][i] = rs.getString("status");
				i++;
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 通过Id查询
	public String[] findUserbyId(int Id) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[] = new String[5];
		res[0] = null;
		try {
			String sql = "select Id,username,email,telephone,status from userinfo where Id='" + Id + "'";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				res[0] = Integer.toString(rs.getInt("Id"));
				res[1] = rs.getString("username");
				res[2] = rs.getString("email");
				res[3] = rs.getString("telephone");
				res[4] = rs.getString("status");
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 修改用户状态
	public boolean userUpdatetype(int Id, String status) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update userinfo set status=? where Id=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, status);
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

	// 删除用户
	public boolean deleteUser(int Id) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from userinfo where Id=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Id);
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

	// 删除用户收藏
	public boolean deleteUserCollect(int userId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewscollect where userId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
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

	// 删除用户评论
	public boolean deleteUserComment(int userId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewscomment where userId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
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

	// 删除用户点赞
	public boolean deleteUserLike(int userId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewslike where userId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
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
