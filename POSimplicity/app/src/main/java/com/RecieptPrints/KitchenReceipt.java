package com.RecieptPrints;

import java.util.List;
import android.content.Context;
import com.Beans.ExtraProductArgument;
import com.Beans.CheckOutParentModel;
import com.Beans.ProductModel;
import com.Beans.RelationalOptionModel;
import com.Beans.SubOptionModel;
import com.PosInterfaces.PrefrenceKeyConst;
import com.SetupPrinter.BasePR;
import com.Utils.CurrentDate;
import com.Utils.MyPreferences;
import com.Utils.MyStringFormat;
import com.Utils.Variables;
import com.posimplicity.HomeActivity;

public class KitchenReceipt implements PrefrenceKeyConst {
	
	private static final String DEFAULT_HEADER  = "KITCHEN ORDER";
	private static final String DEFAULT_FOOTER  = "---THANK YOU---";
	private static final String COMMENT         = "Comment:  ";

	public static void onPrintKitchenReciept(Context mContext,HomeActivity instance , BasePR basePR) {
		
		basePR.onPlayBuzzer();
		basePR.onLargeText();
		basePR.onPrintChar(PrintSettings.onFormatHeaderAndFooter(DEFAULT_HEADER)+"\n");
		basePR.onSmallText();

		String formattedString = CurrentDate.returnCurrentDateWithTime();
		basePR.onPrintChar(formattedString);

		formattedString        = MyPreferences.getMyPreference(MOST_RECENTLY_TRANSACTION_ID, mContext);
		basePR.onPrintChar("TransID  == "+formattedString);

		if(!Variables.tableID.isEmpty())
			basePR.onPrintChar("TableID  == "+Variables.tableID);

		if(!Variables.customerName.isEmpty())
			basePR.onPrintChar("Name     == "+Variables.customerName);

		basePR.onPrintChar("\n");

		for (int index = 0; index < instance.dataList.size(); index ++) {

			CheckOutParentModel   parent                    = instance.dataList.get(index);		
			ProductModel  product                      		= parent.getProduct();
			ExtraProductArgument extraAg          			= parent.getExtraArgument();
			List<RelationalOptionModel> listOfchilds 	    = parent.getChilds();

			String  itemQty    = "";
			int positiveQty    = (Integer.parseInt(product.getProductQty())-Integer.parseInt(product.getProductQtyOnPendingTime()));
			if(positiveQty <= 0)
				itemQty    = "" + product.getProductQty();
			else
				itemQty    = "" + positiveQty;				

			String productName = product.getProductName();
			float newPrice     = (Integer.parseInt(itemQty)) * (Float.parseFloat(product.getProductPrice())  + Float.parseFloat(product.getProductOptionsPrice())  - Float.parseFloat(product.getProductDisAmount()));

			formattedString    = callFormatMethod(productName,itemQty,newPrice);

			if(Variables.isPendingOrderItems && extraAg.isPendingItems())
				basePR.onPrintChar(formattedString);
			else if(Variables.isPendingOrderItems && !extraAg.isPendingItems()){}
			else 
				basePR.onPrintChar(formattedString);

			if (!listOfchilds.isEmpty()) {
				for(int count = 0; count < listOfchilds.size(); count ++){
					List<SubOptionModel> subOptionModels = listOfchilds.get(count).getListOfSubOptionModel();
					int sizeOfEachList    = subOptionModels.size();

					for(int index1 = 0 ; index1 < sizeOfEachList ;index1++){
						SubOptionModel subOptionModel = subOptionModels.get(index1);
						productName   = subOptionModel.getSubOptionName();
						if(Variables.isPendingOrderItems && extraAg.isPendingItems())
							basePR.onPrintChar(PrintSettings.onReformatName(productName));
						else if(Variables.isPendingOrderItems && !extraAg.isPendingItems()){}
						else 
							basePR.onPrintChar(PrintSettings.onReformatName(productName));
					}}
			}		
		}
		
		if (!Variables.orderComment.isEmpty()) {
			basePR.onLargeText();
			basePR.onPrintChar("\n"+COMMENT);
			basePR.onSmallText();
			basePR.onPrintChar(Variables.orderComment);
		}

		basePR.onLargeText();
		basePR.onPrintChar("\n"+PrintSettings.onFormatHeaderAndFooter(DEFAULT_FOOTER)+"\n");
		basePR.onCutterCmd();
	}

	private static String callFormatMethod(String headerText,String qty,Object price) {
		return PrintSettings.onReformatName(headerText) + PrintSettings.onReformatQty(qty) + PrintSettings.onReformatPrice(MyStringFormat.onFormat(price));
	}
}
