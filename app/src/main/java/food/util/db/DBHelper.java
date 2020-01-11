package food.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static DBHelper dbHelper;
	public SQLiteDatabase db;
	static String dbName = "cartDB";	// 数据库名字
	static String tableName = "cart"; 	// 购物车表名

	private DBHelper(Context ctx) {
		super(ctx, dbName, null, 1);
		db = getWritableDatabase(); // 得到数据库对象
	}

	//单例模式
	public static synchronized DBHelper getInstance(Context ctx) {
		if (dbHelper == null) {
//			dbHelper = new DBHelper(ctx);

			/*
				性能优化-->内存泄露优化
   				什么是内存泄露：没有用的对象无法回收的现象就是内存泄露
				垃圾回收机制：某对象不再有任何引用的时候才会进行回收
				可以作为GC Root引用点的是:
					1. JavaStack中引用的对象
					2. 方法区中静态引用指向的对象
					3. 方法区中常量引用指向的对象
					4. Native方法中JNI引用的对象
					5. Thread--活着的线程。
				内存泄露多了容易导致 内存溢出，app会崩溃。
			*/

			// 不使用Activity的上下文，而是使用Application的上下文。(不然Activity销毁时不能被垃圾回收)
			// 因为Application的生命周期长，迸程退出时オ会硝毀，和Static的DBHelper的生命周期一样长。
			dbHelper = new DBHelper(ctx.getApplicationContext());
		}
		return dbHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + tableName + "(foodId integer, foodName text, price float, quantity int, image int)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + tableName);
		onCreate(db);
	}

}
