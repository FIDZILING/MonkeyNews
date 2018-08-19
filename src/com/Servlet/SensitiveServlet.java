package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.SensitiveDAO;

/**
 * Servlet implementation class SensitiveServlet
 */
@SuppressWarnings("serial")
@WebServlet("/SensitiveServlet")
public class SensitiveServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		if ("findallsensitive".equals(method)) {
			findallsensitive(request, response);
		}
		if ("addsensitive".equals(method)) {
			addsensitive(request, response);
		}
		if ("delsensitive".equals(method)) {
			delsensitive(request, response);
		}
	}

	private void delsensitive(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String words = request.getParameter("words");
		SensitiveDAO sensitiveDAO = new SensitiveDAO();
		boolean res = sensitiveDAO.deleteSensitive(words);
		if (res == true) {
			out.write("删除成功");
		} else {
			out.write("删除失败");
		}
	}

	private void addsensitive(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String words = request.getParameter("words");
		SensitiveDAO sensitiveDAO = new SensitiveDAO();
		boolean res = sensitiveDAO.addSensitive(words);
		if (res == true) {
			out.write("添加成功");
		} else {
			out.write("添加失败");
		}
	}

	private void findallsensitive(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		SensitiveDAO sensitiveDAO = new SensitiveDAO();
		String res[] = sensitiveDAO.findAllsensitive();
		String htmlinfo = "";
		if (res[0] == null) {
			htmlinfo = "<div class='div-list'>空空如也</div>";
		} else {
			for (int i = 0; res[i] != null; i++) {
				htmlinfo = htmlinfo + "<div class='div-list'><font>" + res[i]
						+ "</font><img class='img-del' src='../img/del-before.png'"
						+ "onmouseover=\"this.src='../img/del-after.png'\" "
						+ "onmouseout=\"this.src='../img/del-before.png'\" "
						+ "onclick=\"delsensitiveword(this)\"></img></div>" + "</div>";
			}
		}
		htmlinfo = htmlinfo + "<div class='div-list-input'><input type='text' class='add-sensitive' />"
				+ "<img class='img-add' src='../img/add-before.png'"
				+ "onmouseover=\"this.src='../img/add-after.png'\" "
				+ "onmouseout=\"this.src='../img/add-before.png'\" "
				+ "onclick=\"inputsensitiveword(this)\"></img></div>";
		out.write(htmlinfo);
	}

}
