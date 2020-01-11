package bean;

import util.annotation.AnnotationField;

public class Product {
	
	@AnnotationField(columnName = "foodId", type = "int") 			int id;
	@AnnotationField(columnName = "foodName", type = "String")		String name;
	@AnnotationField(columnName = "price", type = "float")			float price;	// 单价
	@AnnotationField(columnName = "shop", type = "int") 			int shop; 		// 商品所属的店铺
	@AnnotationField(columnName = "profile", type = "String")		String profile;
	@AnnotationField(columnName = "image", type = "String")			String image;
	
	@AnnotationField(columnName = "quantity", type = "int")   		int quantity;
	float sumPrice;	
	
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

	public int getShop() {
		return shop;
	}

	public void setShop(int shop) {
		this.shop = shop;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(float sumPrice) {
		this.sumPrice = sumPrice;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", shop=" + shop + ", profile=" + profile
				+ ", image=" + image + ", quantity=" + quantity + ", sumPrice=" + sumPrice + "]";
	}

}
