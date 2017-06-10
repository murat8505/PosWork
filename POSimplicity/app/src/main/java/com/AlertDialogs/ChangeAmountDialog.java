package com.AlertDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.CustomAdapter.SplitBillAdapter;
import com.CustomControls.ShowToastMessage;
import com.Dialogs.SplitBillRowsDialog;
import com.Utils.CalculateWidthAndHeigth;
import com.Utils.GlobalApplication;
import com.Utils.MyStringFormat;
import com.posimplicity.HomeActivity;
import com.posimplicity.R;

public class ChangeAmountDialog {

	public static void showChangeAmount(final Context mContext,final HomeActivity instance , float change) {

		GlobalApplication globalApp = GlobalApplication.getInstance();
		int width   = CalculateWidthAndHeigth.calculatingWidthAndHeight(globalApp.getDeviceWidth(), 30);
		int height  = CalculateWidthAndHeigth.calculatingWidthAndHeight(globalApp.getDeviceHeight(),55);

		AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(	mContext);
		alertDialog1.setCancelable(false);
		alertDialog1.setIcon(R.drawable.app_icon);
		alertDialog1.setTitle(R.string.String_Application_Name);
		alertDialog1.setMessage("Change Amount: $" + MyStringFormat.onFormat(change));
		alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				instance.resetAllData(mContext,0);				
				dialog.dismiss();	
				((Activity) mContext).finish();
			}
		});
		AlertDialog al = alertDialog1.create();
		al.setCanceledOnTouchOutside(false);
		al.show();	
		al.getWindow().setLayout(width, height);
	}

	public static void showChangeAmountOnBillSplit(final Context mContext,final HomeActivity instance , float change,final SplitBillRowsDialog splitBillRowsDialog){

		AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(	mContext);
		alertDialog1.setCancelable(false);
		alertDialog1.setTitle(R.string.String_Application_Name);
		alertDialog1.setMessage("Change Amount: $" + MyStringFormat.onFormat(change));
		alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(!SplitBillAdapter.anyRowLeftForPayment){
					splitBillRowsDialog.dismiss();
					ShowToastMessage.showApprovedToast(mContext);
					instance.resetAllData(mContext,1);
					((Activity) mContext).finish();
				}
			}
		});
		AlertDialog al = alertDialog1.create();
		al.setCanceledOnTouchOutside(false);
		al.show();	
	}
}
