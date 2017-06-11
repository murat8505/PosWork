package com.BackGroundService;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import com.Fragments.BaseFragment;
import com.Fragments.DejavooFragment;
import com.Fragments.MaintFragmentPrinterSetting;
import com.posimplicity.DeviceListActivity;

public class BluetoothUtils {

    public static boolean isBluetoothAvailable() {
        return BluetoothAdapter.getDefaultAdapter() != null;
    }

    public static boolean isBluetoothOpen() {
        return isBluetoothAvailable() && BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public static void openBluetoothSocketForceFully() {
        if (isBluetoothAvailable() && !BluetoothAdapter.getDefaultAdapter().isEnabled())
            BluetoothAdapter.getDefaultAdapter().enable();
    }

    public static void closeBluetoothSocketForceFully() {
        if (isBluetoothAvailable() && BluetoothAdapter.getDefaultAdapter().isEnabled())
            BluetoothAdapter.getDefaultAdapter().disable();
    }

    public static void openBluetootSocket(BaseFragment fragment, Context mContext, int request, int code) {
        Intent serverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        switch (code) {

            case 0:
                ((MaintFragmentPrinterSetting) fragment).startActivityForResult(serverIntent, request);
                break;

            case 1:
                ((DejavooFragment) fragment).startActivityForResult(serverIntent, request);
                break;

            default:
                break;
        }
    }


    public static void findAndSelectAnyDevice(BaseFragment fragment, Context mContext, int code) {
        Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
        switch (code) {

            case 0:
                ((MaintFragmentPrinterSetting) fragment).startActivityForResult(serverIntent, MaintFragmentPrinterSetting.REQUEST_CONNECT_DEVICE);
                break;

            case 1:
                ((DejavooFragment) fragment).startActivityForResult(serverIntent, MaintFragmentPrinterSetting.REQUEST_CONNECT_DEVICE);
                break;

            default:
                break;
        }
    }
}
