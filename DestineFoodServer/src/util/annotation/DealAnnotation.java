package util.annotation;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class DealAnnotation {
    

    /** Cursor -> List */
	public static <E> ArrayList<E> resultSet2List(ResultSet rs, Class<E> clazz) {
		Vector<String> columns = getColumnName(rs);
		ArrayList<E> arrayList = new ArrayList<E>();
		try {
			while (rs.next()) {
				E obj = null;
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				obj = resultSet2Obj(rs, obj, columns);
				arrayList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
	
    /** Cursor -> Object
     * 要定义泛型方法，只需将泛型参数列表置于返回值前
     */
	public static <E> E resultSet2Obj(ResultSet rs, E obj, Vector<String> columns) throws Exception {
		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields(); // 获得成员变量
		for (Field field : fields) {
			if (field.getAnnotations().length > 0 && field.isAnnotationPresent(AnnotationField.class)) { // 判断注解
				field.setAccessible(true);
				AnnotationField annotationField = field.getAnnotation(AnnotationField.class);
				String columnName = annotationField.columnName();
				String type = annotationField.type();
				// Log.i("testLog", "-----" + field.getType());
				// Log.i("testLog", "-----" + field.getName());
				if (columns.contains(columnName)) {
					switch (type) {
					case "int":
						int i = rs.getInt(columnName);
						field.set(obj, i);
						break;
					case "float":
						float f = rs.getFloat(columnName);
						field.set(obj, f);
						break;
					case "double":
						double d = rs.getDouble(columnName);
						field.set(obj, d);
						break;
					case "String":
						String s = rs.getString(columnName);
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
    public static Vector<String> getColumnName(ResultSet rs){
    	Vector<String> columns = new Vector<String>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				columns.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return columns;
    }

}
