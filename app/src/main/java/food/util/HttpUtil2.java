package food.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import food.http.HttpListener;

public class HttpUtil2 {
	private String urlStr;
	private List<NameValuePair> arrayList;
	private HttpListener listener;
	private String interfaceName;
	int requestCode;

	private String tag_in = "HSHttp_in";   // 请求日志
	private String tag_out = "HSHttp_out"; // 返回日志

	public HttpUtil2(String urlStr, List<NameValuePair> arrayList, String interfaceName) {
		this.urlStr = urlStr;
		this.arrayList = arrayList;
		this.interfaceName = interfaceName;
		if (arrayList == null) {
			this.arrayList = new ArrayList<>();
		}
	}

	public void setOnHttpListener(HttpListener httpListener) {
		this.listener = httpListener;
	}

	/** post请求  */
	public void doPost(int requestCode) {
		this.requestCode = requestCode;
		new Thread() {
			public void run() {
				requestStart();
			}
		}.start();
	}

	public void requestStart() {
		Log.i(tag_in, interfaceName + ": "+ urlStr + "\n入参-->" + "\n" + arrayList);
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000); // 请求超时
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000); // 读取超时
		HttpPost httpPost = new HttpPost(urlStr);
		System.out.println("url:   " + urlStr);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(arrayList, "utf-8");
			httpPost.setEntity(entity);

			HttpResponse response = client.execute(httpPost); // 执行post请求
			int resultCode = response.getStatusLine().getStatusCode();
			if (resultCode== 200) {
				String result = EntityUtils.toString(response.getEntity(), "UTF-8");
				Message msg = handler.obtainMessage();
				msg.obj = result;
				handler.sendMessage(msg);
				Log.e(tag_out, interfaceName + ": 返回码-->" + resultCode + "\n出参-->" + "\n" + result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			listener.onHttpSuccess(requestCode, msg.obj);
		}
	};

}
