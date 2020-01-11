package food.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import food.bean.Product;
import food.util.db.DBUtil;
import food.util.annotation.BindView;
import food.util.annotation.DealAnnotation;

public class ModifyCart extends Activity {

	@BindView(R.id.txt_name) 		TextView txt_name;
	@BindView(R.id.txt_price) 		TextView txt_price;
	@BindView(R.id.edit_num)		EditText edit_num;
	@BindView(R.id.btn_modify)		Button btn_modify;

	private Product cart;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_activity);
		DealAnnotation.bind(this);

		Intent intent = getIntent();
		cart = (Product) intent.getSerializableExtra("food");
		
		txt_name.setText("商品名称: " + cart.getName());
		txt_price.setText("商品单价: " + cart.getPrice());
		edit_num.setText(cart.getQuantity() + "");
		
		btn_modify.setOnClickListener(v -> {
			int quantity = Integer.parseInt(edit_num.getText().toString());
			DBUtil.modifyCart(ModifyCart.this, cart.getId(), quantity);

//				Intent intent = getIntent();
//				intent.putExtra("name", "");
//				setResult(RESULT_FIRST_USER, intent);
			finish();
		});
		
	}	

}
