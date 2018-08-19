package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.*;
import com.News.News;
import com.News.NewsCollect;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class NewsServlet
 */
@SuppressWarnings("serial")
@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		// 新闻点赞
		if ("newslike".equals(method)) {
			newslike(request, response);
		}
		// 新闻收藏
		if ("newscollect".equals(method)) {
			newscollect(request, response);
		}
		// 新闻浏览输入
		if ("newsinput".equals(method)) {
			newsinput(request, response);
		}
		// 查所有新闻
		if ("findallnews".equals(method)) {
			findallnews(request, response);
		}
		// 查找单个新闻
		if ("findnews".equals(method)) {
			findnews(request, response);
		}
		// 单个用户新闻收藏表
		if ("usercollect".equals(method)) {
			usercollect(request, response);
		}
		// 删除新闻
		if ("delnews".equals(method)) {
			delnews(request, response);
		}
		//删除评论
		if ("delcomment".equals(method)) {
			delcomment(request, response);
		}
		
	}



	private News buildNewsDO(String res[]) {
		News news = new News();
		news.setIslike(Integer.parseInt(res[0]));
		news.setIscollect(Integer.parseInt(res[1]));
		news.setLiketimes(Integer.parseInt(res[2]));
		news.setCommenttimes(Integer.parseInt(res[3]));
		return news;
	}

	private void delcomment(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			int Id = Integer.valueOf(request.getParameter("Id")).intValue();
			int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
			PrintWriter out = response.getWriter();
			NewsDAO newsDAO = new NewsDAO();
			boolean res = newsDAO.deleteoneComment(Id,newsId);
			if (res == true) {
				out.write(String.valueOf(newsId));
			} else {
				out.write("");
			}
	}
	
	private void delnews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		PrintWriter out = response.getWriter();
		NewsDAO newsDAO = new NewsDAO();
		boolean res1 = newsDAO.deleteNews(newsId);
		boolean res2 = newsDAO.deleteNewsCollect(newsId);
		boolean res3 = newsDAO.deleteNewsComment(newsId);
		boolean res4 = newsDAO.deleteNewsLike(newsId);
		if (res1 == true) {
			out.write("success");
		} else {
			out.write("failed");
		}
	}

	private void usercollect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		int userId = Integer.valueOf(request.getParameter("userId")).intValue();
		PrintWriter out = response.getWriter();
		NewsDAO newsDAO = new NewsDAO();
		String[][] res = newsDAO.userNewscollect(userId);
		JSONObject jsonObjectall = new JSONObject();
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		for (int i = 0; res[0][i] != null; i++) {
			String[] resarray = new String[4];
			resarray[0] = res[0][i];
			resarray[1] = res[1][i];
			resarray[2] = res[2][i];
			resarray[3] = res[3][i];
			
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("newsId",resarray[0]);
	        jsonObject.put("newsname",resarray[1]);
	        jsonObject.put("newstext",resarray[2]);
	        jsonObject.put("source",resarray[3]);
	        
	        jsonObjects.add(jsonObject);
	        
			
			if(res[0][i+1] == null) {
		        jsonObjectall.put("number",i+1);
			}
		}
		jsonObjectall.put("data",jsonObjects);
		System.out.print(jsonObjectall.toString());
		out.write(jsonObjectall.toString());
	}

	private void findnews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		PrintWriter out = response.getWriter();
		NewsDAO newsDAO = new NewsDAO();
		String[] res1 = newsDAO.findnews(newsId);
		String[][] res2 = newsDAO.findnewscomment(newsId);
		String htmlinfo = "";
		if (res1[0] == null) {
			htmlinfo = htmlinfo + "<tr>" + "<td>空空如也</td>" + "<td>空空如也</td>" + "<td>空空如也</td>" + "<td>空空如也</td>"
					+ "<td>空空如也</td>" + "<td>空空如也</td>" + "<td>空空如也</td>" + "</tr>";
		} else {
			if (res2[0][0] == null) {
				htmlinfo = htmlinfo + "<tr><td>" + res1[0] + "</td>" + "<td onclick = 'findoneinfo(this)'"
						+ "onmouseover='mouseovername(this)'" + "onmouseout='mouseoutname(this)'>" + res1[1] + "</td>"
						+ "<td>" + res1[2] + "</td>" + "<td>" + res1[3] + "</td>" + "<td>" + res1[4] + "</td>" + "<td>"
						+ res1[5] + "</td>" + "<td><img class='img-del' src='../img/del-before.png'"
						+ "onmouseover=\"this.src='../img/del-after.png'\""
						+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deletenews(this)\"></img></td>"
						+ "</tr>";
				htmlinfo = htmlinfo + "<tr><td  colspan='7'>还没有评论</td></tr>";
			} else {
				htmlinfo = htmlinfo + "<tr><td>" + res1[0] + "</td>" + "<td onclick = 'findoneinfo(this)'"
						+ "onmouseover='mouseovername(this)'" + "onmouseout='mouseoutname(this)'>" + res1[1] + "</td>"
						+ "<td>" + res1[2] + "</td>" + "<td>" + res1[3] + "</td>" + "<td>" + res1[4] + "</td>" + "<td>"
						+ res1[5] + "</td>" + "<td><img class='img-del' src='../img/del-before.png'"
						+ "onmouseover=\"this.src='../img/del-after.png'\""
						+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deletenews(this)\"></img></td>"
						+ "</tr>";

				for (int i = 0; res2[0][i] != null; i++) {
					htmlinfo = htmlinfo + "<tr>" + "<td colspan='6' class='td-comment'>" + "<div>用户" + res2[1][i]
							+ "说："+ res2[2][i]+"<br>评论时间："+ res2[3][i] + "</div>" + "</td>"
							+ "<td><img class='img-del' src='../img/del-before.png'"
							+ "onmouseover=\"this.src='../img/del-after.png'\""
							+ "onmouseout=\"this.src='../img/del-before.png'\""
							+ "onclick=\"deletecomment(" + res2[0][i] + ",this)\"></img></td>" + "</tr>";
				}
			}
		}
		htmlinfo = htmlinfo + "<tr>" + "<td colspan='7'><img class='img-back' src='../img/back-before.png'"
				+ "onmouseover=\"this.src='../img/back-after.png'\""
				+ "onmouseout=\"this.src='../img/back-before.png'\"" + "onclick=\"callback()\"></img></td>" + "</tr>";
		;
		out.write(htmlinfo);
	}

	private void findallnews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		NewsDAO newsDAO = new NewsDAO();
		String[][] res = newsDAO.findAllnews();
		String htmlinfo = "";
		if (res[0][0] == null) {
			htmlinfo = "<tr>" + "<td>空空如也</td>" + "<td>空空如也</td>" + "<td>空空如也</td>" + "<td>空空如也</td>" + "<td>空空如也</td>"
					+ "<td>空空如也</td>" + "<td>空空如也</td>" + "</tr>";
		} else {
			for (int i = 0; res[0][i] != null; i++) {
				htmlinfo = htmlinfo + "<tr><td>" + res[0][i] + "</td>" + "<td onclick = 'findoneinfo(this)'"
						+ "onmouseover='mouseovername(this)'" + "onmouseout='mouseoutname(this)'>" + res[1][i] + "</td>"
						+ "<td>" + res[2][i] + "</td>" + "<td>" + res[3][i] + "</td>" + "<td>" + res[4][i] + "</td>"
						+ "<td>" + res[5][i] + "</td>" + "<td><img class='img-del' src='../img/del-before.png'"
						+ "onmouseover=\"this.src='../img/del-after.png'\""
						+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deletenews(this)\"></img></td>"
						+ "</tr>";
			}
		}
		out.write(htmlinfo);
	}

	private void newsinput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int userId = Integer.valueOf(request.getParameter("userId")).intValue();
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		String newsname = request.getParameter("newsname").toString();
		String newstext = request.getParameter("newstext").toString();
		String source = request.getParameter("source").toString();
		NewsDAO newsDAO = new NewsDAO();
		boolean res = newsDAO.NewsRead(userId, newsId, newsname, newstext, source);
		String[] resarray = new String[4];
		String newsJson = null;
		if (res == true) {
			// 浏览成功 获取点赞次数、评论次数、是否点赞、是否收藏
			// 是否点赞
			int islike = newsDAO.isLike(userId, newsId);
			if (islike == 1) {
				resarray[0] = String.valueOf(islike);
			} else {
				resarray[0] = String.valueOf(0);
			}
			// 是否收藏
			int iscollect = newsDAO.isCollect(userId, newsId);
			if (iscollect == 1) {
				resarray[1] = String.valueOf(iscollect);
			} else {
				resarray[1] = String.valueOf(0);
			}
			// 点赞次数
			int liketimes = newsDAO.likeTimes(newsId);
			resarray[2] = String.valueOf(liketimes);
			// 评论次数
			int commenttimes = newsDAO.commentTimes(newsId);
			resarray[3] = String.valueOf(commenttimes);
			// 封装到json
			newsJson = JSON.toJSONString(buildNewsDO(resarray));

			out.write(newsJson);
			System.out.println(newsJson);
			System.out.print("查看新闻成功\n");
		} else {
			out.write("failed");
			System.out.print("查看新闻失败\n");
		}
	}

	private void newscollect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int userId = Integer.valueOf(request.getParameter("userId")).intValue();
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		NewsDAO newsDAO = new NewsDAO();
		String[] resarray = new String[4];
		String newsJson = null;
		boolean res = newsDAO.NewsCollect(userId, newsId);
		if (res == true) {
			// 收藏/取消收藏成功 获取点赞次数、评论次数、是否点赞、是否收藏
			// 是否点赞
			int islike = newsDAO.isLike(userId, newsId);
			if (islike == 1) {
				resarray[0] = String.valueOf(islike);
			} else {
				resarray[0] = String.valueOf(0);
			}
			// 是否收藏
			int iscollect = newsDAO.isCollect(userId, newsId);
			if (iscollect == 1) {
				resarray[1] = String.valueOf(iscollect);
			} else {
				resarray[1] = String.valueOf(0);
			}
			// 点赞次数
			int liketimes = newsDAO.likeTimes(newsId);
			resarray[2] = String.valueOf(liketimes);
			// 评论次数
			int commenttimes = newsDAO.commentTimes(newsId);
			resarray[3] = String.valueOf(commenttimes);

			// 封装到json
			newsJson = JSON.toJSONString(buildNewsDO(resarray));

			out.write(newsJson);
			System.out.println(newsJson);
			System.out.print("收藏/取消收藏成功\n");
		} else {
			out.write("failed");
			System.out.print("收藏/取消收藏失败\n");
		}

	}

	private void newslike(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int userId = Integer.valueOf(request.getParameter("userId")).intValue();
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		NewsDAO newsDAO = new NewsDAO();
		String[] resarray = new String[4];
		String newsJson = null;
		boolean res = newsDAO.NewsLike(userId, newsId);

		if (res == true) {
			// 点赞/取消点赞成功 获取点赞次数、评论次数、是否点赞、是否收藏
			// 是否点赞
			int islike = newsDAO.isLike(userId, newsId);
			if (islike == 1) {
				resarray[0] = String.valueOf(islike);
			} else {
				resarray[0] = String.valueOf(0);
			}
			// 是否收藏
			int iscollect = newsDAO.isCollect(userId, newsId);
			if (iscollect == 1) {
				resarray[1] = String.valueOf(iscollect);
			} else {
				resarray[1] = String.valueOf(0);
			}
			// 点赞次数
			int liketimes = newsDAO.likeTimes(newsId);
			resarray[2] = String.valueOf(liketimes);
			// 评论次数
			int commenttimes = newsDAO.commentTimes(newsId);
			resarray[3] = String.valueOf(commenttimes);

			// 封装到json
			newsJson = JSON.toJSONString(buildNewsDO(resarray));

			out.write(newsJson);
			System.out.println(newsJson);
			System.out.print("点赞/取消点赞成功\n");
		} else {
			out.write("failed");
			System.out.print("点赞/取消点赞失败\n");
		}

	}

}
