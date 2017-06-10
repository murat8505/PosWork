package com.SetupPrinter;

import com.PosInterfaces.PrefrenceKeyConst;
import com.Utils.MyPreferences;
import com.Utils.ToastUtils;
import com.zj.btsdk.BluetoothService;
import com.zj.usbsdk.UsbController;
import com.zj.wfsdk.WifiCommunication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class BasePR {

	public abstract boolean isConnected();
	protected abstract void start();
	protected abstract void stop();
	public abstract void playBuzzerCmd(byte[] cmd);
	public abstract void smallTextCmd (byte[] cmd);
	public abstract void largeTextCmd (byte[] cmd);
	public abstract void openDrawerCmd(byte[] cmd);
	public abstract void cuttPaperCmd (byte[] cmd);
	public abstract void print1D      (byte[] cmd);
	public abstract void print2D      (byte[] cmd);
	public abstract void underLine    (byte[] cmd);
	public abstract void printCharacter(String data);	
	public static final String CHARCTER_SET = "GBK";
	private Context mContext;
	private PrinterCallBack callBack;
	protected boolean isConnectionAlive = false;

	protected void onStopConnection(){
		isConnectionAlive = false;
		callBack.onStop();
	}

	protected void onStartedConnection(){
		isConnectionAlive = true;
		callBack.onStarted(this);
	}

	protected final Handler wifiHandler = new Handler(Looper.getMainLooper()) {

		@Override
		public void handleMessage(Message msg) {			

			switch (msg.what) {

			case WifiCommunication.WFPRINTER_CONNECTED:
				onStartedConnection();
				break;

			case WifiCommunication.WFPRINTER_DISCONNECTED:
			case WifiCommunication.SEND_FAILED:
			case WifiCommunication.WFPRINTER_CONNECTEDERR:
			case WifiCommunication.WFPRINTER_REVMSG:
				onStopConnection();
				break;

			default:
				break;
			}
		}
	};


	protected final Handler bluetoothHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {			

			switch (msg.what) {

			case BluetoothService.MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					onStartedConnection();
					break;
				}
				break;

			case BluetoothService.MESSAGE_CONNECTION_LOST: 
			case BluetoothService.MESSAGE_UNABLE_CONNECT:  
				onStopConnection();
				break;

			default:
				break;
			}
		}
	};

	public final Handler usbHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case UsbController.USB_CONNECTED:				
				onStartedConnection();
				break;

			case UsbController.USB_DISCONNECTED:
				onStopConnection();
				break;

			default:				
				break;
			}
		}
	};

	public BasePR (Context _context,PrinterCallBack callBack){
		this.mContext = _context;
		this.callBack = callBack;
	}

	public Context getmContext() {
		return mContext;
	}	

	public void onStart(){
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onStop(){
		try {
			stop();
		} catch (Exception e) {
			e.printStackTrace();
			onStopConnection();
		}
	}

	public boolean onPlayBuzzer(){
		byte[] playBuzzerCmd = new byte[4];
		playBuzzerCmd[0] = 0x1B;
		playBuzzerCmd[1] = 0x42;
		playBuzzerCmd[2] = 0x04;
		playBuzzerCmd[3] = 0x01;
		try {
			if(MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_PRINTER_SOUND_ON, getmContext()))
				playBuzzerCmd(playBuzzerCmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean onSmallText(){
		byte[] smallTextCmd = new byte[3];        
		smallTextCmd[0] = 0x1b;
		smallTextCmd[1] = 0x21;
		smallTextCmd[2] &= 0xEF;
		try {
			smallTextCmd(smallTextCmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean onLargeText(){
		byte[] largeTextCmd = new byte[3];        
		largeTextCmd[0]  = 0x1b;
		largeTextCmd[1]  = 0x21;
		largeTextCmd[2] |= 0x10;		
		try {
			largeTextCmd(largeTextCmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean onCutterCmd(){
		byte[] cuttPaperCmd = new byte[4];
		cuttPaperCmd[0]=0x1D;
		cuttPaperCmd[1]=0x56;
		cuttPaperCmd[2]=0x42;
		cuttPaperCmd[3]=(byte) 0x90;
		try {
			cuttPaperCmd(cuttPaperCmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean onOpenDrawer(){
		byte[] openDrawerCmd = new byte[5];
		openDrawerCmd[0] = 0x1B;
		openDrawerCmd[1] = 0x70;
		openDrawerCmd[2] = 0x00;     
		openDrawerCmd[3] = 0x40;   
		openDrawerCmd[4] = 0x50;
		try {
			openDrawerCmd(openDrawerCmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void onPrintChar(String data){
		try {
			printCharacter(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPrint1DBarCode(String data) {
		try {
			int fixedValue       = 8;
			int lengthOfString   = data.length();
			byte [] printBarcmd  = new byte[lengthOfString + fixedValue];

			printBarcmd[0]  = 0x1d;
			printBarcmd[1]  = 0x48;
			printBarcmd[2]  = 0x02;
			printBarcmd[3]  = 0x1d;
			printBarcmd[4]  = 0x6b;
			printBarcmd[5]  = 0x02;		

			byte[] reuestedArray = data.getBytes();

			for(int index = 0; index < lengthOfString ; index ++){
				printBarcmd[fixedValue] = reuestedArray[index];
				fixedValue++;
			}

			printBarcmd[18] = 0x00;
			printBarcmd[19] = 0x0A;
			print1D(printBarcmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPrint2DQrCode(String data) {
		try {
			int fixedValue      = 6;
			int lengthOfString  = data.length();
			byte [] printQrcmd  = new byte[lengthOfString + fixedValue];

			printQrcmd[0]  = 0x1f;
			printQrcmd[1]  = 0x1c;
			printQrcmd[2]  = 0x08;
			printQrcmd[3]  = 0x00;
			printQrcmd[4]  = 0x06;
			printQrcmd[5]  = 0x00;

			byte[] reuestedArray = data.getBytes();

			for(int index = 0; index < lengthOfString ; index ++){
				printQrcmd[fixedValue] = reuestedArray[index];
				fixedValue++;
			}
			print2D(printQrcmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onUnderLine(boolean underLineNeeded){
		try{
			byte[] playBuzzerCmd = new byte[3];
			playBuzzerCmd[0] = 0x1B;
			playBuzzerCmd[1] = 0x2D;
			if(underLineNeeded)
				playBuzzerCmd[2] = 0x02;
			else
				playBuzzerCmd[2] = 0x00;
			underLine(playBuzzerCmd);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
