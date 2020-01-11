package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UserDao;

//servlet = server + applet
public class LoginServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		System.out.println("登录： username = "+ username);
		
		UserDao dao = new UserDao();
		int userId =  dao.login(username, password);
		
		if(userId==1){
			//将信息传给客户端
			out.println("success");
			out.println(userId);
		}
		out.flush();
		out.close();
	}

}