package com.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class POSDatabaseHandler extends SQLiteOpenHelper {

	private static POSDatabaseHandler posDbHandler;
	private static SQLiteDatabase posSqliteDb;
	public  static final String DATABASE_NAME = "POSimplicity.db";    
	private static final int DATABASE_VERSION = 1;
	Context mContext;

	public POSDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;		
	}

	public static POSDatabaseHandler getInstance(Context context)  // FactoryMethod to create an instance of DbHandler Object
	{ 		
		if (posDbHandler == null)			
			posDbHandler = new POSDatabaseHandler(context);		
		return posDbHandler;
	}

	public synchronized SQLiteDatabase openWritableDataBase() throws SQLException
	{	
		if(posSqliteDb == null || !posSqliteDb.isOpen())
			posSqliteDb = getWritableDatabase();
		return posSqliteDb;
	}

	public synchronized SQLiteDatabase openReadableDataBase() throws SQLException
	{		
		if(posSqliteDb == null || !posSqliteDb.isOpen())
			posSqliteDb = getReadableDatabase();
		return posSqliteDb;
	}

	public synchronized boolean closeDataBase() throws SQLException {
		if(posSqliteDb != null || posSqliteDb.isOpen()) {
			posSqliteDb.close();
			return true;
		}
		return false;
	}

	public synchronized boolean cusorIsFine(Cursor cursor){
		return cursor != null && cursor.getCount() > 0 ;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			new CustomerTable(mContext).createSchemaOfTable(db);
			new CategoryTable(mContext).createSchemaOfTable(db);
			new ProductOptionTable(mContext).createSchemaOfTable(db);
			new ProductTable(mContext).createSchemaOfTable(db);
			new ReportsTable(mContext).createSchemaOfTable(db);
			new SecurityTable(mContext).createSchemaOfTable(db);
			new StaffTable(mContext).createSchemaOfTable(db);
			new RoleTable(mContext).createSchemaOfTable(db);
			new CommentTable(mContext).createSchemaOfTable(db);
			new PayoutDescTable(mContext).createSchemaOfTable(db);
			new TipTable(mContext).createSchemaOfTable(db);
			new CustomerGroupTable(mContext).createSchemaOfTable(db);
			new TransactionTable(mContext).createSchemaOfTable(db);
			new PaymentModeTable(mContext).createSchemaOfTable(db);
			new OrderTable(mContext).createSchemaOfTable(db);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		dropTable(db, CustomerTable.TABLE_NAME);
		dropTable(db, CategoryTable.TABLE_NAME);
		dropTable(db, ProductOptionTable.TABLE_NAME);		
		dropTable(db, ProductTable.TABLE_NAME);				
		dropTable(db, ReportsTable.TABLE_NAME);
		dropTable(db, SecurityTable.TABLE_NAME); 
		dropTable(db, StaffTable.TABLE_NAME);
		dropTable(db, StaffTable.TABLE_NAME);
		dropTable(db, CommentTable.TABLE_NAME);
		dropTable(db, TipTable.TABLE_NAME);
		dropTable(db, PayoutDescTable.TABLE_NAME);
		dropTable(db, CustomerGroupTable.TABLE_NAME);
		dropTable(db, TransactionTable.TABLE_NAME);
		dropTable(db, PaymentModeTable.TABLE_NAME);
		dropTable(db, OrderTable.TABLE_NAME);

		onCreate(db);
	}

	private void dropTable(SQLiteDatabase db,String DATABASE_TABLE) {
		db.execSQL("DROP TABLE IF EXISTS '" + DATABASE_TABLE + "'");
	}

	public void deleteTableInfo(SQLiteDatabase db){

		db.delete(CustomerTable.TABLE_NAME, "1", null);
		db.delete(CategoryTable.TABLE_NAME, "1", null);
		db.delete(ProductOptionTable.TABLE_NAME, "1", null);
		db.delete(ProductTable.TABLE_NAME, "1", null);
		db.delete(CustomerGroupTable.TABLE_NAME, "1", null);
	}
}

