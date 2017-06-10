package com.BackGroundService;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.Utils.GlobalApplication;

public class MyBluetoothReceiver extends BroadcastReceiver {
    private GlobalApplication globalApplication = GlobalApplication.getInstance();

    public MyBluetoothReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {

                case BluetoothAdapter.STATE_OFF:
                    ServiceUtils.operateBTDejavooService(context, false);
                    break;
            }
        }
    }
}
