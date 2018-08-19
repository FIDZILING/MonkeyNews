package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.DBDao.NewsDAO;
import com.DBDao.SensitiveDAO;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class CommentServlet
 */
@SuppressWarnings("serial")
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		if ("getcomment".equals(method)) {
			getcomment(request, response);
		}
		if ("onenewscomment".equals(method)) {
			onenewscomment(request, response);
		}
	}

	private void onenewscomment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		System.out.println(newsId);
		NewsDAO newsDAO = new NewsDAO();
		String[][] res = newsDAO.findnewscomment(newsId);
		JSONObject jsonObjectall = new JSONObject();
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		for (int i = 0; res[0][i] != null; i++) {
			String[] resarray = new String[4];
			resarray[0] = res[0][i];
			resarray[1] = res[1][i];
			resarray[2] = res[2][i];
			resarray[3] = res[3][i];

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Id", resarray[0]);
			jsonObject.put("username", resarray[1]);
			jsonObject.put("comment", resarray[2]);
			jsonObject.put("commenttime", resarray[3]);

			jsonObjects.add(jsonObject);

			if (res[0][i + 1] == null) {
				jsonObjectall.put("number", i + 1);
			}
		}
		jsonObjectall.put("data", jsonObjects);
		System.out.print(jsonObjectall.toString());
		out.write(jsonObjectall.toString());
	}

	private void getcomment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String comment = request.getParameter("comment").toString();
		int newsId = Integer.valueOf(request.getParameter("newsId")).intValue();
		int userId = Integer.valueOf(request.getParameter("userId")).intValue();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String commenttime=df.format(new Date());
		SensitiveDAO sensitiveDAO = new SensitiveDAO();
		String[] Sensitive = sensitiveDAO.findAllsensitive();
		comment = replace(comment, Sensitive);
		PrintWriter out = response.getWriter();
		NewsDAO newsDAO = new NewsDAO();
		boolean res = newsDAO.NewsComment(userId, newsId, comment,commenttime);
		if(res == true) {
			out.write("success");
		}
		else {
			out.write("failed");
		}
	}

	private String replace(String sentence, String[] Sensitive) {
		for (int i = 0; Sensitive[i] != null; i++) {
			int num = Sensitive[i].length();
			String patt = "";
			for (int j = 0; j < num; j++) {
				patt = patt + "*";
			}
			sentence = sentence.replace(Sensitive[i], patt);
		}
		return sentence;
	}

}
