package com.SetupPrinter;

import com.PosInterfaces.PrefrenceKeyConst;
import com.Utils.MyPreferences;
import com.zj.btsdk.BluetoothService;

import android.content.Context;

public class BluetPR extends BasePR {
	private BluetoothService mBluetoothService;
	
	

	/**
	 * @return the mBluetoothService
	 */
	public BluetoothService getmBluetoothService() {
		return mBluetoothService;
	}

	/**
	 * @param mBluetoothService the mBluetoothService to set
	 */
	public void setmBluetoothService(BluetoothService mBluetoothService) {
		this.mBluetoothService = mBluetoothService;
	}

	public BluetPR(Context _context, PrinterCallBack callBack) {
		super(_context, callBack);
	}

	@Override
	public boolean isConnected() {
		return isConnectionAlive;
	}

	@Override
	public void start() {
		mBluetoothService = new BluetoothService(getmContext(), bluetoothHandler);
		mBluetoothService.connect(mBluetoothService.getDevByMac(MyPreferences.getMyPreference(PrefrenceKeyConst.BLUETOOTH_DEVICE_ADDRESS_PRINTING, getmContext())));
	}

	@Override
	public void stop() {
		mBluetoothService.stop();
	}

	@Override
	public void playBuzzerCmd(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void smallTextCmd(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void largeTextCmd(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void openDrawerCmd(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void cuttPaperCmd(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void print1D(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void print2D(byte[] cmd) {
		mBluetoothService.write(cmd);
	}

	@Override
	public void printCharacter(String data) {
		mBluetoothService.sendMessage(data,CHARCTER_SET);
	}

	@Override
	public void underLine(byte[] cmd) {
		mBluetoothService.write(cmd);
	}
}
