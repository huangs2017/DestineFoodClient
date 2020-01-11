package food.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import food.activity.databinding.CartActivityBinding;
import food.adapter.CommonAdapter;
import food.bean.Product;
import food.util.db.DBUtil;

public class CartActivity extends AppCompatActivity {

	MutableLiveData<List> liveCartList = new MutableLiveData<>();
	ArrayList<Product> cartList = new ArrayList<Product>();

	DBUtil db;
	Product product;
	static final int dialog1 = 1;	//删除或修改选中的商品
	static final int dialog2 = 2;	//清空购物车中的商品
	int myRequestCode= 100;
	CartActivityBinding binding;
	Context ctx;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		binding = DataBindingUtil.setContentView(this, R.layout.cart_activity);
		binding.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				product = cartList.get(position);
				showDialog(dialog1);
			}
		});
		initEvent();

		CommonAdapter<Product> commonAdapter = new CommonAdapter<>(this, cartList, R.layout.cart_adapter, food.activity.BR.product);
		binding.setAdapter(commonAdapter);
//		binding.setLifecycleOwner(this);

		liveCartList.observe(this, new Observer<List>() {
            @Override
            public void onChanged(List liveCartList) {
				commonAdapter.setData(cartList);
				float totalPrices = 0; //总金额
				for (int i = 0; i < cartList.size(); i++) { //得到所有商品总的金额
					product = cartList.get(i);
					totalPrices = totalPrices + product.price * product.quantity;
				}
				binding.txtTotalPrice.setText(totalPrices + ""); // txt_total_price 显示所有商品的总金额
            }
        });
	}


	private void initEvent() {
		cartList = DBUtil.getCartList(ctx); //获取数据库中所有的数据
		liveCartList.setValue(cartList);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);
		switch (id) {
		case dialog1: {
			return builder1(this);
		}
		case dialog2: {
			return builder2(this);
		}
		}
		return null;
	}

	private Dialog builder1(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("修改或删除");
		builder.setIcon(R.mipmap.modify);
		builder.setPositiveButton("修改", (dialog, which) -> {
			Intent intent = new Intent(CartActivity.this, ModifyCart.class);
			intent.putExtra("food", product);
			startActivityForResult(intent, myRequestCode);
		});
		builder.setNegativeButton("删除", (dialog, which) -> {
			DBUtil.deleteCart(ctx, product.getId());
			initEvent();
		});
		return builder.create();
	}

	private Dialog builder2(Context context) { //清空购物车
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("删除确认");
		builder.setIcon(R.mipmap.delete);
		builder.setMessage("真的要删除吗?");
		builder.setPositiveButton("确认", (dialog, which) -> {
			db.deleteAllCart(ctx);
			initEvent();
		});
		builder.setNegativeButton("取消", (dialog, which) -> setTitle("取消按钮按下"));
		return builder.create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, Menu.FIRST + 1, 1, "清空购物车").setIcon(R.mipmap.delete);
		menu.add(2, Menu.FIRST + 2, 2, "继续购物").setIcon(R.mipmap.goshop);
		menu.add(3, Menu.FIRST + 3, 3, "下订单").setIcon(R.mipmap.submitorder);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case Menu.FIRST + 1: // 清空购物车
			showDialog(dialog2);
			break;
		case Menu.FIRST + 2: // 继续购物
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case Menu.FIRST + 3: // 下订单
			Intent intent2 = new Intent(this, SubmitOrder.class);
			startActivity(intent2);
			finish();
			break;
		}
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == myRequestCode && resultCode == RESULT_FIRST_USER) {
//			// 地址信息的绑定
//			if (data.hasExtra("name")) {
//				remark = data.getStringExtra("content");
//				txt_remark.setText("订单备注: " + remark);
//			}
//		}
		if (requestCode == myRequestCode) {
			initEvent();
		}
	}


}
