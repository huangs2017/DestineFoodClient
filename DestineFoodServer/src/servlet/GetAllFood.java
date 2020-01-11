package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.Product;
import dao.ProductDao;
import util.JsonUtil;

public class GetAllFood extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ProductDao dao = new ProductDao();
		List<Product> foodList = dao.getAllFood();
		String json = JsonUtil.bean2Json(foodList);
		out.print(json);
		System.out.println( "GetAllFood--->" + foodList);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
