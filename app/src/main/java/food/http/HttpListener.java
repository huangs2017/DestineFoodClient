package food.http;

/** 网络请求回调 */

public interface HttpListener {
	void onHttpSuccess(int requestCode, Object obj);

	void onHttpFail(int requestCode, String errorMsg);
}
