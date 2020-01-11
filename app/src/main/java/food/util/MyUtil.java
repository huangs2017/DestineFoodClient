package food.util;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import food.activity.R;

public class MyUtil {

	@BindingAdapter({"imageUrl"}) // DataBinding自定义属性@BindingAdapter
	public static void loadImage(ImageView view, String url) {
		if (url == null) {
			view.setImageResource(R.mipmap.app_logo);
		} else {
			Glide.with(view.getContext()).load(url).into(view);
		}
	}

	public static String stream2String(InputStream inStream) throws Exception {
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
		String readLine;
		StringBuffer sb = new StringBuffer();
		while ((readLine = responseReader.readLine()) != null) {
			sb.append(readLine).append("\n");
		}
		responseReader.close();
		String result = sb.toString();
		return result;
	}

	public static String map2String(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		Set<String> keySet  = params.keySet();
		for(String key: keySet){
			if(first) {
				first = false;
			} else {
				result.append("&");
			}
			String value = (String) params.get(key);

			try {
				result.append(URLEncoder.encode(key,"UTF-8"));
				result.append("=");
				result.append(URLEncoder.encode(value,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

}
