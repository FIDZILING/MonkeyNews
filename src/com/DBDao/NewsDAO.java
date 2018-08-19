package com.DBDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.DBLink.DBLink;

public class NewsDAO {
	// 新闻浏览
	public boolean NewsRead(int userId, int newsId, String newsname, String newstext, String source) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int readtimes = 0;
		boolean res = false;
		try {
			// 第一步查新闻Id
			String sql = "select newsId from newsinfo where newsId='" + newsId + "'";// SQL语句
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				// 存在第二步查访问次数+1
				// 查看访问次数
				readtimes = readTimes(newsId);
				if (readtimes != -1) {
					// 浏览次数+1
					res = updateReadTimes(newsId, readtimes);
					return res;
				} else {
					return res;
				}
			}
			// 不存在则插入
			else {
				sql = "insert into newsinfo(newsId,newsname,readtimes,newstext,source) values(?,?,?,?,?)";// SQL语句
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, newsId);
				stmt.setString(2, newsname);
				stmt.setInt(3, 1);
				stmt.setString(4, newstext);
				stmt.setString(5, source);
				int i = stmt.executeUpdate();
				if (i == 1) {
					res = true;
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

	// 查找浏览次数
	public int readTimes(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		try {
			String sql = "select readtimes from newsinfo where newsId='" + newsId + "'";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int readtimes = rs.getInt("readtimes");
				return readtimes;
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

	// 浏览次数+1并插入
	public boolean updateReadTimes(int newsId, int readtimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set readtimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, readtimes + 1);
			stmt.setInt(2, newsId);
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

	/* 点赞类 */
	// 新闻点赞
	public boolean NewsLike(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			// 第一步，先查看用户是否点赞
			int islike = isLike(userId, newsId);
			// 第二步，没点过为-1，插入点赞记录
			if (islike == -1) {
				String sql = "insert into usernewslike(userId,newsId) values(?,?)";// SQL语句
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, userId);
				stmt.setInt(2, newsId);
				int i = stmt.executeUpdate();
				if (i == 1) {
					int liketimes = likeTimes(newsId);
					if (liketimes != -1) {
						res = updateLikeTimes(newsId, liketimes);
						return res;
					} else {
						return res;
					}
				} else {
					return res;
				}
			}
			// 为0，取消过点赞，恢复
			else if (islike == 0) {
				// 恢复点赞
				boolean res1 = reLike(userId, newsId);
				// 获取点赞次数
				int liketimes = likeTimes(newsId);
				// 点赞次数+1
				boolean res2 = updateLikeTimes(newsId, liketimes);
				if (res1 == true && res2 == true) {
					res = true;
				}
				return res;
			}
			// 为1，取消点赞
			else if (islike == 1) {
				// 取消点赞
				boolean res1 = delLike(userId, newsId);
				// 获取点赞次数
				int liketimes = likeTimes(newsId);
				// 点赞次数-1
				boolean res2 = updateLikeTimeslowdown(newsId, liketimes);
				if (res1 == true && res2 == true) {
					res = true;
				}
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 查找点赞次数
	public int likeTimes(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		try {
			String sql = "select liketimes from newsinfo where newsId='" + newsId + "'";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int readtimes = rs.getInt("liketimes");
				return readtimes;
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

	// 点赞次数+1
	public boolean updateLikeTimes(int newsId, int liketimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set liketimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			liketimes++;
			stmt.setInt(1, liketimes);
			stmt.setInt(2, newsId);
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

	// 点赞次数-1
	public boolean updateLikeTimeslowdown(int newsId, int liketimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set liketimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			liketimes--;
			stmt.setInt(1, liketimes);
			stmt.setInt(2, newsId);
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

	// 查看用户是否点赞
	public int isLike(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		try {
			String sql = "select islike from usernewslike where userId='" + userId + "' and newsId='" + newsId + "'";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int islike = rs.getInt("islike");
				return islike;
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

	// 取消点赞
	public boolean delLike(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update usernewslike set islike=0 where userId=? and newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, newsId);
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

	// 恢复点赞
	public boolean reLike(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update usernewslike set islike=1 where userId=? and newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, newsId);
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

	/* 收藏类 */
	// 新闻收藏
	public boolean NewsCollect(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			// 第一步，查看用户是否收藏过
			int iscollect = isCollect(userId, newsId);
			// 为-1，插入用户收藏
			if (iscollect == -1) {
				String sql = "insert into usernewscollect(userId,newsId) values(?,?)";// SQL语句
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, userId);
				stmt.setInt(2, newsId);
				int i = stmt.executeUpdate();
				if (i == 1) {
					int collecttimes = collectTimes(newsId);
					if (collecttimes != -1) {
						res = updateCollectTimes(newsId, collecttimes);
						return res;
					} else {
						return res;
					}
				} else {
					return res;
				}
			}
			// 为0，取消过收藏，恢复
			else if (iscollect == 0) {
				// 恢复收藏
				boolean res1 = reCollect(userId, newsId);
				// 获取收藏次数
				int collecttimes = collectTimes(newsId);
				// 收藏次数+1
				boolean res2 = updateCollectTimes(newsId, collecttimes);
				if (res1 == true && res2 == true) {
					res = true;
				}
				return res;
			}
			// 为1，取消收藏
			else if (iscollect == 1) {
				// 取消收藏
				boolean res1 = delCollect(userId, newsId);
				// 获取收藏次数
				int collecttimes = collectTimes(newsId);
				// 收藏次数-1
				boolean res2 = updateCollectTimeslowdown(newsId, collecttimes);
				if (res1 == true && res2 == true) {
					res = true;
				}
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 查找收藏次数
	public int collectTimes(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		try {
			String sql = "select collecttimes from newsinfo where newsId='" + newsId + "'";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int readtimes = rs.getInt("collecttimes");
				return readtimes;
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

	// 收藏次数+1
	public boolean updateCollectTimes(int newsId, int collecttimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set collecttimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, collecttimes + 1);
			stmt.setInt(2, newsId);
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

	// 收藏次数-1
	public boolean updateCollectTimeslowdown(int newsId, int collecttimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set collecttimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, collecttimes - 1);
			stmt.setInt(2, newsId);
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

	// 查看用户是否收藏
	public int isCollect(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		try {
			String sql = "select iscollect from usernewscollect where userId='" + userId + "' and newsId='" + newsId
					+ "'";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int iscollect = rs.getInt("iscollect");
				return iscollect;
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

	// 取消收藏
	public boolean delCollect(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update usernewscollect set iscollect=0 where userId=? and newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, newsId);
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

	// 恢复收藏
	public boolean reCollect(int userId, int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update usernewscollect set iscollect=1 where userId=? and newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, newsId);
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

	// 获取单个用户的收藏列表
	public String[][] userNewscollect(int userId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[][] = new String[4][1000];
		int i = 0;
		try {
			String sql = "select newsinfo.newsId,newsname,newstext,source from newsinfo join usernewscollect where usernewscollect.newsId=newsinfo.newsId and usernewscollect.userId='"
					+ userId + "' and usernewscollect.iscollect=1";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[0][i] = Integer.toString(rs.getInt("newsId"));
				res[1][i] = rs.getString("newsname");
				res[2][i] = rs.getString("newstext");
				res[3][i] = rs.getString("source");
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

	/* 评论类 */
	// 新闻评论
	public boolean NewsComment(int userId, int newsId, String comment, String commenttime) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "insert into usernewscomment(userId,newsId,comment,commenttime) values(?,?,?,?)";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, newsId);
			stmt.setString(3, comment);
			stmt.setString(4, commenttime);
			int i = stmt.executeUpdate();
			if (i == 1) {
				int liketimes = commentTimes(newsId);
				res = updateCommentTimes(newsId, liketimes);
				return res;
			} else {
				return res;
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 查找评论次数
	public int commentTimes(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		int res = -1;
		try {
			String sql = "select commenttimes from newsinfo where newsId='" + newsId + "'";
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				int readtimes = rs.getInt("commenttimes");
				return readtimes;
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

	// 评论次数+1
	public boolean updateCommentTimes(int newsId, int commenttimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set commenttimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commenttimes + 1);
			stmt.setInt(2, newsId);
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

	// 评论次数-1
	public boolean updateCommentTimeslowdown(int newsId, int commenttimes) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "update newsinfo set commenttimes=? where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commenttimes - 1);
			stmt.setInt(2, newsId);
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
	
	//评论删除
	public boolean deleteoneComment(int Id,int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewscomment where Id=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Id);
			int i = stmt.executeUpdate();
			if (i == 1) {
				int commenttimes = commentTimes(newsId);
				res = updateCommentTimeslowdown(newsId, commenttimes);
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
	
	// 获取全部新闻
	public String[][] findAllnews() {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[][] = new String[6][1000];
		int i = 0;
		try {
			String sql = "select * from newsinfo";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[0][i] = Integer.toString(rs.getInt("newsId"));
				res[1][i] = rs.getString("newsname");
				res[2][i] = Integer.toString(rs.getInt("readtimes"));
				res[3][i] = Integer.toString(rs.getInt("liketimes"));
				res[4][i] = Integer.toString(rs.getInt("collecttimes"));
				res[5][i] = Integer.toString(rs.getInt("commenttimes"));
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

	// 获取单个新闻
	public String[] findnews(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[] = new String[6];
		try {
			String sql = "select * from newsinfo where newsId='" + newsId + "'";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			if (rs.next()) {
				res[0] = Integer.toString(rs.getInt("newsId"));
				res[1] = rs.getString("newsname");
				res[2] = Integer.toString(rs.getInt("readtimes"));
				res[3] = Integer.toString(rs.getInt("liketimes"));
				res[4] = Integer.toString(rs.getInt("collecttimes"));
				res[5] = Integer.toString(rs.getInt("commenttimes"));
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBLink.close(rs, state, conn);
		}
		return res;
	}

	// 获取单个新闻评论
	public String[][] findnewscomment(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		String res[][] = new String[4][1000];
		int i = 0;
		try {
			String sql = "select usernewscomment.Id,userinfo.username,usernewscomment.comment,usernewscomment.commenttime "
					+ "from usernewscomment inner join userinfo where userinfo.Id=usernewscomment.userId and usernewscomment.newsId='"
					+ newsId + "' order by commenttime";// SQL语句,
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				res[0][i] = rs.getString("Id");
				res[1][i] = rs.getString("username");
				res[2][i] = rs.getString("comment");
				res[3][i] = rs.getString("commenttime");
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

	/* 删除新闻 */
	// 删除新闻
	public boolean deleteNews(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from newsinfo where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, newsId);
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

	// 删除新闻收藏
	public boolean deleteNewsCollect(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewscollect where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, newsId);
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

	// 删除新闻评论
	public boolean deleteNewsComment(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewscomment where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, newsId);
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

	// 删除新闻点赞
	public boolean deleteNewsLike(int newsId) {
		Connection conn = DBLink.getConn();
		Statement state = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			String sql = "delete from usernewslike where newsId=?";// SQL语句
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, newsId);
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
