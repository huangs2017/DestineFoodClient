package food.http;

public class httpUrl{
	
	//服务器地址
	public final static String service = "http://10.1.1.201:8080/DestineFoodServer/"; 	//家
//	public final static String service = "http://192.168.0.90:8080/DestineFoodServer/"; //公司
//	public final static String service = "http://39.97.232.0:8080/DestineFoodServer/";
//	public final static String service = "http://www.huangsong.work:8080/DestineFoodServer/";
	
//	google服务器
//	public final static String service = "http://34.92.147.217:8080/DestineFoodServer/";
	
	
	//服务器接口
	public final static String login = service + "login";
	public final static String register = service + "register";
	public final static String allFood = service + "getAllFood";
	public final static String images = service + "images/";
	public final static String addOrder = service + "addOrder";



	
}
