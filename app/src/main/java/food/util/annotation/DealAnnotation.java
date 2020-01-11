package food.util.annotation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class DealAnnotation {

    /** 解析注解，获取控件 */
    public static void bind(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields(); // 获得成员变量
        for (Field field : fields) {
//          当无注解时 field.getAnnotations().length = 0
            if (field.getAnnotations().length > 0 && field.isAnnotationPresent(BindView.class)) { //判断注解
                field.setAccessible(true);
                BindView bindView = field.getAnnotation(BindView.class);
                View targetView = activity.findViewById(bindView.value());
                try {
                    field.set(activity, targetView); // 将注解的id，注入成员变量中
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 解析注解，获取控件 */
    public static void bind(Object obj, View view) {
        Field[] fields = obj.getClass().getDeclaredFields(); // 获得成员变量
        for (Field field : fields) {
//          当无注解时 field.getAnnotations().length = 0
            if (field.getAnnotations().length > 0 && field.isAnnotationPresent(BindView.class)) { //判断注解
                field.setAccessible(true);
                BindView bindView = field.getAnnotation(BindView.class);
                View targetView = view.findViewById(bindView.value());
                try {
                    field.set(obj, targetView); // 将注解的id，注入成员变量中
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /** Cursor -> List */
	@SuppressLint("NewApi") 
	public static <E> ArrayList<E> cursor2List(Cursor cursor, Class<E> clazz) {
		Vector<String> columns = getColumnName(cursor);
		cursor.moveToFirst();
		ArrayList<E> arrayList = new ArrayList<E>();
		for (int i = 0; i < cursor.getCount(); i++) {
			E obj = null;
			try {
				obj = clazz.newInstance();
				obj = cursor2Obj(cursor, obj, columns);
			} catch (Exception e) {
				e.printStackTrace();
			}
			arrayList.add(obj);
			cursor.moveToNext();
		}
		cursor.close();
		return arrayList;
	}
	
    /** Cursor -> Object
     * 要定义泛型方法，只需将泛型参数列表置于返回值前
     */
	public static <E> E cursor2Obj(Cursor cursor, E obj, Vector<String> columns) throws IllegalAccessException {
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields(); // 获得成员变量
		for (Field field : fields) {
			if (field.getAnnotations().length > 0 && field.isAnnotationPresent(AnnotationField.class)) { // 判断注解
				field.setAccessible(true);
				AnnotationField annotationField = field.getAnnotation(AnnotationField.class);
				String columnName = annotationField.columnName();
				int  columnIndex = cursor.getColumnIndex(columnName);

				String type = annotationField.type();
//				 Log.i("testLog", "--- " + field.getType());
//				 Log.i("testLog", "=== " + field.getName());
				if (columns.contains(columnName)) {
					switch (type) {
						case "int":
							int i = cursor.getInt(columnIndex);
							field.set(obj, i);
							break;
						case "float":
							float f = cursor.getFloat(columnIndex);
							field.set(obj, f);
							break;
						case "double":
							double d = cursor.getDouble(columnIndex);
							field.set(obj, d);
							break;
						case "String":
							String s = cursor.getString(columnIndex);
							field.set(obj, s);
							break;
					}
				}
			}
		}
		return obj;
	}


/*
	1、顺序容器：list，vector
	2、关联容器：map，set
	   其中vector的存储结构是数组，其它的存储结构是链表
*/
	public static Vector<String> getColumnName(Cursor cursor){
		Vector<String> columns = new Vector<String>();
		String array[] = cursor.getColumnNames();
		List<String> list = Arrays.asList(array);
		columns = new Vector<String>(list);
		return columns;
	}

}
