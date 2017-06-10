package com.posimplicity;

import com.PosInterfaces.PrefrenceKeyConst;
import com.PosInterfaces.SocketInterface;
import com.PosInterfaces.WebServiceCallObjectIds;
import com.Socket.SocketIO;
import com.Utils.GlobalApplication;
import com.Utils.InternetConnectionDetector;
import com.Utils.MyPreferences;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

public abstract class BaseActivity extends Activity implements PrefrenceKeyConst,SocketInterface,WebServiceCallObjectIds{

	protected GlobalApplication  globalApp;
	protected Context mContext;
	protected PendingIntent pendingIntentForAppRes,penIntentForAlrt;
	protected AlarmManager managerForAppRes,managerForAlrt;
	private   boolean connectToSocket = false;
	private   Activity cuurentAct     = null;

	protected void onCreate(Bundle savedInstanceState,boolean socketConnectionRequired,Activity currentActivity) {
		super.onCreate(savedInstanceState);
		mContext        = this;
		globalApp       = GlobalApplication.getInstance(); 
		connectToSocket = socketConnectionRequired;
		cuurentAct      = currentActivity; 
		//getLayoutInflater().setFactory2(CustomFontFactory.getInstance());

		if(MyPreferences.getBooleanPrefrences(IS_SOCKET_NEEDED, mContext) && cuurentAct instanceof HomeActivity && connectToSocket && InternetConnectionDetector.isInternetAvailable(mContext)){
			SocketIO socketIO = new SocketIO(mContext);
			socketIO.setSocketRecievedData(this);
			socketIO.connectSocket();
			globalApp.setSocketIo(socketIO);
		}
	}

	@SuppressWarnings("unchecked")
	protected  <T>  T findViewByIdAndCast(int id){
		return (T)findViewById(id);
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
	}

	public abstract void onInitViews();

	public abstract void onListenerRegister();
}
