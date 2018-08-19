package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBDao.UserDAO;
import com.User.User;
import com.alibaba.fastjson.JSON;



/**
 * Servlet implementation class UserLogin
 */
@WebServlet("/UserLogin")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private User buildUserDO(String res[]){  
        User user = new User();
        user.setId(Integer.parseInt(res[0]));
        user.setUsername(res[1]);
        user.setEmail(res[2]);
        user.setTelephone(res[3]);
        user.setPhourl(res[4]);
        user.setStatus(res[5]);
        user.setResult("success");
        return user;  
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String username = request.getParameter("username").toString();
		String password = request.getParameter("password").toString();
	   	UserDAO userDAO = new UserDAO();
	   	PrintWriter out = response.getWriter();
	   	String[] isLoginEmail = userDAO.userLoginbyEmail(username, password);
	   	String[] isLoginTelephone = userDAO.userLoginbyTelephone(username, password);
	   	String userJson = null;
	   	if(isLoginEmail[0] != "false" || isLoginTelephone[0] != "false") {
	   		String[] res = new String[6];
	   		if(isLoginEmail[0] != "false") {
	   			res[0] = isLoginEmail[0];
	   			res[1] = isLoginEmail[1];
	   			res[2] = isLoginEmail[2];
	   			res[3] = isLoginEmail[3];
	   			res[4] = isLoginEmail[4];
	   			res[5] = isLoginEmail[5];
	   			userJson = JSON.toJSONString(buildUserDO(res)); 
	   		}
	   		else if(isLoginTelephone[0] != "false") {
	   			res[0] = isLoginTelephone[0];
	   			res[1] = isLoginTelephone[1];
	   			res[2] = isLoginTelephone[2];
	   			res[3] = isLoginTelephone[3];
	   			res[4] = isLoginTelephone[4];
	   			res[5] = isLoginTelephone[5];
	   			userJson = JSON.toJSONString(buildUserDO(res)); 
	   		}
        	request.getSession().setAttribute("Id", res[0]);
        	request.getSession().setAttribute("username", res[1]);
        	request.getSession().setAttribute("email", res[2]);
        	request.getSession().setAttribute("telephone", res[3]);
        	request.getSession().setAttribute("phourl", res[4]);
        	request.getSession().setAttribute("status", res[5]);
        	
			out.write(userJson);
			System.out.println(userJson);
			System.out.print("登录成功\n");
	   	}
	   	else {
	   		out.write("failed"); 
            System.out.print("登录失败\n");
	   	}
	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
