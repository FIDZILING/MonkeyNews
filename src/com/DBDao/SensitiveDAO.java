package com.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.DBLink.DBLink;

public class SensitiveDAO {
	// 查看全部敏感词
	public String[] findAllsensitive() {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[] = new String[1000];
		int i = 0;
		try {
			String sql = "select words from sensitivelist";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[i] = rs.getString("words");
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
	
	//添加敏感词
	public boolean addSensitive(String words) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "insert into sensitivelist(words) values(?)";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, words);
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
	
	//删除敏感词
	public boolean deleteSensitive(String words) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from sensitivelist where words=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, words);
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
