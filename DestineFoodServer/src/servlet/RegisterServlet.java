package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.User;
import dao.UserDao;

public class RegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		System.out.println("注册： username = "+ username);
		
		User user = new User(username, password);
		UserDao dao = new UserDao();
		int a = dao.addUser(user);
		if(a==0)
			out.println("success");
		else
			out.print("add failue");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
