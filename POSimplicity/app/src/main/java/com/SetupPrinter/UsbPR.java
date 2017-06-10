package com.SetupPrinter;

import com.zj.usbsdk.UsbController;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.os.Message;

public class UsbPR extends BasePR {

	private int[][] u_infor;
	private UsbController usbCtrl = null;
	private UsbDevice dev         = null;


	public UsbPR(Context _context, PrinterCallBack callBack) {
		super(_context, callBack);
	}

	@Override
	public boolean isConnected() {
		return isConnectionAlive;
	}

	@Override
	protected void start() {
		usbCtrl = new UsbController((Activity) getmContext(), usbHandler);
		u_infor = new int[5][2];
		u_infor[0][0] = 0x1CBE;
		u_infor[0][1] = 0x0003;
		u_infor[1][0] = 0x1CB0;
		u_infor[1][1] = 0x0003;
		u_infor[2][0] = 0x0483;
		u_infor[2][1] = 0x5740;
		u_infor[3][0] = 0x0493;
		u_infor[3][1] = 0x8760;
		u_infor[4][0] = 0x0416;
		u_infor[4][1] = 0x5011;
		int i         = 0;

		for (i = 0; i < 5; i++) {
			dev = usbCtrl.getDev(u_infor[i][0], u_infor[i][1]);
			if (dev != null)
				break;
		}

		if (dev != null) {
			if (!(usbCtrl.isHasPermission(dev))) {
				usbCtrl.getPermission(dev);
			} else {
				Message message = usbHandler.obtainMessage();
				message.what    = UsbController.USB_CONNECTED;
				usbHandler.dispatchMessage(message);
			}
		}
		if (dev == null){
			Message message = usbHandler.obtainMessage();
			message.what    = UsbController.USB_DISCONNECTED;
			usbHandler.sendMessage(message);
		}		
	}

	@Override
	protected void stop() {
		usbCtrl.close();
	}

	@Override
	public void playBuzzerCmd(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void smallTextCmd(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void largeTextCmd(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void openDrawerCmd(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void cuttPaperCmd(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void print1D(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void print2D(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}

	@Override
	public void printCharacter(String data) {
		usbCtrl.sendMsg(data, CHARCTER_SET, dev);
	}

	@Override
	public void underLine(byte[] cmd) {
		usbCtrl.sendByte(cmd, dev);
	}
}
