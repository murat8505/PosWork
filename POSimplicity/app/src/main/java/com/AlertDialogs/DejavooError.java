package com.AlertDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.posimplicity.R;

public class DejavooError {
	

	public static void onShow(final Context mContext,String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);				
		builder.setTitle(R.string.String_Application_Name);
		builder.setIcon(R.drawable.app_icon);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				dialog.dismiss();
			}
		});	
		builder.show();
		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
	}

}
