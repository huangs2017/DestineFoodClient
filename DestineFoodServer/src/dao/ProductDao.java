package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.Product;
import bean.Order;
import com.mysql.jdbc.Statement;
import util.DButil;
import util.annotation.DealAnnotation;

public class ProductDao {

	// 获取所有商品
	public List<Product> getAllFood() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Product> foodList = null;
		String sql = "select * from food";
		try {
			conn = DButil.getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			foodList = DealAnnotation.resultSet2List(rs, Product.class);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DButil.closeStatement(pstmt);
			DButil.closeConnection(conn);
		}
		return foodList;
	}

	// 添加订单信息并返回orderId,以便稍后插入到food_order表中当外键
	public int addOrder(Order order) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int orderId = 0;
		String sql = "insert into orders(userName, address, phone, remark) values(?, ?, ?, ?)";
		try {
			conn = DButil.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 申明在执行的时候会返回主键
			pstmt.setString(1, order.getUserName());
			pstmt.setString(2, order.getAddress());
			pstmt.setString(3, order.getPhone());
			pstmt.setString(4, order.getRemark());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys(); // 获取最后插入记录的自增id值
			rs.next();
			orderId = rs.getInt(1);
			System.out.println("orderId--------" + orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DButil.closeStatement(pstmt);
			DButil.closeConnection(conn);
		}
		return orderId;
	}

	// 添加购物车中商品的id,订单的id,商品的数量
	public void addFoodOrder(int orderId, List<Product> productList) {
		Connection conn = DButil.getConnection();
		for(Product food : productList) {
			PreparedStatement pstmt = null;
			String sql = "insert into food_order(foodId, orderId, quantity) values(?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, food.getId());
				pstmt.setInt(2, orderId);
				pstmt.setFloat(3, food.getQuantity());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DButil.closeStatement(pstmt);
		}
		DButil.closeConnection(conn);
	}

	public ArrayList<Product> findProductByOrderId(int orderId) {
		ArrayList<Product> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "select food.foodName, food.price, food_order.quantity from food, food_order "
				+ "where food.foodId=food_order.foodId and food_order.orderId=" + orderId;
		conn = DButil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			list = DealAnnotation.resultSet2List(rs, Product.class);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DButil.closeStatement(pstmt);
			DButil.closeConnection(conn);
		}
		return list;
	}

}
