package food.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import food.activity.databinding.ProductFragmentBinding;
import food.adapter.CommonAdapter;
import food.bean.Product;
import food.http.HttpListener;
import food.http.HttpRequest;
import food.util.JsonUtil;

//显事内容根据从MainTabActivity里选择的tab
public class MyFragment extends Fragment implements HttpListener{

	CommonAdapter<Product> commonAdapter;
	ArrayList<Product> productList = new ArrayList<Product>();
	int shop;
	MainActivity activity;
	final int http_getAllFood = 1;


	public MyFragment(int shop) {
		this.shop = shop;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ProductFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false);
		activity = (MainActivity)getActivity();
		commonAdapter = new CommonAdapter<Product>(activity, productList, R.layout.product_adapter, food.activity.BR.product);
		binding.setAdapter(commonAdapter);

		HttpRequest.getAllFood(this, http_getAllFood);//获取菜品
		binding.grid.setOnItemClickListener((parent, view, position, id) -> {
			for (Product product : productList) {
				product.checked = false;
			}
			Product product = productList.get(position);
			product.checked = true;
			commonAdapter.setData(productList);
			activity.product = product;
			activity.openOptionsMenu();
		});
		return binding.getRoot();
	}


	@Override
	public void onHttpSuccess(int requestCode, Object obj) {
		String result = (String) obj;
		List<Product> allProductList = JsonUtil.json2BeanList(result, Product.class);
		Product product;
		Iterator<Product> it = allProductList.iterator();
		while (it.hasNext()) {
			product = it.next();
			if (product.shop == shop) {
				productList.add(product);
			}
		}
		commonAdapter.setData(productList);
	}
	
	@Override
	public void onHttpFail(int requestCode, String errorMsg) {
		
	}

}
