package food.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import food.util.JsonUtil;
import food.util.MyUtil;

public class HttpUtil2 {

	private HttpURLConnection client;
	private String urlStr;
    Map<String, Object> params;
	private String interfaceName;
	private HttpListener listener;
	private int requestCode;

	private String tag_in = "HSHttp_in";   // 请求日志
	private String tag_out = "HSHttp_out"; // 返回日志
	private final int http_success = 1;
	private final int http_fail = 2;

	public void setOnHttpListener(HttpListener httpListener) {
		this.listener = httpListener;
	}

	public HttpUtil2(String urlStr, Map<String, Object> params, String interfaceName) {
		this.urlStr = urlStr;
		this.params = params;
		this.interfaceName = interfaceName;
		if (params == null) {
			this.params = new HashMap();
		}
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

	private void requestStart() {
		Message msg = Message.obtain();
		try {
			URL url = new URL(urlStr);
			client = (HttpURLConnection) url.openConnection();
			client.setRequestMethod("POST");
			client.setConnectTimeout(20000);	// 连接超时
			client.setReadTimeout(25000);		// 读取超时（服务器响应比较慢，增大时间）
			// 发送POST请求必须设置如下两行
			client.setDoOutput(true);
			client.setDoInput(true);

			String paramStr;
			if (urlStr.equals(httpUrl.login) || urlStr.equals(httpUrl.register)) {
				paramStr = MyUtil.map2String(params);
				client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 发送表单数据(默认方式)
			} else {
				paramStr = JsonUtil.bean2Json(params);
				client.setRequestProperty("Content-Type", "application/json"); // 发送json数据
			}

			Log.i(tag_in, interfaceName + ": "+ urlStr + "\n入参-->" + "\n" + paramStr);
			OutputStream outStrm = client.getOutputStream();
			byte[] bs = paramStr.getBytes();
			outStrm.write(bs);
			outStrm.flush();
			outStrm.close();

			int resultCode = client.getResponseCode();
			String result;
			InputStream inStrm;
			if (resultCode == 200) {
				inStrm = client.getInputStream();
				result = MyUtil.stream2String(inStrm);
				msg.what = http_success;
				msg.obj = result;
			} else {
				inStrm = client.getErrorStream();
				result = MyUtil.stream2String(inStrm);
				msg.what = http_fail;
				msg.obj = result;
			}
			Log.e(tag_out, interfaceName + ": 返回码-->" + resultCode + "\n出参-->" + "\n" + result);
		} catch (ConnectTimeoutException e){
			msg.what = http_fail;
			msg.obj = "网络请求超时";
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			handler.sendMessage(msg);
		}
	}


	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case http_success:
					if (listener != null) {
						listener.onHttpSuccess(requestCode, msg.obj);
					}
					break;
				case http_fail:
					if (listener != null) {
						listener.onHttpFail(requestCode, msg.obj.toString());
					}
					break;
			}
		};
	};

//	入参-->[username=hs, password=hs]
}

