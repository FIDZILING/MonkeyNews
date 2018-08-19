package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.*;

/**
 * Servlet implementation class UserManagementServlet
 */
@SuppressWarnings("serial")
@WebServlet("/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		if ("findalluser".equals(method)) {
			findalluser(request, response);
		}
		if ("finduser".equals(method)) {
			finduser(request, response);
		}
		if ("upuserstatus".equals(method)) {
			upuserstatus(request, response);
		}
		if ("deleteuser".equals(method)) {
			deleteuser(request, response);
		}
	}

	private void deleteuser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		UserManagementDAO userManagementDAO = new UserManagementDAO();
		boolean res1 = userManagementDAO.deleteUser(Id);
		boolean res2 = userManagementDAO.deleteUserCollect(Id);
		boolean res3 = userManagementDAO.deleteUserComment(Id);
		boolean res4 = userManagementDAO.deleteUserLike(Id);
		if (res1 == true) {
			out.write("success");
		} else {
			out.write("failed");
		}
	}

	private void upuserstatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		String status = request.getParameter("status");
		UserManagementDAO userManagementDAO = new UserManagementDAO();
		boolean res = userManagementDAO.userUpdatetype(Id, status);
		if (res == true) {
			out.write("success");
		} else {
			out.write("failed");
		}

	}

	private void finduser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String res[] = new String[5];
		String htmlinfo = "";
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		UserManagementDAO userManagementDAO = new UserManagementDAO();
		res = userManagementDAO.findUserbyId(Id);
		if (res[0] == null) {
			htmlinfo = "<tr>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>"
					+ "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "</tr>";
		} else {
			htmlinfo = htmlinfo + "<tr>" + "<td>" + res[0] + "</td>" + "<td>" + res[1] + "</td>" + "<td>" + res[2]
					+ "</td>" + "<td>" + res[3] + "</td>" + "<td>";
			if (res[4].equals("正常")) {
				htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
						+ "<option value = '正常' selected = 'selected'>正常</option>" + "<option value = '禁言'>禁言</option>"
						+ "<option value = '封号'>封号</option>"
						+ "</select>";
			} else if (res[4].equals("禁言")) {
				htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
						+ "<option value = '正常'>正常</option>" + "<option value = '禁言' selected = 'selected'>禁言</option>"
						+ "<option value = '封号'>封号</option>"
						+ "</select>";
			} else if (res[4].equals("封号")) {
				htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
						+ "<option value = '正常'>正常</option>" + "<option value = '禁言'>禁言</option>"
						+ "<option value = '封号' selected = 'selected'>封号</option>"+ "</select>";
			}
			htmlinfo = htmlinfo + "</td>" + "<td><img class='img-del' src='../img/del-before.png' "
					+ "onmouseover=\"this.src='../img/del-after.png'\""
					+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deleteuser(this)\"></img></td>"
					+ "<div style='display: none;'>" + res[0] + "</div>" + "</tr>";
		}
		out.write(htmlinfo);
	}

	private void findalluser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String res[][] = new String[5][1000];
		String htmlinfo = "";
		UserManagementDAO userManagementDAO = new UserManagementDAO();
		res = userManagementDAO.findAlluser();
		if (res[0][0] == null) {
			htmlinfo = "<tr>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>"
					+ "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "</tr>";
		} else {
			for (int i = 0; res[0][i] != null; i++) {
				htmlinfo = htmlinfo + "<tr>" + "<td>" + res[0][i] + "</td>" + "<td>" + res[1][i] + "</td>" + "<td>"
						+ res[2][i] + "</td>" + "<td>" + res[3][i] + "</td>" + "<td>";
				if (res[4][i].equals("正常")) {
					htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
							+ "<option value = '正常' selected = 'selected'>正常</option>"
							+ "<option value = '禁言'>禁言</option>" + "<option value = '封号'>封号</option>"
							+ "</select>";
				} else if (res[4][i].equals("禁言")) {
					htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
							+ "<option value = '正常'>正常</option>"
							+ "<option value = '禁言' selected = 'selected'>禁言</option>"
							+ "<option value = '封号'>封号</option>"+ "</select>";
				} else if (res[4][i].equals("封号")) {
					htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
							+ "<option value = '正常'>正常</option>" + "<option value = '禁言'>禁言</option>"
							+ "<option value = '封号' selected = 'selected'>封号</option>"
							+ "</select>";
				}
				htmlinfo = htmlinfo + "</td>" + "<td><img class='img-del' src='../img/del-before.png' "
						+ "onmouseover=\"this.src='../img/del-after.png'\""
						+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deleteuser(this)\"></img>"
						+ "<div style='display: none;'>" + res[0][i] + "</div>" + "</td>" + "</tr>";
			}
		}
		out.write(htmlinfo);

	}

}
