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

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShiftReportActivity extends BaseActivity implements OnItemClickListener, OnClickListener{

	private ReportsModel shiftReportModel;	
	private ListView daliyReportListView;
	private ReportAdapter reportListAdapter;
	private List<ReportListModel> reportListModel;
	private Button printBtn,newShiftBtn;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState,false,this);
		setContentView(R.layout.activity_shift_report_as_child);

		onInitViews();
		onListenerRegister();

		shiftReportModel = new ReportsTable(mContext).getReportModel(null,ReportsTable.SHIFT_REPORT);
		shiftReportModel.setTipAmount(new TipTable(mContext).getSumOfTipsWithOutDate());

		reportListModel.add(new ReportListModel(ReportsTable.TOTAL_AMOUNT,			 shiftReportModel.getTotalAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.CASH_AMOUNT, 			 shiftReportModel.getCashAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.CREDIT_AMOUNT,     	 shiftReportModel.getCreditAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.CHECK_AMOUNT, 			 shiftReportModel.getCheckAmount()));		
		reportListModel.add(new ReportListModel(ReportsTable.GIFT_AMOUNT,   		 shiftReportModel.getGiftAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.REWARDS_AMOUNT,         shiftReportModel.getRewardAmount()));
		reportListModel.add(new ReportListModel(MaintFragmentTender1.getCustom1Name(mContext), 			 shiftReportModel.getCustom1Amount()));
		reportListModel.add(new ReportListModel(MaintFragmentTender1.getCustom2Name(mContext), 			 shiftReportModel.getCustom2Amount()));
		reportListModel.add(new ReportListModel(ReportsTable.TAX_AMOUNT, 			 shiftReportModel.getTaxAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.TIP_AMOUNT,             shiftReportModel.getTipAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.LOTTERY_AMOUNT, 		 shiftReportModel.getLotteryAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.EXPENSES_AMOUNT, 		 shiftReportModel.getExpensesAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.SUPPLIES_AMOUNT,		 shiftReportModel.getSuppliesAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.PRODUCT_AMOUNT, 		 shiftReportModel.getProductAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.OTHER_AMOUNT,   		 shiftReportModel.getOtherAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.TIP_Pay_AMOUNT,   		 shiftReportModel.getTipPayAmount()));
		reportListModel.add(new ReportListModel(ReportsTable.MANUAL_CASH_REFUND,     shiftReportModel.getManualCashRefund()));

		daliyReportListView.setOnItemClickListener(this);
		reportListAdapter.notifyDataSetChanged();
	}	

	@Override
	public void onInitViews() {

		daliyReportListView    = findViewByIdAndCast(R.id.Activity_ShiftReport_ListView_Items_);
		printBtn               = findViewByIdAndCast(R.id.Activity_ShiftReport_Button_Print_);
		newShiftBtn            = findViewByIdAndCast(R.id.Activity_ShiftReport_Button_New_Shift);

		reportListModel        = new ArrayList<>();
		reportListAdapter      = new ReportAdapter(mContext, reportListModel);

		daliyReportListView.setAdapter(reportListAdapter);
		shiftReportModel      = new ReportsModel();
	}

	@Override
	public void onListenerRegister() {

		printBtn.setOnClickListener(this);
		newShiftBtn.setOnClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

		if(position > 7){

			ReportListModel rpModel           = reportListModel.get(position);
			String payoutName                 = rpModel.getNameOfField();
			List<String> listOFEachPayOut     = new ReportsTable(mContext).getListOfSomeInfoBasedOnDynamicValue(payoutName,ReportsTable.SHIFT_REPORT,"",payoutName);
			List<String> listOFEachPayOutDesc = new ReportsTable(mContext).getListOfSomeInfoBasedOnDynamicValue(ReportsTable.DESCRIPTION,ReportsTable.SHIFT_REPORT,"",payoutName);

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

		case R.id.Activity_ShiftReport_Button_Print_:
			
			if(PrintSettings.isAbleToPrintCustomerReceiptThroughUsb(mContext)){
				new UsbPR(mContext, new PrinterCallBack() {
					
					@Override
					public void onStop() {}
					
					@Override
					public void onStarted(BasePR printerCmmdO) {						
						new PrintReports().onPrintReport(ShiftReportActivity.this,printerCmmdO,shiftReportModel);
					}
					
				}).onStart();
			}
			
			if(PrintSettings.isAbleToPrintCustomerReceiptThroughBluetooth(mContext)){				
				new PrintReports().onPrintReport(ShiftReportActivity.this,globalApp.getmBasePrinterBT(),shiftReportModel);			
			}

			break;

		case R.id.Activity_ShiftReport_Button_New_Shift:		

			shiftReportModel = new ReportsModel();
			shiftReportModel.setReportName(ReportsTable.SHIFT_REPORT);
			reportListModel.clear();
			reportListModel.add(new ReportListModel(ReportsTable.TOTAL_AMOUNT,			 shiftReportModel.getTotalAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.CASH_AMOUNT, 			 shiftReportModel.getCashAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.CREDIT_AMOUNT,     	 shiftReportModel.getCreditAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.CHECK_AMOUNT, 			 shiftReportModel.getCheckAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.GIFT_AMOUNT,   		 shiftReportModel.getGiftAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.REWARDS_AMOUNT,         shiftReportModel.getRewardAmount()));
			reportListModel.add(new ReportListModel(MaintFragmentTender1.getCustom1Name(mContext), 			 shiftReportModel.getCustom1Amount()));
			reportListModel.add(new ReportListModel(MaintFragmentTender1.getCustom2Name(mContext), 			 shiftReportModel.getCustom2Amount()));
			reportListModel.add(new ReportListModel(ReportsTable.TAX_AMOUNT, 			 shiftReportModel.getTaxAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.TIP_Pay_AMOUNT,         shiftReportModel.getTipAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.LOTTERY_AMOUNT, 		 shiftReportModel.getLotteryAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.EXPENSES_AMOUNT, 		 shiftReportModel.getExpensesAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.SUPPLIES_AMOUNT,		 shiftReportModel.getSuppliesAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.PRODUCT_AMOUNT, 		 shiftReportModel.getProductAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.OTHER_AMOUNT,   		 shiftReportModel.getOtherAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.TIP_Pay_AMOUNT,   		 shiftReportModel.getTipPayAmount()));
			reportListModel.add(new ReportListModel(ReportsTable.MANUAL_CASH_REFUND,     shiftReportModel.getManualCashRefund()));
			reportListAdapter.notifyDataSetChanged();

			new ReportsTable(mContext).deleteInfoFromTable(ReportsTable.SHIFT_REPORT);
			new TipTable(mContext).deleteInfoFromTable();

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


