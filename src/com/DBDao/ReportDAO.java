package com.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.DBLink.DBLink;

public class ReportDAO {
	//查找全部举报信息
	//信息列表
	//被举报Id 被举报用户状态 评论内容 举报理由
	public String[][] findAllreport() {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[][] = new String[6][1000];
		int i = 0;
		try {
			String sql = "select userreport.Id,userinfo.Id,userinfo.username,userinfo.status,usernewscomment.comment,reason from userreport inner join userinfo inner join usernewscomment "
					+ "where userinfo.Id=usernewscomment.userId and userreport.decommentId=usernewscomment.Id order by userreport.Id";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[0][i] = String.valueOf(rs.getInt("userinfo.Id"));
				res[1][i] = rs.getString("status");
				res[2][i] = rs.getString("comment");
				res[3][i] = rs.getString("reason");
				res[4][i] = String.valueOf(rs.getInt("userreport.Id"));
				res[5][i] = rs.getString("userinfo.username");
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
	
	//添加举报信息
	public boolean addReport(int decommentId,String reason) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "insert into userreport(decommentId,reason) values(?,?)";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, decommentId);
			stmt.setString(2, reason);
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
	
	//删除举报信息
		public boolean deleteReport(int Id) {
			Connection conn = DBLink.getConn();
			Statement state = null;
			ResultSet rs = null;
			boolean res = false;
			try {
				String sql = "delete from userreport where Id=?";// SQL语句
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
