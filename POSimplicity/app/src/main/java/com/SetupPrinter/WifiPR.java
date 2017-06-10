package com.SetupPrinter;

import com.zj.wfsdk.WifiCommunication;
import android.content.Context;

public class WifiPR extends BasePR {

	private String ipAddress = "";
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	private WifiCommunication wifiCommunication;
	

	/**
	 * @return the wifiCommunication
	 */
	public WifiCommunication getWifiCommunication() {
		return wifiCommunication;
	}

	public WifiPR(Context _context,PrinterCallBack callBack) {
		super(_context, callBack);
	}

	@Override
	public boolean isConnected() {
		return isConnectionAlive;
	}

	@Override
	public void playBuzzerCmd(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void smallTextCmd(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void largeTextCmd(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void openDrawerCmd(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void cuttPaperCmd(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void printCharacter(String data) {
		wifiCommunication.sendMsg(data+"\n", CHARCTER_SET);
	}

	@Override
	public void start() {
		wifiCommunication = new WifiCommunication(wifiHandler);
		wifiCommunication.initSocket(ipAddress, 9100);
	}

	@Override
	public void stop() {
		wifiCommunication.close();
	}

	@Override
	public void print1D(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void print2D(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}

	@Override
	public void underLine(byte[] cmd) {
		wifiCommunication.sndByte(cmd);
	}
}
