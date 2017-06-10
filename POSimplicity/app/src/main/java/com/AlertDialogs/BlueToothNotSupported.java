package com.AlertDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.posimplicity.R;

public class BlueToothNotSupported {

	public static void onBlueTooth(Context mContext){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);				
		builder.setTitle(R.string.String_Application_Name);
		builder.setIcon(R.drawable.app_icon);
		builder.setMessage("Device Doesn't Support Bluetooth Hardware");
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.show();		
		builder.create();
	}
}
