package com.posimplicity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.AlertDialogs.DetailsOfEachPayouts;
import com.Beans.ReportListModel;
import com.Beans.ReportsModel;
import com.CustomAdapter.ReportAdapter;
import com.Database.ReportsTable;
import com.Database.TipTable;
import com.Fragments.MaintFragmentTender1;
import com.RecieptPrints.PrintReports;
import com.RecieptPrints.PrintSettings;
import com.SetupPrinter.BasePR;
import com.SetupPrinter.PrinterCallBack;
import com.SetupPrinter.UsbPR;
import com.Utils.CurrentDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DailyReportAsChild extends BaseActivity implements OnItemClickListener, OnClickListener{

	private ReportsModel dailyReportModel;	
	private ListView daliyReportListView;
	private ReportAdapter reportListAdapter;
	private List<ReportListModel> reportListModel;
	private Button printBtn,chartAct;
	private String transTime ;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState,false,this);
		setContentView(R.layout.activity_daily_report_as_child);

		onInitViews();
		onListenerRegister();	

		transTime        = CurrentDate.returnCurrentDate();

		dailyReportModel = new ReportsTable(mContext).getReportModel(transTime,ReportsTable.DAILY_REPORT);
		String tipAmt    = new TipTable(mContext).getSumOfTips(transTime);
		dailyReportModel.setTipAmount(tipAmt);
		dailyReportModel.setNoInternetOrders (new ReportsTable(mContext).getSumOfTotalAmountBasedOnDynamicValues(transTime, ReportsTable.DAILY_REPORT, ReportsTable.FAILED));
		dailyReportModel.setManuallyRecOrders(new ReportsTable(mContext).getSumOfTotalAmountBasedOnDynamicValues(transTime, ReportsTable.DAILY_REPORT, ReportsTable.MANUALLY_ENTRY));


		reportListModel.add(new ReportListModel(ReportsTable.TOTAL_AMOUNT,			 dailyReportModel.getTotalAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.CASH_AMOUNT, 			 dailyReportModel.getCashAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.CREDIT_AMOUNT,     	 dailyReportModel.getCreditAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.CHECK_AMOUNT, 			 dailyReportModel.getCheckAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.GIFT_AMOUNT,   		 dailyReportModel.getGiftAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.REWARDS_AMOUNT,         dailyReportModel.getRewardAmount()));
		reportListModel.add(new ReportListModel(MaintFragmentTender1.getCustom1Name(mContext), 			 dailyReportModel.getCustom1Amount()));
		reportListModel.add(new ReportListModel(MaintFragmentTender1.getCustom2Name(mContext), 			 dailyReportModel.getCustom2Amount()));
		reportListModel.add(new ReportListModel(ReportsTable.TAX_AMOUNT, 			 dailyReportModel.getTaxAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.TIP_AMOUNT,             dailyReportModel.getTipAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.LOTTERY_AMOUNT, 		 dailyReportModel.getLotteryAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.EXPENSES_AMOUNT, 		 dailyReportModel.getExpensesAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.SUPPLIES_AMOUNT,		 dailyReportModel.getSuppliesAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.PRODUCT_AMOUNT, 		 dailyReportModel.getProductAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.OTHER_AMOUNT,   		 dailyReportModel.getOtherAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.TIP_Pay_AMOUNT,   		 dailyReportModel.getTipPayAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.MANUAL_CASH_REFUND,     dailyReportModel.getManualCashRefund()));
		reportListModel.add(new ReportListModel(ReportsTable.NO_INTERNET_ORDERS,     dailyReportModel.getNoInternetOrders()));
		reportListModel.add(new ReportListModel(ReportsTable.MANUALLY_RECORDED_ORDERS, dailyReportModel.getManuallyRecOrders()));


		daliyReportListView.setOnItemClickListener(this);
		reportListAdapter.notifyDataSetChanged();
	}	

	@Override
	public void onInitViews() {

		daliyReportListView    = findViewByIdAndCast(R.id.Activity_DailyReport_ListView_Items_);
		printBtn               = findViewByIdAndCast(R.id.Activity_DailyReport_Button_Print_);
		chartAct               = findViewByIdAndCast(R.id.Activity_DailyReport_Button_New_ChartAct);

		reportListModel        = new ArrayList<>();
		reportListAdapter      = new ReportAdapter(mContext, reportListModel);

		daliyReportListView.setAdapter(reportListAdapter);
		dailyReportModel      = new ReportsModel();
	}

	@Override
	public void onListenerRegister() {

		printBtn.setOnClickListener(this);
		chartAct.setOnClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

		if(position > 7 && position < 15){

			ReportListModel rpModel           = reportListModel.get(position);
			String payoutName                 = rpModel.getNameOfField();
			List<String> listOFEachPayOut     = new ReportsTable(mContext).getListOfSomeInfoBasedOnDynamicValue(payoutName,ReportsTable.DAILY_REPORT,transTime,payoutName);
			List<String> listOFEachPayOutDesc = new ReportsTable(mContext).getListOfSomeInfoBasedOnDynamicValue(ReportsTable.DESCRIPTION,ReportsTable.DAILY_REPORT,transTime,payoutName);

			float totalAmont = sumOfAllValues(listOFEachPayOut);
			new DetailsOfEachPayouts().onDetailsOfEachPayouts(mContext, listOFEachPayOut, listOFEachPayOutDesc,totalAmont, payoutName);

		}
	}

	public float sumOfAllValues(List<String> listOFEachPayOut){
		float sumOfAmount = 0.0f;

		for(int index = listOFEachPayOut.size()-1 ; index >= 0 ; index -- ){
			sumOfAmount += Float.parseFloat(listOFEachPayOut.get(index));
		}
		return sumOfAmount;		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.Activity_DailyReport_Button_Print_:
			
			if(PrintSettings.isAbleToPrintCustomerReceiptThroughUsb(mContext)){
				new UsbPR(mContext, new PrinterCallBack() {
					
					@Override
					public void onStop() {}
					
					@Override
					public void onStarted(BasePR printerCmmdO) {						
						new PrintReports().onPrintReport(DailyReportAsChild.this,printerCmmdO,dailyReportModel);
					}
					
				}).onStart();
			}
			
			if(PrintSettings.isAbleToPrintCustomerReceiptThroughBluetooth(mContext)){				
				new PrintReports().onPrintReport(DailyReportAsChild.this,globalApp.getmBasePrinterBT(),dailyReportModel);			
			}
			
			break;

		case R.id.Activity_DailyReport_Button_New_ChartAct:		

			Intent intent = new Intent(mContext, ChartViewActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	@Override
	public void onDataRecieved(JSONArray arry) {}

	@Override
	public void onSocketStateChanged(int state) {}

}
