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
 * Servlet implementation class UserUppassServlet
 */
@WebServlet("/UserUppassServlet")
public class UserUppassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserUppassServlet() {
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
		String password = request.getParameter("password").toString();
		int Id = Integer.valueOf(request.getParameter("Id")).intValue();
		UserDAO userDAO = new UserDAO();
		PrintWriter out = response.getWriter();
		boolean res = userDAO.userUpdatepass(Id, password);
		if (res == true) {
			out.write("success");
			System.out.print("修改成功\n");
		} else {
			out.write("failed");
			System.out.print("修改失败\n");
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
