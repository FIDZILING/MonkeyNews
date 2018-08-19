package com.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.DBLink.DBLink;

public class AdminDAO {
	// 管理员登录
	public String adminLogin(String adminname, String adminpass) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String admintype = null;
		try {
			String sql = "select * from admininfo where adminname = '" + adminname + "'";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				if (rs.getString("adminpass").equals(adminpass)) {
					admintype = rs.getString("admintype");
					return admintype;
				} else {
					return admintype;
				}
			} else {
				return admintype;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return admintype;
	}

	// 查询全部管理员列表
	public String[][] findAlladmin() {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[][] = new String[3][1000];
		int i = 0;
		try {
			String sql = "select Id,adminname,admintype from admininfo";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[0][i] = Integer.toString(rs.getInt("Id"));
				res[1][i] = rs.getString("adminname");
				res[2][i] = rs.getString("admintype");
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
	public String[] findAdminbyId(int Id) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[] = new String[3];
		res[0] = null;
		try {
			String sql = "select * from admininfo where Id='" + Id + "'";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				res[0] = Integer.toString(rs.getInt("Id"));
				res[1] = rs.getString("adminname");
				res[2] = rs.getString("admintype");
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 修改管理员身份
	public boolean adminUpdatetype(int Id, String admintype) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update admininfo set admintype=? where Id=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, admintype);
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

	// 添加管理员
	public boolean insertAdmin(String adminname, String adminpass, String admintype) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "insert into admininfo(adminname,adminpass,admintype) values(?,?,?)";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, adminname);
			stmt.setString(2, adminpass);
			stmt.setString(3, admintype);
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

	// 删除管理员
	public boolean delAdmin(int Id) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from admininfo where Id=?";// SQL语句
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
}
