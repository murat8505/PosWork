package com.PosBroadcast;

import com.Utils.GlobalApplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public abstract class PosAbstractService {

	protected Context mContext;
	protected AlarmManager alarmManager;
	protected PendingIntent pendingIntent;
	protected GlobalApplication gApp;

	public PosAbstractService(Context mcContext) {
		this.mContext = mcContext;
		this.gApp     = GlobalApplication.getInstance();
	}

	public abstract void setupAlarm();

	public void onPosServiceSetup(Class<?> serviceClass){		

		Intent myIntent    = new Intent(mContext, serviceClass);
		pendingIntent      = PendingIntent.getService(mContext,  0, myIntent, 0);
		alarmManager       = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
	}
	
}
