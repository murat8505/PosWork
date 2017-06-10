package com.Dialogs;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Utils.StartAndroidActivity;
import com.Utils.ToastUtils;
import com.Utils.WebServiceCall;
import com.posimplicity.R;
import com.posimplicity.ReprintActivity;

public class SelectCustomDate extends BaseDialog implements View.OnClickListener{

	private TextView startDateTv,endDateTv,processNowTv,startDateTvString,endDateTvString;
	private ImageButton cancelView;
	private WebServiceCall webServiceCall;


	public SelectCustomDate(Context context, int theme, int width,
			int height, boolean isOutSideTouch, boolean isCancelable,
			int layoutId) {
		super(context, theme, width, height, isOutSideTouch, isCancelable, layoutId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cancelView            = findViewByIdAndCast(R.id.Dialog_Custome_Date_Tv_Cancel);
		startDateTv           = findViewByIdAndCast(R.id.Dialog_Custome_Date_Tv_FromDate);
		endDateTv             = findViewByIdAndCast(R.id.Dialog_Custome_Date_Tv_ToDate);
		startDateTvString     = findViewByIdAndCast(R.id.Dialog_Custome_Date_Tv_FromDateString);
		endDateTvString       = findViewByIdAndCast(R.id.Dialog_Custome_Date_Tv_ToDateString);
		processNowTv          = findViewByIdAndCast(R.id.Dialog_Custome_Date_BTN_FetchRecord);
		cancelView.setOnClickListener(this);
		processNowTv.setOnClickListener(this);	
		startDateTv.setOnClickListener(this);
		endDateTv.setOnClickListener(this);
	}	

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.Dialog_Custome_Date_Tv_Cancel:
			StartAndroidActivity.onActivityStart(false, mContext, ReprintActivity.class);
			dismiss();
			break;

		case R.id.Dialog_Custome_Date_Tv_FromDate:
			openDatePicker(fromDateListener);

			break;

		case R.id.Dialog_Custome_Date_Tv_ToDate:
			openDatePicker(toDateListener);

			break;

		case R.id.Dialog_Custome_Date_BTN_FetchRecord:

			if(startDateTvString.getText().toString().isEmpty()){
				ToastUtils.showOwnToast(mContext, "Please Select From Date First");
				return;
			}

			if(endDateTvString.getText().toString().isEmpty()){
				ToastUtils.showOwnToast(mContext, "Please Select To Date First");
				return;
			}

			StringBuilder stBuilder = new StringBuilder(webServiceCall.getRequetsedUrl());
			stBuilder.append("&from_date="+startDateTvString.getText().toString());
			stBuilder.append("&to_date="  +endDateTvString.getText().toString());

			webServiceCall.setRequetsedUrl(stBuilder.toString());
			webServiceCall.execute();
			dismiss();
			break;


		default:
			break;
		}
	}

	OnDateSetListener fromDateListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			startDateTvString.setText(""+year+"-"+monthOfYear+"-"+dayOfMonth);			
		}
	};

	OnDateSetListener toDateListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			endDateTvString.setText(""+year+"-"+monthOfYear+"-"+dayOfMonth);
		}
	};


	private void openDatePicker(OnDateSetListener dataListner) {
		Calendar calendar = Calendar.getInstance();		
		DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, dataListner, calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}

	public void show(WebServiceCall webServiceCall) {
		this.webServiceCall = webServiceCall;
		show();
	}  
}