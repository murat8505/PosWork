package com.Database;

import com.Beans.ReportsModel;
import com.Beans.TransactionModel;
import com.Utils.CurrentDate;
import com.Utils.MyStringFormat;

import android.content.Context;

public class SaveTransactionDetails {
	

	public static void saveTransactionInDataBase(Context mContext,float totalAmount, float taxAmount, float totalWithTaxAmount, float cashAmount, float creditAmount, float checkAmount, float giftAmount, float rewardAmount ,String refundStatus, String saveState,float custom1Amt,float custom2Amt){
		String transDate  = CurrentDate.returnCurrentDate();		

		ReportsModel dailyReportsModel = new ReportsModel(				
				MyStringFormat.onFormat(totalAmount + taxAmount)                    , MyStringFormat.onFormat(taxAmount)   , 
				MyStringFormat.onFormat(totalWithTaxAmount)                         , MyStringFormat.onFormat(excludeTaxAmt(cashAmount,taxAmount))  , 
				MyStringFormat.onFormat(excludeTaxAmt(creditAmount,taxAmount))      , MyStringFormat.onFormat(excludeTaxAmt(checkAmount,taxAmount)) , 
				MyStringFormat.onFormat(excludeTaxAmt(giftAmount,taxAmount))        , MyStringFormat.onFormat(excludeTaxAmt(rewardAmount,taxAmount)), 
				transDate                                                           , refundStatus,
				saveState                                                           , ReportsTable.DEFAULT_VALUE, 
				ReportsTable.DEFAULT_VALUE                                          , ReportsTable.DEFAULT_VALUE, 
				ReportsTable.DEFAULT_VALUE                                          , ReportsTable.DEFAULT_VALUE,
				ReportsTable.DEFAULT_VALUE                                          , ReportsTable.DEFAULT_VALUE, 
				ReportsTable.DEFAULT_DESCRIPTION                                    , ReportsTable.DEFAULT_PAYOUT_NAME,
				MyStringFormat.onFormat(excludeTaxAmt(custom1Amt,taxAmount)),MyStringFormat.onFormat(excludeTaxAmt(custom2Amt,taxAmount)));

		new ReportsTable(mContext).addInfoInTable(dailyReportsModel,ReportsTable.DAILY_REPORT);
		new ReportsTable(mContext).addInfoInTable(dailyReportsModel,ReportsTable.SHIFT_REPORT);

	}

	private static float excludeTaxAmt(float payAmt, float taxAmt){
		/*if(payAmt > 0)
			return payAmt-taxAmt;
		else*/
			return payAmt;
	}

	public static void savePayoutsTransactionInDataBase(Context mContext,float lotteryAmt, float expensesAmt, float suppliesAmt, 
			float productAmt, float otherAmt, float tipPayAmt,float manualCashrefAmt, String refundStatus, String saveState,String description,String descrptionPayoutName){

		String transTime               = CurrentDate.returnCurrentDate();

		ReportsModel dailyReportsModel = new ReportsModel(
				ReportsTable.DEFAULT_VALUE                                    , ReportsTable.DEFAULT_VALUE, 
				ReportsTable.DEFAULT_VALUE                                    , ReportsTable.DEFAULT_VALUE, 
				ReportsTable.DEFAULT_VALUE                                    , ReportsTable.DEFAULT_VALUE, 
				ReportsTable.DEFAULT_VALUE                                    , ReportsTable.DEFAULT_VALUE,  
				transTime                                                     , refundStatus,
				saveState                                                     , MyStringFormat.onFormat(lotteryAmt), 
				MyStringFormat.onFormat(expensesAmt)                          , MyStringFormat.onFormat(suppliesAmt) , 
				MyStringFormat.onFormat(productAmt)                           , MyStringFormat.onFormat(otherAmt), 
				MyStringFormat.onFormat(tipPayAmt)                            , MyStringFormat.onFormat(manualCashrefAmt),
				description                                                   , descrptionPayoutName, 
				ReportsTable.DEFAULT_VALUE                                    , ReportsTable.DEFAULT_VALUE);

		new ReportsTable(mContext).addInfoInTable(dailyReportsModel,ReportsTable.DAILY_REPORT);
		new ReportsTable(mContext).addInfoInTable(dailyReportsModel,ReportsTable.SHIFT_REPORT);
	}
	
	public static void saveTransactionWithId(String transactionId,String clerkId,Context mContext){
		TransactionModel transactionModel = new TransactionModel(clerkId, transactionId, CurrentDate.returnCurrentDate());
		new TransactionTable(mContext).addInfoInTable(transactionModel);
	}
}
