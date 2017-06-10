package com.Database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Beans.OrderPaymentInfo;

public class OrderTable {

	public static final String TABLE_NAME     = "OrderTable";

	private final String _ID                  = "uniqueId";
	private final String ORDER_ID             = "orderId";
	private final String PAYMENT_MODE_ID      = "paymentModeId";
	private final String PAYMENT_AMOUNT       = "paymentAmount";
	private final String ORDER_DATE           = "orderDate";
	private final String REPORT_NAME          = "reportName";

	private SQLiteDatabase posDataBase;
	private POSDatabaseHandler posDbHandler;

	public OrderTable(Context context) {
		posDbHandler  = POSDatabaseHandler.getInstance(context);
	}

	public void createSchemaOfTable(SQLiteDatabase db){
		try{		    	
			String query = "CREATE TABLE "
					+ TABLE_NAME            +" (        "	
					+ _ID                   +" INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ ORDER_ID              +" TEXT, "
					+ PAYMENT_MODE_ID       +" TEXT, "
					+ PAYMENT_AMOUNT        +" TEXT, "
					+ ORDER_DATE            +" TEXT, "
					+ REPORT_NAME           +" TEXT )";

			Log.e(this.getClass().getName()+" :", "QUERY: -->>" + query);
			db.execSQL(query);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public boolean addInfoInTable(OrderPaymentInfo paymentMode) {
		boolean recordInserted = false;
		try{
			posDataBase           = posDbHandler.openWritableDataBase();
			ContentValues values  = new ContentValues();

			values.put(ORDER_ID,          paymentMode.getOrderId());
			values.put(PAYMENT_MODE_ID,   paymentMode.getPaymentModeId());
			values.put(PAYMENT_AMOUNT,    paymentMode.getPaymentAmount());
			values.put(ORDER_DATE,        paymentMode.getOrderDate());
			values.put(REPORT_NAME,      paymentMode.getReportName());

			// Inserting Row
			long id = posDataBase.insert(TABLE_NAME, null, values);

			if(id != -1)
				recordInserted = true;
		}
		catch(Exception ex){
			ex.printStackTrace();

		}
		finally{
			posDbHandler.closeDataBase();// Closing database connection
		}
		return recordInserted;
	}

	public void updateInfoInTable(OrderPaymentInfo paymentMode) {
		try{
			posDataBase           = posDbHandler.openWritableDataBase();
			ContentValues values  = new ContentValues();

			values.put(ORDER_ID,          paymentMode.getOrderId());
			values.put(PAYMENT_MODE_ID,   paymentMode.getPaymentModeId());
			values.put(PAYMENT_AMOUNT,    paymentMode.getPaymentAmount());
			values.put(ORDER_DATE,        paymentMode.getOrderDate());
			values.put(REPORT_NAME,       paymentMode.getReportName());


			// Update Row
			posDataBase.update(TABLE_NAME, values, ORDER_ID +" =? ", new String[] { String.valueOf(paymentMode.getOrderId()) } );
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			posDbHandler.closeDataBase();// Closing database connection
		}
	}

	public void updateInfoListInTable(List<OrderPaymentInfo> listOfDetails){
		try{
			posDataBase           = posDbHandler.openWritableDataBase();

			for(int index = listOfDetails.size()-1 ; index >= 0 ; index-- ){

				OrderPaymentInfo paymentMode = listOfDetails.get(index);
				ContentValues values = new ContentValues();

				values.put(ORDER_ID,          paymentMode.getOrderId());
				values.put(PAYMENT_MODE_ID,   paymentMode.getPaymentModeId());
				values.put(PAYMENT_AMOUNT,    paymentMode.getPaymentAmount());
				values.put(ORDER_DATE,        paymentMode.getOrderDate());
				values.put(REPORT_NAME,      paymentMode.getReportName());


				// Update Row
				posDataBase.update(TABLE_NAME, values, ORDER_ID +" =? ", new String[] { String.valueOf(paymentMode.getOrderId()) } );
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			posDbHandler.closeDataBase();// Closing database connection
		}
	}

	public void addInfoListInTable(List<OrderPaymentInfo> listOfDetails){
		try{
			posDataBase           = posDbHandler.openWritableDataBase();

			for(int index = listOfDetails.size()-1 ; index >= 0 ; index-- ){

				OrderPaymentInfo paymentMode = listOfDetails.get(index);
				ContentValues values = new ContentValues();

				values.put(ORDER_ID,          paymentMode.getOrderId());
				values.put(PAYMENT_MODE_ID,   paymentMode.getPaymentModeId());
				values.put(PAYMENT_AMOUNT,    paymentMode.getPaymentAmount());
				values.put(ORDER_DATE,        paymentMode.getOrderDate());
				values.put(REPORT_NAME,      paymentMode.getReportName());


				// Inserting Row
				posDataBase.insert(TABLE_NAME, null, values);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			posDbHandler.closeDataBase();// Closing database connection
		}
	}

	public List<OrderPaymentInfo> getAllInfoFromTableDefalut(){
		List<OrderPaymentInfo> listOfAllData = new ArrayList<OrderPaymentInfo>();
		try {
			posDataBase                 = posDbHandler.openReadableDataBase();
			Cursor cursor               = posDataBase.query(TABLE_NAME, null ,null, null, null, null,null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					OrderPaymentInfo paymentMode = new OrderPaymentInfo();					
					paymentMode.setOrderId(cursor.getString(1));
					paymentMode.setPaymentModeId(cursor.getString(2));
					paymentMode.setPaymentAmount(cursor.getString(3));
					paymentMode.setOrderDate(cursor.getString(4));
					paymentMode.setReportName(cursor.getString(5));

					listOfAllData.add(paymentMode);

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			posDbHandler.closeDataBase();
		}

		return listOfAllData;
	}

	public int getLastId() {
		int id = 100;		
		try{
			posDataBase           = posDbHandler.openWritableDataBase();
			String qry            = "Select "+ORDER_ID+" From "+TABLE_NAME + " Order by "+ORDER_ID+" desc limit 1";
			System.out.println(qry);
			Cursor cursor         = posDataBase.rawQuery(qry, null);
			if (cursor != null && cursor.moveToFirst()) {
				id = (Integer.parseInt(cursor.getString(0)));
			}
			cursor.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			posDbHandler.closeDataBase();// Closing database connection
		}
		return id;
	}

	public void clearTable() {
		try{
			posDataBase           = posDbHandler.openWritableDataBase();
			posDataBase.delete(TABLE_NAME, null, null);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			posDbHandler.closeDataBase();// Closing database connection
		}
	}
}
