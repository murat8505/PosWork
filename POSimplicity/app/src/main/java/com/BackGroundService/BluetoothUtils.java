package com.BackGroundService;

import com.Fragments.BaseFragment;
import com.Fragments.DejavooFragment;
import com.Fragments.MaintFragmentPrinterSetting;
import com.posimplicity.DeviceListActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

public class BluetoothUtils {

	public static boolean isBluetoothAvailabe(){
		return BluetoothAdapter.getDefaultAdapter() != null;
	}

	public static void openBluetootSocket(BaseFragment fragment,Context mContext,int request,int code){
		Intent serverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
		switch (code) {

		case 0:
			((MaintFragmentPrinterSetting)fragment).startActivityForResult(serverIntent,request);
			break;

		case 1:
			((DejavooFragment)fragment).startActivityForResult(serverIntent,request);
			break;

		default:
			break;
		}
	}


	public static void closeBluetootSocket(){
		if(isBluetoothAvailabe() && BluetoothAdapter.getDefaultAdapter().isEnabled())
			BluetoothAdapter.getDefaultAdapter().disable();
	}

	public static void findAndSelectAnyDevice(BaseFragment fragment,Context mContext,int code) {
		Intent serverIntent = new Intent(mContext,DeviceListActivity.class); 
		switch (code) {

		case 0:
			((MaintFragmentPrinterSetting)fragment).startActivityForResult(serverIntent,MaintFragmentPrinterSetting.REQUEST_CONNECT_DEVICE);
			break;

		case 1:
			((DejavooFragment)fragment).startActivityForResult(serverIntent,MaintFragmentPrinterSetting.REQUEST_CONNECT_DEVICE);
			break;

		default:
			break;
		}
		
	}

	public static void openBluetootSocketForcly() {
		if(isBluetoothAvailabe() && !BluetoothAdapter.getDefaultAdapter().isEnabled())
			BluetoothAdapter.getDefaultAdapter().enable();
	}

	public static boolean isBluetoothOpen() {
		return isBluetoothAvailabe() && BluetoothAdapter.getDefaultAdapter().isEnabled();
	}
}
