package food.activity.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class FarmerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String content = getResultData();
        System.out.println("FarmerReceiver-->" + content);
    }
}
