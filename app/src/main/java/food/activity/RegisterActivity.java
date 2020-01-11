package food.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import java.util.HashMap;
import food.activity.databinding.RegisterActivityBinding;
import food.bean.User;
import food.http.HttpListener;
import food.http.HttpRequest;

public class RegisterActivity extends Activity implements HttpListener{

	String name, pwd, confirmPwd;
	final int http_register = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RegisterActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.register_activity);
		binding.setUser(new User());

		binding.btnRegister.setOnClickListener(v -> {
//			name = binding.etname.getText().toString();
			name = binding.getUser().name;
			pwd = binding.getUser().pwd;
			confirmPwd = binding.getUser().confirmPwd;
			if (name.equals("") || pwd.equals("")) {
				Toast.makeText(RegisterActivity.this, "用户名或密码不能为空!", Toast.LENGTH_LONG).show();
				return;
			}
			if (!pwd.equals(confirmPwd)) {
				Toast.makeText(RegisterActivity.this, "两次密码不一样,请重新输入!", Toast.LENGTH_LONG).show();
				return;
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("password", pwd);
			HttpRequest.loginRquest(RegisterActivity.this, http_register, map, "注册请求"); // 网络请求
		});

		binding.back.setOnClickListener(v -> {
			System.exit(0);// 结束当前应用程序
			Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
			startActivity(intent);
		});
	}

	@Override
	public void onHttpSuccess(int requestCode, Object obj) {
		String result = (String)obj;
		if (result.trim().equals("success")) {
			setTitle("注册成功:" + name + ":" + pwd);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onHttpFail(int requestCode, String errorMsg) {
		
	}

}