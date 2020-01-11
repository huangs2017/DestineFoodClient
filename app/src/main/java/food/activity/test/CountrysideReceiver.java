package food.activity.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CountrysideReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String content = getResultData();
        System.out.println("CountrysideReceiver-->" + content);

//      abortBroadcast(); 终止广播
        setResultData("习大大给每个村民发了500斤大米");
    }
}
