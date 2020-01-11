package food.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonUtil {

    /** json -> Bean */
    public static <E> E json2Bean(String json, Class<E> tClass) {
        E object = JSON.parseObject(json, tClass);
        return object;
    }

    /** json -> BeanList */
    public static <E> List<E> json2BeanList(String json, Class<E> tClass) {
        List<E> list = new ArrayList<E>();
        if (json != null) {
            list = JSON.parseArray(json, tClass);
        }
        return list;
    }

    /** 取出json串里指定的key对应的String */
    public static String getString(String json, String key) {
        return JSON.parseObject(json).getString(key);
    }

    /** 取出json串里指定的key对应的int */
    public static int getInt(String json, String key) {
        return JSON.parseObject(json).getIntValue(key);
    }




    /** Bean -> json
     *  map -> json
     */
    public static <E> String bean2Json(E obj) {
        return JSON.toJSONString(obj);
    }

    /** JSONObj -> json */
    public static String JSONObj2Json(JSONObject JSONObj) {
        return JSONObj.toString();
    }




    /** json -> map
     *
     *  Intent跳转时，不能传递Map，可以传递HashMap
     *  因为HashMap implements Serializable
     */
    public static HashMap json2Map(String json) {
        return JSON.parseObject(json, HashMap.class);
    }


    /** 注意事项：
     *  类中自定义构造方法时，必须要有默认的构造方法，不然fastjson解析报错
     */
}