package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyUtil {

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

	/* 得到图片字节流数组。
    	当你选择用Reader来输出数据的时候，就决定了是字符流的形式，实际上Reader在把数据给你之前已经进行了处理。
    	所以如果你要处理字节流，就必须用Stream来做。
	*/

	public static byte[] stream2Bytes(InputStream inStream) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		try {
			while ( (len = inStream.read(bytes)) != -1 ) {
				outStream.write(bytes, 0, len);
			}
			outStream.close();
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outStream.toByteArray();
	}

}
