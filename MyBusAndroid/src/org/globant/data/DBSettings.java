package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Setting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBSettings extends DBMaster {

	// Table name
	public static final String TABLE_NAME = "Settings";

	public static final String CN_ID = "_id";
	public static final String CN_SHOWFAVORITES = "showfavorites";
	public static final String CN_SHOWSTATIONS = "showstations";

	public static String create_table() {
		String query = "CREATE TABLE "+TABLE_NAME+" ( "+
									CN_ID+" INTEGER PRIMARY KEY,"+
									CN_SHOWFAVORITES+" INTEGER NOT NULL CHECK ("+CN_SHOWFAVORITES+" = 0 OR "+CN_SHOWFAVORITES+" = 1 ),"+
									CN_SHOWSTATIONS+" INTEGER NOT NULL CHECK ("+CN_SHOWSTATIONS+" = 0 OR "+CN_SHOWSTATIONS+" = 1 )" +
									")";
		return query;
	}

	private String[] columns = new String[] { CN_ID, CN_SHOWFAVORITES, CN_SHOWSTATIONS };

	public DBSettings(Context context) {
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(int showfavorites,int showstations) {
		ContentValues cont = new ContentValues();
		cont.put(CN_SHOWFAVORITES, showfavorites);
		cont.put(CN_SHOWSTATIONS, showstations);
		return cont;
	}

	public void Insert(int showfavorites,int showstations) {
		db.insert(TABLE_NAME, null, ValuesContainer(showfavorites,showstations));
	}

	public List<Setting> CreateList(Cursor c) {
		List<Setting> List_settings = new ArrayList<Setting>();
		if (c.moveToFirst()) {
			do {
				int id = c.getColumnIndex(CN_ID);
				int showfavorites = c.getColumnIndex(CN_SHOWFAVORITES);
				int showstations = c.getColumnIndex(CN_SHOWSTATIONS);
				Setting set = new Setting(c.getInt(id), c.getInt(showfavorites), c.getInt(showstations));
				List_settings.add(set);
			} while (c.moveToNext());
		}
		c.close();
		return List_settings;
	}

	public List<Setting> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}

	@Override
	public void Delete(int id)
	{
		// TODO Implement deletion procedure
	}
	
	public void Update(int id, int showfavorites, int showstations)
	{
		ContentValues reg = ValuesContainer(showfavorites, showstations);
		
		reg.put(CN_ID, id);
		
		db.update(TABLE_NAME, reg, null, null);
	}
	

}
