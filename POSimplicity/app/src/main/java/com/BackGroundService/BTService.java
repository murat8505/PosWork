package com.BackGroundService;

import com.Fragments.MaintFragmentPrinterSetting;
import com.PosInterfaces.PrefrenceKeyConst;
import com.SetupPrinter.BasePR;
import com.SetupPrinter.BluetPR;
import com.SetupPrinter.PrinterCallBack;
import com.Utils.GlobalApplication;
import com.Utils.MyPreferences;

import android.app.IntentService;
import android.content.Intent;

public class BTService extends IntentService implements PrinterCallBack {		

	private BasePR basePrinter;
	private GlobalApplication gApplication = GlobalApplication.getInstance();
	public  static boolean runningService  = true;

	public BTService() {
		super(BTService.class.getCanonicalName());
		basePrinter = new BluetPR(this, this);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		basePrinter.onStart();
		while(runningService){
			try {							
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(basePrinter != null && ((BluetPR)basePrinter).getmBluetoothService() != null){
			((BluetPR)basePrinter).getmBluetoothService().stop();
			return;
		}
		updateUiWhenSameFragmentOpen("onStop");
	}

	@Override
	public void onStarted(BasePR basePrinter) {
		gApplication.setmBasePrinterBT(basePrinter);
	}

	@Override
	public void onStop() {		
		updateUiWhenSameFragmentOpen("onStop");
	}

	private void updateUiWhenSameFragmentOpen(String calledMethod){
		System.out.println(BTService.class.getSimpleName().concat(" ( ").concat(calledMethod).concat(" ) "));
		gApplication.setmBasePrinterBT(null);
		ServiceUtils.operateBTService(this, false);
		MyPreferences.setBooleanPrefrences(PrefrenceKeyConst.IS_BT_ON_PS,false,this);
		if(gApplication.getVisibleFragment() != null ){
			if (gApplication.getVisibleFragment() instanceof MaintFragmentPrinterSetting){
				MaintFragmentPrinterSetting settingsfrg = (MaintFragmentPrinterSetting) gApplication.getVisibleFragment();
				settingsfrg.loadAndRefereshList();
			} 
		}
	}
}
