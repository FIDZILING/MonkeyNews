package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.UserDAO;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String username = request.getParameter("username").toString();
		String email = request.getParameter("email").toString();
		String telephone = request.getParameter("telephone").toString();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		UserDAO userDAO = new UserDAO();
		PrintWriter out = response.getWriter();
		int res = userDAO.userUpdateinfo(Id, username, email, telephone);
		if (res == -1) {
			out.write("failed");
			System.out.println("修改失败\n");
		}
		if (res == 0) {
			out.write("success");
			System.out.println("修改成功\n");
		}
		if (res == 1) {
			out.write("emailexist");
			System.out.println("邮箱存在\n");
		}
		if (res == 2) {
			out.write("telephoneexist");
			System.out.println("手机存在\n");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
