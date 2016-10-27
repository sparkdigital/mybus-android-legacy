package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Bus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBBuses extends DBMaster {
	// Table name
	public static final String TABLE_NAME = "Buses";

	public static final String CN_ID = "_id";
	public static final String CN_NAME = "name";
	public static final String CN_COLOR = "color";

	public static String create_table() {
		String query = "CREATE TABLE "+TABLE_NAME+" ("+
											CN_ID+" INTEGER PRIMARY KEY,"+
											CN_NAME+" TEXT NOT NULL,"+
											CN_COLOR+" TEXT NOT NULL "+
											")";
		return query;
	}

	private String[] columns = new String[] { CN_ID, CN_NAME , CN_COLOR};

	public DBBuses(Context context) {
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(String name, String color) {
		ContentValues cont = new ContentValues();
		cont.put(CN_NAME, name);
		cont.put(CN_COLOR, color);
		return cont;
	}

	public void Insert(String name, String color) {
		db.insert(TABLE_NAME, null, ValuesContainer(name,color));
	}

	@Override
	public void Delete(int id) {
		// TODO Implement deletion procedure
	}

	public List<Bus> CreateList(Cursor c) {
		List<Bus> List_bus = new ArrayList<Bus>();
		if (c.moveToFirst()) {
			do {
				int id = c.getColumnIndex(CN_ID);
				int name = c.getColumnIndex(CN_NAME);
				int color = c.getColumnIndex(CN_COLOR);
				Bus bus = new Bus(c.getInt(id), c.getString(name), c.getString(color));
				List_bus.add(bus);
			} while (c.moveToNext());
		}
		c.close();
		return List_bus;
	}

	public List<Bus> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}
}
