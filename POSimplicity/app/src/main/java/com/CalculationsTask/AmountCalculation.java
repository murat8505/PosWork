package com.CalculationsTask;

import java.util.Iterator;
import com.Beans.CheckOutParentModel;
import com.Beans.ProductModel;
import com.Utils.MyStringFormat;
import com.posimplicity.HomeActivity;
import android.content.Context;

public class AmountCalculation {	

	protected Context mContext;
	private HomeActivity instance;		

	public AmountCalculation(Context mContext) {
		super();
		this.mContext = mContext;
		instance      = HomeActivity.localInstance;
	}

	public String getItemsAmt() {
		float itemstotalPrice = 0;

		for(int index = instance.dataList.size() - 1 ; index >= 0 ;index --){
			CheckOutParentModel parent = instance.dataList.get(index);
			ProductModel product       = parent.getProduct();

			if(!parent.getExtraArgument().isSurageApplicable()){
				itemstotalPrice += Float.parseFloat(product.getProductCalAmount());
			}			
		}
		return MyStringFormat.onFormat(itemstotalPrice);
	}

	public String getItemsTaxAmt() {

		float itemTaxs = 0;
		for(int index = instance.dataList.size() - 1 ; index >= 0 ;index --){
			CheckOutParentModel parent = instance.dataList.get(index);
			ProductModel product       = parent.getProduct();

			if(!parent.getExtraArgument().isSurageApplicable()){
				itemTaxs += Float.parseFloat(product.getProductTaxRate()) * (Float.parseFloat(product.getProductCalAmount()) - Float.parseFloat(product.getProductDisAmount()));
			}			
		}
		
		return MyStringFormat.onFormat(itemTaxs);
	}

	public ProductModel isSurchagreProductExist()
	{
		Iterator<CheckOutParentModel> iterator = instance.dataList.iterator();		
		while (iterator.hasNext()) {
			CheckOutParentModel parent = (CheckOutParentModel) iterator.next();
			if(parent.getExtraArgument().isSurageApplicable()){
				return parent.getProduct();
			}
		}
		return null;	
	}

	public String getItemsDiscountAmt(){
		float itemsDisAmt = 0;

		for(int index = instance.dataList.size() - 1 ; index >= 0 ;index --){
			CheckOutParentModel parent = instance.dataList.get(index);
			ProductModel product       = parent.getProduct();

			if(!parent.getExtraArgument().isSurageApplicable()){
				itemsDisAmt += Float.parseFloat(product.getProductDisAmount());
			}			
		}
		return MyStringFormat.onFormat(itemsDisAmt);
	}

/*	public String getExtraAmount(){

		float fees = 0;
		Iterator<CheckOutParentModel> iterator = instance.dataList.iterator();		
		while (iterator.hasNext()) {
			CheckOutParentModel parent = (CheckOutParentModel) iterator.next();
			if(!parent.getExtraArgument().isSurageApplicable()){
				ExtraProductArgument product =  parent.getExtraArgument();
				fees += product.getCustomProductPrice();
			}
		}	
		String	value = String.format(Locale.ENGLISH, "%.2f", fees);		
		return value;
	}*/

}
