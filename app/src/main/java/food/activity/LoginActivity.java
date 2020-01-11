package food.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import food.http.HttpListener;
import food.http.HttpRequest;
import food.util.SPUtils;

public class LoginActivity extends Activity implements HttpListener{

	private static final int DIALOG = 1;
	private String name, pwd;
	private final int http_login = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDialog(DIALOG);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);
		View dialogView = LayoutInflater.from(this).inflate(R.layout.login_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialogView);
		builder.setTitle("hs订餐系统!");
		builder.setIcon(R.mipmap.loginimage);
		
		final EditText edit_name = dialogView.findViewById(R.id.edit_name);
		final EditText edit_pwd = dialogView.findViewById(R.id.edit_pwd);
		TextView txt_register = dialogView.findViewById(R.id.txt_register);

		builder.setPositiveButton("Enter", (dialog, which) -> {
			name = edit_name.getText().toString();
			pwd = edit_pwd.getText().toString();
			if (name.equals("") || pwd.equals("")) {
				Toast.makeText(LoginActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
				removeDialog(DIALOG);
				showDialog(DIALOG);
			} else {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				map.put("password", pwd);
				HttpRequest.loginRquest(LoginActivity.this, http_login, map, "登录请求"); // 网络请求
			}
		});

		builder.setNegativeButton("Exit", (dialog, which) -> {
			setTitle("Exit");
			LoginActivity.this.finish();
		});

		txt_register.setOnClickListener(v -> {
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
		});
		return builder.create();
	}


	@Override
	public void onHttpSuccess(int requestCode, Object obj) {
		String result = (String)obj;
		String resultArray[] = result.split("\n");

		if (resultArray.length == 2 && resultArray[0].equals("success")) {
			SPUtils.putString(LoginActivity.this, "userName", name); // 把name放入共享参数
			Intent intent = new Intent(this, MainActivity.class);
			Log.i("testLog", "-----" + resultArray[0]);
			startActivity(intent); // 登录成功跳转到系统主界面
			finish();
		} else {
			removeDialog(DIALOG);
			showDialog(DIALOG);
			Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onHttpFail(int requestCode, String errorMsg) {
		
	}

}