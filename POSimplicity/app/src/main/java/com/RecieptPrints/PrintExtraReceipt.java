package com.RecieptPrints;

import android.content.Context;
import com.Beans.CustomerModel;
import com.PosInterfaces.PrefrenceKeyConst;
import com.SetupPrinter.BasePR;
import com.Utils.MyPreferences;
import com.Utils.MyStringFormat;

public class PrintExtraReceipt {

	private final static String DEFAULT_ADDRESS = "Enter Address ";

	public static void onPrintTipReceipt(CustomerModel clerkCustomer, String tsysTransactionId,BasePR basePR) {

		basePR.onPlayBuzzer();
		basePR.onLargeText();
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter("Tip Receipt")+"\n");
		basePR.onSmallText();

		basePR.onPrintChar(PrintSettings.onReformatAnyText("TSYS trans ID", 30) +PrintSettings.onReformatAnyText(":"+tsysTransactionId             , 18));
		basePR.onPrintChar(PrintSettings.onReformatAnyText("Clerk Name:", 30) + PrintSettings.onReformatAnyText(":"+clerkCustomer.getFirstName()  , 18));
		basePR.onPrintChar(PrintSettings.onReformatAnyText("Clerk ID", 30) + PrintSettings.onReformatAnyText(":"+clerkCustomer.getCustomerId() , 18));
		basePR.onPrintChar(PrintSettings.onReformatAnyText("Tip Amount", 30) + PrintSettings.onReformatAnyText(":"+MyStringFormat.onStringFormat(clerkCustomer.getTipAmount())  , 18));

		basePR.onPrintChar("\n"+PrintSettings.onFormatHeaderAndFooter("-----------------------------"));
		basePR.onCutterCmd();

	}


	public static void onSamplePrint(Context mContext,BasePR basePR)
	{
		basePR.onPlayBuzzer();
		basePR.onLargeText();		
		basePR.onPrintChar(MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT1, mContext).isEmpty() ? DEFAULT_ADDRESS :MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT1, mContext));
		basePR.onPrintChar(MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT2, mContext).isEmpty() ? DEFAULT_ADDRESS :MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT2, mContext));
		basePR.onPrintChar(MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT3, mContext).isEmpty() ? DEFAULT_ADDRESS :MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT3, mContext));
		basePR.onPrintChar(MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT4, mContext).isEmpty() ? DEFAULT_ADDRESS :MyPreferences.getMyPreference(PrefrenceKeyConst.TEXT4, mContext));	
		basePR.onCutterCmd();
	}

	public static void onPrintPayoutReceipt(Context mContext, final String payoutAmt,final String payoutName,final String payoutDesc,BasePR basePR){
		basePR.onPlayBuzzer();
		basePR.onLargeText();
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter("---PAYOUT---\n"));
		basePR.onSmallText();
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter(payoutName));
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter(payoutAmt));
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter(payoutDesc));				
		basePR.onCutterCmd();
	}

	public static void onOpenCashDrawer(BasePR basePR)
	{
		basePR.onOpenDrawer();
	}

	public static void onFailedToSave(Context mContext,BasePR basePR){

		String transId  = MyPreferences.getMyPreference(PrefrenceKeyConst.MOST_RECENTLY_TRANSACTION_ID, mContext);
		basePR.onPlayBuzzer();
		basePR.onLargeText();
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter("Please Save Last Order In"));
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter("Magento Manually")+"\n");
		basePR.onPrintChar("TransID == "+transId);
		basePR.onCutterCmd();
	}
}
