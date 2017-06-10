package com.AlertDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.Dialogs.ClerkSalesLoginLogoutDialog;
import com.Utils.CalculateWidthAndHeigth;
import com.Utils.GlobalApplication;
import com.posimplicity.R;

public class LogoutClerkFromOrders {

	public static void onLogoutClerkFromOrder(final Context mContext)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);				
		builder.setTitle(R.string.String_Application_Name);
		builder.setIcon(R.drawable.app_icon);
		builder.setMessage("Continue , To Logout The Assinged Clerk From Order...");
		builder.setCancelable(false);
		builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				int width   = CalculateWidthAndHeigth.calculatingWidthAndHeight(GlobalApplication.getInstance().getDeviceWidth(), 60);
				int height  = CalculateWidthAndHeigth.calculatingWidthAndHeight(GlobalApplication.getInstance().getDeviceHeight(),70);

				ClerkSalesLoginLogoutDialog dailog = new ClerkSalesLoginLogoutDialog(mContext, R.style.myCoolDialog, width, height, false, false, R.layout.dialog_show_clerk_login_logout);
				dailog.show();

				if(dailog.isShowing())
					dailog.show(1);
				
			}
		});		
		builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {	
				dialog.dismiss();
			}
		});	
		builder.show();
		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
	}
}
