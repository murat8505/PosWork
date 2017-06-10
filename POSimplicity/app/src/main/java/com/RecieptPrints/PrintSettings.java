package com.RecieptPrints;

import com.PosInterfaces.PrefrenceKeyConst;
import com.Utils.GlobalApplication;
import com.Utils.MyPreferences;
import com.Utils.ToastUtils;

import android.content.Context;

public class PrintSettings {

	public static final int MAX_CHAR_IN_LINE       = 48;
	public static final int ITEM_NAME_SPACE_SIZE   = 30;
	public static final int MID_SPACE_SIZE         = 24;
	public static final int QTY_SPACE_SIZE         = 7;
	public static final int PRICE_SPACE_SIZE       = 11;
	public static final String SPECIAL_CHAR        = " ";
	public static final String NEW_LINE_CHAR       = "\n";


	public static String onReformatName(String textString) {
		StringBuilder strBld = new StringBuilder(textString);
		int stringLegth      = strBld.length();
		if(stringLegth   < ITEM_NAME_SPACE_SIZE){
			int addSpaces = ITEM_NAME_SPACE_SIZE - stringLegth;		
			String padded = String.format("%-" + addSpaces + "s", SPECIAL_CHAR);
			strBld.append(padded);
		}
		else if(stringLegth   > ITEM_NAME_SPACE_SIZE)
		{
			strBld.delete(ITEM_NAME_SPACE_SIZE - 3, strBld.length());
			strBld.append("...");
			/*
			int count = 0;
			if(stringLegth % ITEM_NAME_SPACE_SIZE == 0)
				count = stringLegth / ITEM_NAME_SPACE_SIZE;
			else
				count = stringLegth / ITEM_NAME_SPACE_SIZE + 1;

			int counter = 0;
			for(int index = 1 ; index <= count ; index ++){
				if(index != count)
					strBld.insert(ITEM_NAME_SPACE_SIZE * index + counter, NEW_LINE_CHAR);
				else{
					int newLength = strBld.substring((ITEM_NAME_SPACE_SIZE * (index-1))+counter).length(); 
					int addSpaces = ITEM_NAME_SPACE_SIZE - newLength;		
					String padded = String.format("%-" + addSpaces + "s", SPECIAL_CHAR);
					strBld.append(padded);
				}
				counter ++;
			}
			 */
		}
		return strBld.toString();
	}

	public static String onReformatAnyText(String textString,int specifiedValue) {
		StringBuilder strBld = new StringBuilder(textString);
		int stringLegth      = strBld.length();
		if(stringLegth   < specifiedValue){
			int addSpaces = specifiedValue - stringLegth;		
			String padded = String.format("%-" + addSpaces + "s", SPECIAL_CHAR);
			strBld.append(padded);
		}
		else if(stringLegth   > specifiedValue)
		{
			strBld.delete(specifiedValue - 3, strBld.length());
			strBld.append("...");
		}
		return strBld.toString();
	}

	public static String onReformatQty(String textString) {
		StringBuilder strBld = new StringBuilder(textString);
		strBld.insert(0, "  ");
		int stringLegth      = strBld.length();
		if(stringLegth   < QTY_SPACE_SIZE){
			int addSpaces = QTY_SPACE_SIZE - stringLegth;		
			String padded = String.format("%-" + addSpaces + "s", SPECIAL_CHAR);
			strBld.append(padded);
		}
		return strBld.toString();
	}


	public static String onReformatPrice(String textString){

		StringBuilder strBld = new StringBuilder(textString);
		int stringLegth      = strBld.length();
		if(stringLegth   < PRICE_SPACE_SIZE){
			int addSpaces = PRICE_SPACE_SIZE - stringLegth;		
			String padded = String.format("%-" + addSpaces + "s", SPECIAL_CHAR);
			strBld.insert(0, padded);
		}
		return strBld.toString();
	}

	public static String onFormatHeaderAndFooter(String textString){
		StringBuilder strBld = new StringBuilder(textString);
		int stringLegth      = strBld.length();
		if(stringLegth   < MAX_CHAR_IN_LINE){
			int addSpaces = ( MAX_CHAR_IN_LINE - stringLegth )/2;
			String padded = String.format("%-" + addSpaces + "s", SPECIAL_CHAR);
			strBld.append(padded);
			strBld.insert(0, padded);
			return strBld.toString();
		}
		else
			return strBld.toString();
	}

	public static boolean isValueGreaterThanZero(float value){
		return value > 0;
	}
	
	public static boolean isAbleToPrintCustomerReceiptThroughUsb(Context mContext){
		boolean enable = MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_USB_ON_PS, mContext) && MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_USB_CU_ON_PS, mContext);
		return enable;
	}
	
	public static boolean isAbleToPrintKitchenReceiptThroughUsb(Context mContext){
		boolean enable = MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_USB_ON_PS, mContext) && MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_USB_KI_ON_PS, mContext);
		return enable;
	}
	
	public static boolean canPrintWifiSlip(Context mContext){
		boolean enable = MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_WIFI_ON_PS, mContext);
		return enable && isWifiPrintAvilable(mContext);
	}
	
	
	public static boolean isAbleToPrintCustomerReceiptThroughBluetooth(Context mContext){
		boolean enable = MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_BT_ON_PS, mContext) && MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_BT_CU_ON_PS, mContext) && isBTPrintAvilable(mContext);
		return enable;
	}
	
	public static boolean isAbleToPrintKitchenReceiptThroughBluetooth(Context mContext){
		boolean enable = MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_BT_ON_PS, mContext) && MyPreferences.getBooleanPrefrences(PrefrenceKeyConst.IS_BT_KI_ON_PS, mContext) && isBTPrintAvilable(mContext);
		return enable;
	}
	
	public static boolean isBTPrintAvilable(Context mContext){
		GlobalApplication globalApplication = GlobalApplication.getInstance();
		return globalApplication.getmBasePrinterBT() != null;
	}
	
	private static boolean isWifiPrintAvilable(Context mContext){
		GlobalApplication globalApplication = GlobalApplication.getInstance();
		return globalApplication.getmBasePrinterWF() != null;
	}

	public static void showUsbNotAvailableToast(Context mContext) {
		ToastUtils.showOwnToast(mContext, "Please Connect USB Printer");
	}
}
