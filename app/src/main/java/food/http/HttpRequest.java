package food.http;

import android.widget.ImageView;

import java.util.Map;

// 封装的HTTP连接
public class HttpRequest {

	// 以键值对的形式发请求
	public static void loginRquest(HttpListener listener, int requestCode, Map<String, Object> params, String interfaceName) {
		HttpUtil http = null;
		switch (interfaceName) {
		case "登录请求":
			http =  new HttpUtil(httpUrl.login, params, interfaceName);
			break;
		case "注册请求":
			http =  new HttpUtil(httpUrl.register, params, interfaceName);
			break;
		}
		http.setOnHttpListener(listener);
		http.doPost(requestCode);
	}

	
	public static void getAllFood(HttpListener listener, int requestCode) {
		HttpUtil http = new HttpUtil(httpUrl.allFood, null, "获取全部菜品");
		http.setOnHttpListener(listener);
		http.doPost(requestCode);
	}
	
	
	public static void submitOrder(HttpListener listener, int requestCode, Map<String, Object> params) {
		HttpUtil http = new HttpUtil(httpUrl.addOrder, params, "提交订单");
		http.setOnHttpListener(listener);
		http.doPost(requestCode);
	}

}
