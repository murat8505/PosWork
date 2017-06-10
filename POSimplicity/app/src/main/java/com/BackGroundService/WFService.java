package com.BackGroundService;

import com.Fragments.MaintFragmentPrinterSetting;
import com.PosInterfaces.PrefrenceKeyConst;
import com.SetupPrinter.BasePR;
import com.SetupPrinter.PrinterCallBack;
import com.SetupPrinter.WifiPR;
import com.Utils.CheckAppStatus;
import com.Utils.GlobalApplication;
import com.Utils.MyPreferences;

import android.app.IntentService;
import android.content.Intent;

public class WFService extends IntentService implements PrinterCallBack {		

	private BasePR basePrinter;
	private GlobalApplication gApplication = GlobalApplication.getInstance();
	public  static boolean runningService  = true;

	public WFService() {
		super(WFService.class.getCanonicalName());
		basePrinter = new WifiPR(this,this);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		while(runningService){
			try {
				boolean appIsRunning = CheckAppStatus.isAppRunning(this);
				((WifiPR)basePrinter).setIpAddress(MyPreferences.getMyPreference(PrefrenceKeyConst.WIFI_IP_ADDRESS, this));
				if(!basePrinter.isConnected() && appIsRunning && !MyPreferences.getMyPreference(PrefrenceKeyConst.WIFI_IP_ADDRESS, this).isEmpty() && MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_WIFI_ON_PS, this))
					basePrinter.onStart();				

				Thread.sleep(13000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStarted(BasePR basePrinter) {
		gApplication.setmBasePrinterWF(basePrinter);
	}

	@Override
	public void onStop() {
		updateUiWhenSameFragmentOpen("onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(basePrinter != null && ((WifiPR)basePrinter).getWifiCommunication() != null){
			((WifiPR)basePrinter).getWifiCommunication().close();
			return;
		}
		else
			updateUiWhenSameFragmentOpen("onStop");
	}

	private void updateUiWhenSameFragmentOpen(String calledMethod){
		System.out.println(WFService.class.getSimpleName().concat(" ( ").concat(calledMethod).concat(" ) "));
		gApplication.setmBasePrinterWF(null);
		ServiceUtils.operateWFService(this, false);
		if(gApplication.getVisibleFragment() != null ){
			if (gApplication.getVisibleFragment() instanceof MaintFragmentPrinterSetting){		
				MyPreferences.setBooleanPrefrences(PrefrenceKeyConst.IS_WIFI_ON_PS,false,this);				
				MaintFragmentPrinterSetting settingsfrg = (MaintFragmentPrinterSetting) gApplication.getVisibleFragment();
				settingsfrg.loadAndRefereshList();
			} 
		}
	}
}
