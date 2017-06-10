package com.RecieptPrints;

import android.app.Activity;
import android.content.Context;

import com.AlertDialogs.ChangeAmountDialog;
import com.AsyncTasks.CompleteReportInMagento;
import com.AsyncTasks.ShareOrderWithCustomer;
import com.CustomAdapter.SplitBillAdapter;
import com.CustomControls.ShowToastMessage;
import com.Dialogs.SplitBillRowsDialog;
import com.PosInterfaces.PrefrenceKeyConst;
import com.SetupPrinter.BasePR;
import com.SetupPrinter.PrinterCallBack;
import com.SetupPrinter.UsbPR;
import com.Utils.GlobalApplication;
import com.Utils.MyPreferences;
import com.Utils.MyStringFormat;
import com.Utils.ToastUtils;
import com.Utils.Variables;
import com.posimplicity.HomeActivity;

public class EachBillPrint implements PrefrenceKeyConst{

	private Context mContext;
	private HomeActivity localInsatance;
	private final int CREDIT_FRAGMENT  = 2;
	private int numdersOfReciepts = 1;
	private SplitBillRowsDialog splitBillRowsDialog;
	float changeAmount;

	public EachBillPrint(Context mContext) {
		super();
		this.mContext            = mContext;
		this.localInsatance      = HomeActivity.localInstance;
		this.splitBillRowsDialog = SplitBillAdapter.splitBillRowsDialog;
	}


	public void onExectue() {

		SplitBillAdapter.localObj.setSplitBillPaidAmount(MyStringFormat.onFormat(SplitBillAdapter.billPaidAmount + SplitBillAdapter.amountToPaid));
		SplitBillAdapter.localObj.setSplitBillPayAmount(MyStringFormat.onFormat(SplitBillAdapter.billLeftToPay));
		SplitBillAdapter.localObj.setSplitBillPayAmountText(MyStringFormat.onFormat(SplitBillAdapter.billLeftToPay));
		SplitBillAdapter.localObj.setPartOfBillPaid(true);

		if(SplitBillAdapter.billLeftToPay == 0){
			SplitBillAdapter.localObj.setBillPaid(true);
			ToastUtils.showOwnToast(mContext, "Bill Paid SuccessFully");			
		}
		else if(SplitBillAdapter.billLeftToPay < 0){
			changeAmount = SplitBillAdapter.amountToPaid - SplitBillAdapter.billDueAmount;
			SplitBillAdapter.localObj.setSplitBillPaidAmount(MyStringFormat.onFormat(SplitBillAdapter.billPaidAmount + SplitBillAdapter.billDueAmount));
			SplitBillAdapter.localObj.setSplitBillPayAmount(MyStringFormat.onFormat(0.0f));
			SplitBillAdapter.localObj.setSplitBillPayAmountText(MyStringFormat.onFormat(0.0f));
			SplitBillAdapter.localObj.setBillPaid(true);
			ToastUtils.showOwnToast(mContext, "Bill Paid SuccessFully");
			SplitBillAdapter.amountToPaid = SplitBillAdapter.billDueAmount;			
		}
		updateVariables();

		SplitBillAdapter.adapter.notifyDataSetChanged();
		SplitBillAdapter.anyRowLeftForPayment();



		if(!SplitBillAdapter.anyRowLeftForPayment){

			new CompleteReportInMagento(mContext,splitBillRowsDialog.oRDER_STATUS,splitBillRowsDialog.pAYMENT_MODE,true).execute();

			if (!Variables.billToName.isEmpty()) {
				new ShareOrderWithCustomer(mContext).execute();
			}
		}

		if(SplitBillAdapter.paymentMode == CREDIT_FRAGMENT)
			numdersOfReciepts++;
		if(MyPreferences.getBooleanPrefrences(IS_DUPLICATE_RECIEPT_ON_PS, mContext))
			numdersOfReciepts++;

		boolean letPrintDone = true;
		if(PrintSettings.isAbleToPrintCustomerReceiptThroughUsb(mContext)){

			letPrintDone = false;
			new UsbPR(mContext, new PrinterCallBack() {

				@Override
				public void onStop() {
					onPostExection();
				}

				@Override
				public void onStarted(BasePR printerCmmdO) {
					PrintRecieptCustomer pReciept = new PrintRecieptCustomer(mContext);
					for(int index = 0 ; index < numdersOfReciepts ; index ++){
						pReciept.onEachBillPriniting(SplitBillAdapter.paymentMode,SplitBillAdapter.amountToPaid,printerCmmdO);
					}
					PrintExtraReceipt.onOpenCashDrawer(printerCmmdO);
					onPostExection();
				}
			}).onStart();
		}
		if(PrintSettings.isAbleToPrintCustomerReceiptThroughBluetooth(mContext)){
			PrintRecieptCustomer pReciept = new PrintRecieptCustomer(mContext);
			for(int index = 0 ; index < numdersOfReciepts ; index ++){
				pReciept.onEachBillPriniting(SplitBillAdapter.paymentMode,SplitBillAdapter.amountToPaid,GlobalApplication.getInstance().getmBasePrinterBT());
			}
			PrintExtraReceipt.onOpenCashDrawer(GlobalApplication.getInstance().getmBasePrinterBT());
		}

		if(letPrintDone)
			onPostExection();
	}


	private void updateVariables() {
		switch (SplitBillAdapter.paymentMode) {

		case 1:
			Variables.cashAfterChange  += SplitBillAdapter.amountToPaid; 
			break;			

		case 3:
			Variables.checkAmount      += SplitBillAdapter.amountToPaid;
			break;

		default:
			break;
		}
	}


	private void onPostExection(){

		if(changeAmount > 0)
			ChangeAmountDialog.showChangeAmountOnBillSplit(mContext, localInsatance, changeAmount,splitBillRowsDialog);
		else {
			if(!SplitBillAdapter.anyRowLeftForPayment){
				splitBillRowsDialog.dismiss();
				ShowToastMessage.showApprovedToast(mContext);
				localInsatance.resetAllData(mContext,0);
				((Activity) mContext).finish();
			}
		}
	}
}
