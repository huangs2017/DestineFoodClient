package food.activity.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import food.activity.R;
import hunter.BinderTest;
import hunter.Book;
import hunter.Student;

public class AIDLActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_activity);
        intent = new Intent("com.hs.remoteService");
        intent.setPackage("hs.activity");
    }

    public void bind (View view) {
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BinderTest binderTest = BinderTest.Stub.asInterface(service);
            try {
                int i= binderTest.callAdd(1, 2);
                System.out.println(i);

                Student student = new Student("张三", 21);
                List<Book> bookList = binderTest.getBooks(student);
                System.out.println("客户端in： "+ bookList);

                binderTest.getStudent(student);
                System.out.println("客户端out： " + student);

                binderTest.getStudentWithInOutTag(student);
                System.out.println("客户端inout： " + student);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}


/*
AIDL的三个定向tag：in、out、inout

所有非基本类型的参数都需要一个定向tag来表明数据是如何走向的，
要么是in、out或者inout。基本数据类型只能是in

in      表示数据只能由客户端流向服务端，
out     表示数据只能由服务端流向客户端，
inout   表示数据可以在客户端与服务端之间双向流通

in和out的区别：
    1. in表示输入参数，out表示输出参数
    2. in类型是值传递，out类型是引用传递
对于out，服务端将会收到客户端对象，该对象不为空，但它里面的字段为空
对于inout ，服务端将会接收到客户端传来对象的完整信息，并且客户端会同步服务端对该对象的变动

*/