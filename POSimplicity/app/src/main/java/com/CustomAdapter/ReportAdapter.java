package com.CustomAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Beans.ReportListModel;
import com.posimplicity.R;

import java.util.List;

public class ReportAdapter extends BaseAdapter {

	private List<ReportListModel> dataList;
	private LayoutInflater lInflater;

	public ReportAdapter(Context mContext, List<ReportListModel> dataList) {
		super();
		this.dataList  = dataList;
		this.lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public ReportListModel getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReportHolder holder;
		if(convertView == null){
			holder = new ReportHolder();
			convertView = lInflater.inflate(R.layout.report_rows, null);
			holder.fieldName  = (TextView) convertView.findViewById(R.id.fieldName);
			holder.fieldValue = (TextView) convertView.findViewById(R.id.fieldValue);
			convertView.setTag(holder);
		}
		else
			holder  = 	(ReportHolder) convertView.getTag();
		ReportListModel localObj = getItem(position);

		holder.fieldName.setTextColor(Color.RED);
		holder.fieldValue.setTextColor(Color.RED);
		holder.fieldName.setText(localObj.getNameOfField());
		holder.fieldValue.setText(localObj.getValueOfField());	

		if(position > 7 && position <= 9){
			holder.fieldName.setTextColor(Color.GREEN);
			holder.fieldValue.setTextColor(Color.GREEN);
		}
		else if(position > 9 && position <= 16){
			holder.fieldName.setTextColor(Color.BLUE);
			holder.fieldValue.setTextColor(Color.BLUE);
		}
		else if(position > 16)
		{
			holder.fieldName.setTextColor(Color.DKGRAY);
			holder.fieldValue.setTextColor(Color.DKGRAY);
		}

		/*if(position >= 0 && position < 8){
			holder.fieldName.setTextColor(Color.parseColor("#990012"));
			holder.fieldValue.setTextColor(Color.parseColor("#990012"));
			holder.fieldName.setText(localObj.getNameOfField());
			holder.fieldValue.setText(localObj.getValueOfField());	
		}

		else if(position > 7 && position < 15){
			holder.fieldName.setTextColor(Color.parseColor("#000080"));
			holder.fieldValue.setTextColor(Color.parseColor("#000080"));
			holder.fieldName.setText(localObj.getNameOfField());
			holder.fieldValue.setText(localObj.getValueOfField());
		}
		else if(position >= 15)
		{
			holder.fieldName.setTextColor(Color.parseColor("#7F5217"));
			holder.fieldValue.setTextColor(Color.parseColor("#7F5217"));
			holder.fieldName.setText(localObj.getNameOfField());
			holder.fieldValue.setText(localObj.getValueOfField());
		}*/
		return convertView;
	}

	public class ReportHolder{
		TextView fieldName,fieldValue;
	}

}
