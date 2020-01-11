package food.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import food.bean.Product;
import food.util.annotation.DealAnnotation;

public class DBUtil {

	public static SQLiteDatabase db;
	static String tableName = DBHelper.tableName; 	//购物车表名
	// cart表的字段
	static final String[] cartColumns = new String[] { "foodId", "foodName", "price", "quantity", "image" };
	
	// 增加cart表的商品
	public static long add2Cart(Context ctx, Product product) {
		db = DBHelper.getInstance(ctx).db;
		ArrayList<Product>  productList = getCartList(ctx);
		for (Product cartProduct : productList) {
			if (product.id == cartProduct.id) {
				return modifyCart(ctx, cartProduct.id, product.quantity);
			}
		}
		ContentValues value = new ContentValues();
		value.put("foodId", product.getId());
		value.put("foodName", product.getName());
		value.put("price", product.getPrice());
		value.put("quantity", product.getQuantity());
		return db.insert(tableName, null, value);
	}
	
	// 删除cart表的商品
	public static int deleteCart(Context ctx, int foodId) {
		db = DBHelper.getInstance(ctx).db;
		return db.delete(tableName, "foodId=" + foodId, null);
	}

	// 清空cart表
	public static int deleteAllCart(Context ctx) {
		db = DBHelper.getInstance(ctx).db;
		return db.delete(tableName, null, null);
	}
	
	// 修改cart表的商品
	public static int modifyCart(Context ctx, int foodId, int quantity) {
		db = DBHelper.getInstance(ctx).db;
		ContentValues value = new ContentValues();
		value.put("quantity", quantity);
		return db.update(tableName, value, "foodId=" + foodId, null);
	}

	// 得到cart表的所有数据
	public static ArrayList<Product> getCartList(Context ctx) {
		db = DBHelper.getInstance(ctx).db;
		Cursor cursor = db.query(tableName, cartColumns, null, null, null, null, null);
		ArrayList<Product> arrayList = DealAnnotation.cursor2List(cursor, Product.class);


		return arrayList;
	}
	
}
