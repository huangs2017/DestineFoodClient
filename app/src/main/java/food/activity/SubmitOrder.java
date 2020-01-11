package food.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import java.util.ArrayList;
import java.util.HashMap;
import food.activity.databinding.SubmitOrderActivityBinding;
import food.bean.Product;
import food.http.HttpListener;
import food.http.HttpRequest;
import food.util.db.DBUtil;
import food.util.SPUtils;

public class SubmitOrder extends Activity implements OnClickListener, HttpListener{

	String userName, address, phone, remark;
	ArrayList<Product> cartList = new ArrayList<Product>(); //购物车中的信息
	final int http_submitOrder = 1;
	SubmitOrderActivityBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = DataBindingUtil.setContentView(this, R.layout.submit_order_activity);
		userName = SPUtils.getString(this, "userName", "");
		cartList = DBUtil.getCartList(this); //从数据库获取购物车信息。
		binding.txtName.setText(userName);
		findViewById(R.id.bsubmit).setOnClickListener(this);
		findViewById(R.id.bcancel).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bsubmit:
			address = binding.editAddress.getText().toString();
			phone = binding.editPhone.getText().toString();
			remark = binding.editRemark.getText().toString();
			if (!"".equals(address) && !"".equals(phone) && cartList.size() > 0) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userName", userName);
				map.put("address", address);
				map.put("phone", phone);
				map.put("remark", remark);
				map.put("productList", cartList);
				HttpRequest.submitOrder(this, http_submitOrder, map);
			} 
			else {
				Toast.makeText(SubmitOrder.this, "客户电话、地址为空！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.bcancel:
			Intent intent = new Intent(SubmitOrder.this, CartActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	@Override
	protected void onResume() {
		userName = SPUtils.getString(this, "userName", "");
		super.onResume();
	}

	@Override
	public void onHttpSuccess(int requestCode, Object obj) {
		Toast.makeText(SubmitOrder.this, "已提交至服务器", Toast.LENGTH_SHORT).show();
		DBUtil.deleteAllCart(this);
		finish();
	}

	@Override
	public void onHttpFail(int requestCode, String errorMsg) {
		
	}

}
