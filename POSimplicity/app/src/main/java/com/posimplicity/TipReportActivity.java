package com.posimplicity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.Beans.CustomerModel;
import com.Beans.ReportListModel;
import com.CustomAdapter.ReportAdapter;
import com.Database.ReportsTable;
import com.Database.TipTable;
import com.RecieptPrints.PrintReports;
import com.RecieptPrints.PrintSettings;
import com.SetupPrinter.BasePR;
import com.SetupPrinter.PrinterCallBack;
import com.SetupPrinter.UsbPR;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TipReportActivity extends BaseActivity implements  OnClickListener{

	private ListView daliyReportListView;
	private ReportAdapter reportListAdapter;
	private List<ReportListModel> reportListModel;
	private Button printBtn,newShiftBtn;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState,false,this);

		setContentView(R.layout.activity_shift_report_as_child);

		onInitViews();
		onListenerRegister();

		List<CustomerModel> customerList = new TipTable(mContext).getAllInfoFromTable();
		reportListModel.clear();

		if(customerList.size() > 0){
			for(int index = customerList.size()-1 ; index >= 0 ; index --){
				CustomerModel customerModel   = customerList.get(index);
				reportListModel.add(new ReportListModel(customerModel.getFirstName(),customerModel.getTipAmount()));
			}
		}

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
	}

	@Override
	public void onListenerRegister() {

		printBtn.setOnClickListener(this);
		newShiftBtn.setOnClickListener(this);

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
						new PrintReports().onPrintAllTip(TipReportActivity.this,printerCmmdO,reportListModel);
					}

				}).onStart();
			}

			if(PrintSettings.isAbleToPrintCustomerReceiptThroughBluetooth(mContext)){				
				new PrintReports().onPrintAllTip(TipReportActivity.this,globalApp.getmBasePrinterBT(),reportListModel);			
			}

			break;

		case R.id.Activity_ShiftReport_Button_New_Shift:	

			new TipTable(mContext).deleteInfoFromTable();
			new ReportsTable(mContext).deleteInfoFromTable(ReportsTable.SHIFT_REPORT);
			reportListModel.clear();
			reportListAdapter.notifyDataSetChanged();

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


