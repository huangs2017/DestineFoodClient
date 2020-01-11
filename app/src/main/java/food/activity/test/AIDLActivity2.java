package food.activity.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import food.activity.R;

public class AIDLActivity2 extends AppCompatActivity {

    Intent intent;
    Messenger messengerProxy;

    private Messenger clientMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message serviceMsg) {
            System.out.println("服务端返回结果：" + serviceMsg.arg1);
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_activity);
        intent = new Intent("com.hs.remoteService2");
        intent.setPackage("hs.activity");
    }

    public void bind (View view) {
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    int i = 0;
    public void button2Click (View view) {
        i++;
        Message msg = Message.obtain();
        msg.arg1 = i;
        msg.arg2 = 2;
        msg.replyTo = clientMessenger;
        try {
            messengerProxy.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerProxy = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}