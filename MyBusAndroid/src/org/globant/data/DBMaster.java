package org.globant.data;

import android.database.sqlite.SQLiteDatabase;

public abstract class DBMaster
{

	protected DBHelper helper;
	protected SQLiteDatabase db;

	public DBMaster()
	{
		super();
	}

	public void ReadableMode()
	{
		db = helper.getReadableDatabase();
	}

	public void WritableMode()
	{
		db = helper.getWritableDatabase();
	}

	public void Close()
	{
		if (db != null)
			db.close();
	}

	abstract void Delete(int id);
	
//	abstract <T> List<T> CreateList(Cursor C);

}