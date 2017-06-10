package com.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.Utils.MyPreferences;
import com.posimplicity.R;

public class BridgePaymentFragment extends BaseFragment{
	
	public  EditText bridgeGatewayPassword,bridgeGatewayUserName;	 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		rootView   				  = inflater.inflate(R.layout.fragment_bridgepay, container, false);
		bridgeGatewayUserName     = findViewIdAndCast(R.id.Fragment_Bridge_Payment_ET_UserName);
		bridgeGatewayPassword     = findViewIdAndCast(R.id.Fragment_Bridge_Payment_ET_Password);
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
		
		bridgeGatewayUserName.setText(MyPreferences.getMyPreference(BRIDGE_GATEWAY_USERNAME, mContext));		
		bridgeGatewayPassword.setText(MyPreferences.getMyPreference(BRIDGE_GATEWAY_PASSWORD, mContext));
	}
}
