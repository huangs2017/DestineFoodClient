package food.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import food.activity.databinding.ProductDetailBinding;
import food.bean.Product;

// 菜品的详细信息
public class ProductDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		Product product = (Product) intent.getSerializableExtra("food");

		ProductDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.product_detail);
		binding.setProduct(product); //
//		binding.setVariable(BR.product, product); //等同于上面一行，这里的BR.product来自布局文件中的变量声明
		binding.txtShop.setText("店铺: " + foodShopTransform(product.shop));
		binding.imgBtnBack.setOnClickListener(v -> finish());
	}

	public String foodShopTransform(int shop) {
		String transform = "";
		if (shop == 0) {
			transform = "鸭子店";
		} else if (shop == 1) {
			transform = "包子店";
		} else if (shop == 2) {
			transform = "咸菜店";
		} else {
			throw new IllegalArgumentException();
		}
		return transform;
	}

}
