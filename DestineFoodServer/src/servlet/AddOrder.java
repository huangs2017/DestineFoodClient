package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Product;
import bean.Order;
import dao.ProductDao;
import util.JsonUtil;

public class AddOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

//		ServletInputStream is = request.getInputStream();
//		InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
//		BufferedReader buffReader = new BufferedReader(isReader);
		BufferedReader buffReader = request.getReader();
		StringBuilder strBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = buffReader.readLine()) != null) {
			strBuilder.append(inputStr);
		}
		String param = strBuilder.toString();
		System.out.println("下单---> " + param);
		Order order = JsonUtil.json2Bean(param, Order.class);
		
		System.out.println("姓名    地址    电话    备注");
		System.out.println(order.getUserName() + "    " + order.getAddress() + "     " + order.getPhone() + "   "
				+ order.getRemark());

		ProductDao dao = new ProductDao();
		int orderId = dao.addOrder(order);
		dao.addFoodOrder(orderId, order.getProductList());// 插入到t_food_order表中去。
		ArrayList<Product> productList = dao.findProductByOrderId(orderId);
		System.out.println("菜名    价格    份数");
		for (Product food : productList) {
			System.out.println(food.getName() + "     " + food.getPrice() + "    " + food.getQuantity());
		}
		// 没有返回客户端数据
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}