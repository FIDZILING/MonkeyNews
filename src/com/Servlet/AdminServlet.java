package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.AdminDAO;

/**
 * Servlet implementation class AdminServlet
 */
@SuppressWarnings("serial")
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 设置字符集，避免乱码
		response.setContentType("text/html;charset=UTF-8");
		String method = request.getParameter("method");
		if ("login".equals(method)) {
			login(request, response);
		}
		if ("findalladmin".equals(method)) {
			findalladmin(request, response);
		}
		if ("findadmin".equals(method)) {
			findadmin(request, response);
		}
		if ("upadmintype".equals(method)) {
			upadmintype(request, response);
		}
		if ("insertadmin".equals(method)) {
			insertadmin(request, response);
		}
		if ("deladmin".equals(method)) {
			deladmin(request, response);
		}
	}

	private void deladmin(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		AdminDAO adminDAO = new AdminDAO();
		boolean res = adminDAO.delAdmin(Id);
		if (res == true) {
			out.write("success");
		} else {
			out.write("failed");
		}
	}

	private void insertadmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String adminname = request.getParameter("adminname").toString();
		String adminpass = request.getParameter("adminpass").toString();
		String admintype = request.getParameter("admintype").toString();
		AdminDAO adminDAO = new AdminDAO();
		boolean res = adminDAO.insertAdmin(adminname, adminpass, admintype);
		if (res == true) {
			out.write("success");
		} else {
			out.write("failed");
		}
	}

	private void upadmintype(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		String admintype = request.getParameter("admintype");
		AdminDAO adminDAO = new AdminDAO();
		boolean res = adminDAO.adminUpdatetype(Id, admintype);
		if (res == true) {
			out.write("success");
		} else {
			out.write("failed");
		}
	}

	private void findadmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		String res[] = new String[3];
		String htmlinfo = "";
		AdminDAO adminDAO = new AdminDAO();
		res = adminDAO.findAdminbyId(Id);
		if (res[0] == null) {
			htmlinfo = "<tr>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>"
					+ "</tr>";
		} else {
			htmlinfo = htmlinfo + "<tr>" + "<td>" + res[0] + "</td>" + "<td>" + res[1] + "</td>" + "<td>";
			if (res[2].equals("管理员管理")) {
				htmlinfo = htmlinfo + res[2];
			} else if (res[2].equals("用户管理")) {
				htmlinfo = htmlinfo + "<select class='sel-type' onchange='change()'>"
						+ "<option value = '用户管理' selected = 'selected'>用户管理</option>"
						+ "<option value = '新闻管理'>新闻管理</option>" + "</select>"
						+ "<img class='img-del' src='../img/del-before.png'"
						+ "onmouseover=\"this.src='../img/del-after.png'\""
						+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deleteadmin(this)\"></img>";
			} else if (res[2].equals("新闻管理")) {
				htmlinfo = htmlinfo + "<select class='sel-type' onchange='change()'>"
						+ "<option value = '用户管理'>用户管理</option>"
						+ "<option value = '新闻管理' selected = 'selected'>新闻管理</option>" + "</select>"
						+ "<img class='img-del' src='../img/del-before.png'"
						+ "onmouseover=\"this.src='../img/del-after.png'\""
						+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deleteadmin(this)\"></img>";
			}
			htmlinfo = htmlinfo + "</td>" + "</tr>";
		}
		out.write(htmlinfo);
	}

	private void findalladmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String res[][] = new String[3][1000];
		String htmlinfo = "";
		AdminDAO adminDAO = new AdminDAO();
		res = adminDAO.findAlladmin();
		if (res[0][0] == null) {
			htmlinfo = "<tr>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>" + "<td>" + "空空如也" + "</td>"
					+ "</tr>";
		} else {
			for (int i = 0; res[0][i] != null; i++) {
				htmlinfo = htmlinfo + "<tr>" + "<td>" + res[0][i] + "</td>" + "<td>" + res[1][i] + "</td>" + "<td>";
				if (res[2][i].equals("管理员管理")) {
					htmlinfo = htmlinfo + res[2][i];
				} else if (res[2][i].equals("用户管理")) {
					htmlinfo = htmlinfo + "<select id='sel-type' class='sel-type' onchange='change()'>"
							+ "<option value = '用户管理' selected = 'selected'>用户管理</option>"
							+ "<option value = '新闻管理'>新闻管理</option>" + "</select>"
							+ "<img class='img-del' src='../img/del-before.png'"
							+ "onmouseover=\"this.src='../img/del-after.png'\""
							+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deleteadmin(this)\"></img>";
				} else if (res[2][i].equals("新闻管理")) {
					htmlinfo = htmlinfo + "<select class='sel-type' onchange='change()'>"
							+ "<option value = '用户管理'>用户管理</option>"
							+ "<option value = '新闻管理' selected = 'selected'>新闻管理</option>" + "</select>"
							+ "<img class='img-del' src='../img/del-before.png'"
							+ "onmouseover=\"this.src='../img/del-after.png'\""
							+ "onmouseout=\"this.src='../img/del-before.png'\"" + "onclick=\"deleteadmin(this)\"></img>";
				}
				htmlinfo = htmlinfo + "</td>" + "</tr>";

			}
		}
		htmlinfo = htmlinfo + "<tr>" + "<td><img class='img-inputadmin' src='../img/add-before.png' "
				+ "onmouseover=\"this.src='../img/add-after.png'\"" + "onmouseout=\"this.src='../img/add-before.png'\""
				+ "onclick='inputadmin(this)'>" + "</img></td>"
				+ "<td>账号：<input class='inputadmin-text' type='text'/><br>密码：<input class='inputadmin-text' type='text'/></td>"
				+ "<td><select class='sel-type'>" + "<option value = '用户管理' selected = 'selected'>用户管理</option>"
				+ "<option value = '新闻管理'>新闻管理</option>" + "</select></td>" + "</tr>";
		out.write(htmlinfo);
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String adminname = request.getParameter("adminname");
		String adminpass = request.getParameter("adminpass");
		AdminDAO adminDAO = new AdminDAO();
		String admintype = adminDAO.adminLogin(adminname, adminpass);
		if (admintype != null) {
			System.out.println("登录成功");
			out.write(admintype);
			request.getSession().setAttribute("admintype", admintype);
			request.getSession().setAttribute("adminname", adminname);
			System.out.println(request.getSession().getAttribute("admintype"));
		} else if (admintype == null) {
			out.write("登录失败！");
			System.out.println("登录失败");
		}

	}

}
