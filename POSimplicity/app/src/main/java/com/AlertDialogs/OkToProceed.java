package com.AlertDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup.LayoutParams;

import com.Utils.CalculateWidthAndHeigth;
import com.Utils.GlobalApplication;
import com.posimplicity.R;

public class OkToProceed {

	public static void onExit(final Context mContext) {
		GlobalApplication globalApplication = GlobalApplication.getInstance();
		int width  = CalculateWidthAndHeigth.calculatingWidthAndHeight(globalApplication.getDeviceWidth(),50);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);				
		builder.setTitle(R.string.String_Application_Name);
		builder.setIcon(R.drawable.app_icon);
		builder.setMessage("OK to Exit From App!");
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((Activity) mContext).finish();
			}
		});		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {	
				dialog.dismiss();
			}
		});
		
		builder.show();		
		AlertDialog alertDialog = builder.create();		
		alertDialog.getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
	}

}
