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
 * Servlet implementation class UserSignupServlet
 */
@WebServlet("/UserSignupServlet")
public class UserSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserSignupServlet() {
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String username = request.getParameter("username").toString();
		String password = request.getParameter("password").toString();
		String email = request.getParameter("email").toString();
		String telephone = request.getParameter("telephone").toString();
		UserDAO userDAO = new UserDAO();
		PrintWriter out = response.getWriter();
		int resofSignup = userDAO.userSignup(username, password, email, telephone);
		if (resofSignup == -1) {
			out.write("failed");
			System.out.println("插入失败\n");
		}
		if (resofSignup == 0) {
			out.write("success");
			System.out.println("插入成功\n");
		}
		if (resofSignup == 1) {
			out.write("emailexist");
			System.out.println("邮箱存在\n");
		}
		if (resofSignup == 2) {
			out.write("telephoneexist");
			System.out.println("手机存在\n");
		}
	}

}
