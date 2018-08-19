package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.ReportDAO;

/**
 * Servlet implementation class ReportServlet
 */
@SuppressWarnings("serial")
@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		if ("findallreport".equals(method)) {
			findallreport(request, response);
		}
		if ("addreport".equals(method)) {
			addreport(request, response);
		}
		if ("delreport".equals(method)) {
			delreport(request, response);
		}
	}

	private void delreport(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		ReportDAO reportDAO = new ReportDAO();
		boolean res = reportDAO.deleteReport(Id);
	}

	private void addreport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int decommentId = Integer.valueOf(request.getParameter("Id")).intValue();
		String reason = request.getParameter("reason").toString();
		ReportDAO reportDAO = new ReportDAO();
		boolean res = reportDAO.addReport(decommentId, reason);
		if(res == true) {
			out.write("success");
		}
		else {
			out.write("failed");
		}
	}

	private void findallreport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ReportDAO reportDAO = new ReportDAO();
		String[][] res = reportDAO.findAllreport();
		String htmlinfo = "";
		if (res[0][0] == null) {
			htmlinfo = "<tr>" + "<td colspan='7'>没有举报信息</td>" + "</tr>";
		} else {
			for (int i = 0; res[0][i] != null; i++) {
				htmlinfo = htmlinfo + "<tr>" + "<td><input type='checkbox' name='check' class='checkbox'/></td>" + "<td>" + res[4][i]
						+ "</td>" + "<td>" + res[0][i] + "<td>" + res[5][i] + "</td>"+ "<td>" + res[1][i] + "</td>" + "<td>" + res[2][i] + "</td>" + "<td>" + res[3][i]
						+ "</td>" + "</tr>";
			}
		}
		out.write(htmlinfo);

	}

}
