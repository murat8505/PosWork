package com.AlertDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.posimplicity.R;

public class ShowTimeAlert {

	public static void onShowTimeAlert(Context _mContext){
		AlertDialog.Builder builder = new AlertDialog.Builder(_mContext);
		builder.setIcon(R.drawable.app_icon).setMessage("Please Save All Your Current Working Data . App Will Restart In Next 10 Minutes ").setTitle(R.string.String_Application_Name).setPositiveButton("Ok", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		AlertDialog al = builder.create();
		al.show();
	}

}
