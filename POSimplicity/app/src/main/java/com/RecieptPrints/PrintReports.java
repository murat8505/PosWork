package com.RecieptPrints;

import java.util.List;

import android.content.Context;

import com.Beans.ReportListModel;
import com.Beans.ReportsModel;
import com.Fragments.MaintFragmentTender1;
import com.SetupPrinter.BasePR;
import com.Utils.CurrentDate;

public class PrintReports {

	private final String TOTAL             = "Total:";
	private final String TAX               = "Tax:";
	private final String CASH              = "Cash Total:";
	private final String CREDIT            = "Credit Card:";
	private final String CHECK             = "Check:";		
	private final String GIFT              = "Gift Card:";
	private final String REWARDS           = "Rewards Amount:";
	private final String LOTTERY           = "Lottery Amount:";
	private final String EXPENSES          = "Expenses Amount:";
	private final String SUPPLIES          = "Supplies Amount:";
	private final String PRODUCT           = "Product Amount:";
	private final String OTHERPAY          = "OtherPay Amount:";
	private final String TIPPAY            = "TipPay Amount:";
	private final String MANUAL_REF        = "ManualCashRefund Amount:";
	private final String NOT_RECORED       = "Not Recoreded Amount:";
	private final String MANUALR_REC       = "ManualRecorded Amount:";

	private String callFormatMethod(String headerText,String qty,String price) {
		return PrintSettings.onReformatName(headerText) + PrintSettings.onReformatQty(qty) + PrintSettings.onReformatPrice(price);
	}

	public void onPrintAllTip(Context mContext,BasePR basePr, List<ReportListModel> reportListModel){

		basePr.onPlayBuzzer();
		basePr.onLargeText();

		String formattedString   = CurrentDate.returnCurrentDateWithTime();
		basePr.onPrintChar("   "+formattedString+"\n\n");

		basePr.onPrintChar(PrintSettings.onFormatHeaderAndFooter("Tip Report")+"\n");
		basePr.onSmallText();

		for(int index = 0 ;index < reportListModel.size(); index++){
			formattedString = callFormatMethod(reportListModel.get(index).getNameOfField(),"",reportListModel.get(index).getValueOfField());
			basePr.onPrintChar(formattedString);
		}
		
		basePr.onPrintChar("\n\n"+PrintSettings.onFormatHeaderAndFooter("-----------------------------------")+"\n");
		basePr.onCutterCmd();
	}

	public void onPrintReport(Context mContext,BasePR basePr, ReportsModel shiftReportModel) {

		basePr.onPlayBuzzer();
		basePr.onLargeText();

		String formattedString = CurrentDate.returnCurrentDateWithTime();

		basePr.onPrintChar("   "+formattedString+"\n\n");
		basePr.onPrintChar(PrintSettings.onFormatHeaderAndFooter(shiftReportModel.getReportName())+"\n");

		basePr.onSmallText();
		formattedString = callFormatMethod(TOTAL, "", shiftReportModel.getTotalAmount());
		basePr.onPrintChar(formattedString);
		
		formattedString = callFormatMethod(CASH, "", shiftReportModel.getCashAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(CREDIT, "", shiftReportModel.getCreditAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(CHECK, "", shiftReportModel.getCheckAmount());
		basePr.onPrintChar(formattedString);
		
		formattedString = callFormatMethod(MaintFragmentTender1.getCustom1Name(mContext)+" :", "", shiftReportModel.getCustom1Amount());
		basePr.onPrintChar(formattedString);
		
		formattedString = callFormatMethod(MaintFragmentTender1.getCustom2Name(mContext)+" :", "", shiftReportModel.getCustom2Amount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(GIFT, "", shiftReportModel.getGiftAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(REWARDS, "", shiftReportModel.getRewardAmount());
		basePr.onPrintChar(formattedString);
		
		formattedString = callFormatMethod(TAX, "", shiftReportModel.getTaxAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod("Tip Amount:", "", shiftReportModel.getTipAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(LOTTERY, "", shiftReportModel.getLotteryAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(EXPENSES, "", shiftReportModel.getExpensesAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(SUPPLIES, "", shiftReportModel.getSuppliesAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(PRODUCT, "", shiftReportModel.getProductAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(OTHERPAY, "", shiftReportModel.getOtherAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(TIPPAY, "", shiftReportModel.getTipPayAmount());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(MANUAL_REF, "", shiftReportModel.getManualCashRefund());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(NOT_RECORED, "", shiftReportModel.getNoInternetOrders());
		basePr.onPrintChar(formattedString);

		formattedString = callFormatMethod(MANUALR_REC, "", shiftReportModel.getManuallyRecOrders());
		basePr.onPrintChar(formattedString);

		basePr.onPrintChar("\n\n"+PrintSettings.onFormatHeaderAndFooter("-----------------------------------")+"\n");
		basePr.onCutterCmd();


	}

}
