package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBLocations extends DBMaster {

	// Table name
	public static final String TABLE_NAME = "Locations";

	public static final String CN_ID = "_id";
	public static final String CN_LATITUDE = "latitude";
	public static final String CN_LONGITUDE = "longitude";
	public static final String CN_STREET = "street";
	//public static final String CN_NUMBER = "number";
	public static final String CN_SEARCH_COUNT = "search_count";
	public static final String CN_LAST_SEARCH = "last_search";
	public static final String CN_ISCRITICAL = "iscritical";
	public static final String CN_ISFAVORITE = "isfavorite";
	public static final String CN_OBSERVATIONS = "observations";

	public static String create_table() {
		String query = "CREATE TABLE "+TABLE_NAME+" ( "+
										CN_ID+" INTEGER PRIMARY KEY, "+
										CN_LATITUDE+" REAL NOT NULL, "+
										CN_LONGITUDE+" REAL NOT NULL, "+
										CN_STREET+" TEXT, "+
										CN_SEARCH_COUNT+" INTEGER DEFAULT 0, "+
										CN_LAST_SEARCH+" TEXT DEFAULT TIMESTAMP, "+
										CN_ISCRITICAL+" INTEGER NOT NULL CHECK ("+CN_ISCRITICAL+" = 0 OR "+CN_ISCRITICAL+" = 1), "+
										CN_ISFAVORITE+" INTEGER NOT NULL CHECK ("+CN_ISFAVORITE+" = 0 OR "+CN_ISFAVORITE+" = 1), "+
										CN_OBSERVATIONS+" TEXT )";
		return query;
	}
	
	private String[] columns = new String[] { CN_ID, CN_LATITUDE,CN_LONGITUDE,CN_STREET,CN_SEARCH_COUNT,CN_LAST_SEARCH,CN_ISCRITICAL,CN_ISFAVORITE,CN_OBSERVATIONS};


	public DBLocations(Context context) {
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(double latitude,double longitude,String street,int search_count,int iscritical,int isfavorite,String observations) {
		ContentValues cont = new ContentValues();
		cont.put(CN_LATITUDE, latitude);
		cont.put(CN_LONGITUDE, longitude);
		cont.put(CN_STREET, street);
//		cont.put(CN_NUMBER, number);
		cont.put(CN_SEARCH_COUNT, search_count);
		cont.put(CN_ISCRITICAL, iscritical);
		cont.put(CN_ISFAVORITE, isfavorite);
		cont.put(CN_OBSERVATIONS, observations);
		return cont;
	}

	public void Insert(double latitude,double longitude,String street,int search_count,int iscritical,int isfavorite,String observations) {
		db.insert(TABLE_NAME, null, ValuesContainer(latitude,longitude,street,search_count,iscritical,isfavorite,observations));
	}

	@Override
	public void Delete(int id) 
	{
		db.delete(TABLE_NAME, "_id=" + id, null);
	}
	
	public void DeleteFavorites() {
		String query = "DELETE FROM "+TABLE_NAME+" WHERE "+CN_ISFAVORITE+" = 1";
		db.execSQL(query);
	}

	public List<Location> CreateList(Cursor c) {
		List<Location> List_loc = new ArrayList<Location>();
		if (c.moveToFirst()) {
			do {
				int id = c.getColumnIndex(CN_ID);
				int latitude = c.getColumnIndex(CN_LATITUDE);
				int longitude = c.getColumnIndex(CN_LONGITUDE);
				int street = c.getColumnIndex(CN_STREET);
//				int number = c.getColumnIndex(CN_NUMBER);
				int search_count = c.getColumnIndex(CN_SEARCH_COUNT);
				int last_search = c.getColumnIndex(CN_LAST_SEARCH);
				int iscritical = c.getColumnIndex(CN_ISCRITICAL);
				int isfavorite = c.getColumnIndex(CN_ISFAVORITE);
				int observations = c.getColumnIndex(CN_OBSERVATIONS);
				Location loc = new Location(c.getInt(id), c.getDouble(latitude), c.getDouble(longitude), c.getString(street),
											c.getInt(search_count), c.getString(last_search), c.getInt(iscritical), c.getInt(isfavorite), c.getString(observations));
				List_loc.add(loc);
			} while (c.moveToNext());
		}
		c.close();
		return List_loc;
	}

	public List<Location> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}
	
	public List<Location> getListFavorites(){
		Cursor c = db.query(TABLE_NAME, columns, CN_ISFAVORITE+" = 1", null, null, null, null);
		return CreateList(c);
	}
	
	public List<Location> getListCriticals(){
		Cursor c = db.query(TABLE_NAME, columns, CN_ISCRITICAL+" = 1", null, null, null, null);
		return CreateList(c);
	}
	
	public Location GiveLocation(int id_location){
		Cursor c = db.query(TABLE_NAME, columns, CN_ID+" = "+id_location, null, null, null, null);
		List<Location> lloc = CreateList(c);
		return lloc.get(0);
	}

}
