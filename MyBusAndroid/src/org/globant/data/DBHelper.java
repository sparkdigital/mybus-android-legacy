package org.globant.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DB_NAME = "MyBussApp.sqlite";
	private static final int DB_SCHEME_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_SCHEME_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.CreateTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Is an upgrade method necessary?
		
	}
	
	private void CreateTables(SQLiteDatabase db){
		db.execSQL(DBLocation_types.create_table());
		db.execSQL(DBLocations.create_table());
		db.execSQL(DBSystem_locations.create_table());
		db.execSQL(DBSearches.create_table());
		db.execSQL(DBSettings.create_table());
		db.execSQL(DBTickets.create_table());
	}

}
