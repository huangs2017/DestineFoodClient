package food.bean;

import java.io.Serializable;

import food.http.httpUrl;
import food.util.annotation.AnnotationField;
import food.util.annotation.AnnotationTable;

// foodType设定为3种: 0:鸭子, 1:包子, 2:咸菜
@AnnotationTable("shopcart")
public class Product implements Serializable {
	
	@AnnotationField(columnName = "foodId", type = "int") 			public int id;
	@AnnotationField(columnName = "foodName", type = "String")		public String name;
	@AnnotationField(columnName = "price", type = "float")			public float price;	// 单价
	@AnnotationField(columnName = "image", type = "String")			public String image;

	public String profile;	// 简介
	public int shop;  		// 所属店铺

	//购物车用到
	@AnnotationField(columnName = "quantity", type = "int")			public int quantity;

	public boolean checked;  		// 所属店铺

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = httpUrl.images + image;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public int getShop() {
		return shop;
	}

	public void setShop(int shop) {
		this.shop = shop;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
