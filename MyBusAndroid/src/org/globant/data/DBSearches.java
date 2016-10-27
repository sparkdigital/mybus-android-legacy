package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Search;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

// TODO Task for sprint #2: code local storage of route description.

public class DBSearches extends DBMaster {

	// Table name
	public static final String TABLE_NAME = "Searches";

	public static final String CN_ID = "_id";
	public static final String CN_ID_ORIGIN = "id_origin";
	public static final String CN_ID_DESTINY = "id_destiny";
	public static final String CN_ROUTE_DESCRIPTION = "route_description";
	public static final String CN_SEARCH_COUNT = "search_count";
	public static final String CN_LAST_SEARCH = "last_search";

	public static String create_table() {
		String query = "CREATE TABLE "+TABLE_NAME+" ( "+
									CN_ID+" INTEGER PRIMARY KEY,"+
									CN_ID_ORIGIN+" INTEGER NOT NULL CHECK ("+CN_ID_ORIGIN+" <> "+CN_ID_DESTINY+"),"+
									CN_ID_DESTINY+" INTEGER NOT NULL CHECK ("+CN_ID_ORIGIN+" <> "+CN_ID_DESTINY+"),"+
									CN_ROUTE_DESCRIPTION+" TEXT, "+
									"FOREIGN KEY ("+CN_ID_ORIGIN+") REFERENCES "+DBLocations.TABLE_NAME+" ("+DBLocations.CN_ID+")  ON UPDATE CASCADE ON DELETE CASCADE,"+
									"FOREIGN KEY ("+CN_ID_DESTINY+") REFERENCES "+DBLocations.TABLE_NAME+" ("+DBLocations.CN_ID+")  ON UPDATE CASCADE ON DELETE CASCADE "+
									")";
		return query;
	}

	private String[] columns = new String[] { CN_ID, CN_ID_ORIGIN, CN_ID_DESTINY, CN_ROUTE_DESCRIPTION };

	public DBSearches(Context context){
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(int id_origin,int id_destiny,int search_count,String last_search) {
		ContentValues cont = new ContentValues();
		cont.put(CN_ID_ORIGIN, id_origin);
		cont.put(CN_ID_DESTINY, id_destiny);
		return cont;
	}

	public void Insert(int id_origin,int id_destiny,int search_count,String last_search) {
		db.insert(TABLE_NAME, null, ValuesContainer(id_origin,id_destiny,search_count,last_search));
	}

	@Override
	public void Delete(int id) {
		// TODO Implement deletion procedure
	}

	public List<Search> CreateList(Cursor c) {
		List<Search> List_search = new ArrayList<Search>();
		if (c.moveToFirst()) {
			do {
				int id = c.getColumnIndex(CN_ID);
				int id_origin = c.getColumnIndex(CN_ID_ORIGIN);
				int id_destiny = c.getColumnIndex(CN_ID_DESTINY);
				Search srch = new Search(c.getInt(id), c.getInt(id_origin), c.getInt(id_destiny));
				List_search.add(srch);
			} while (c.moveToNext());
		}
		c.close();
		return List_search;
	}

	public List<Search> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}

}
