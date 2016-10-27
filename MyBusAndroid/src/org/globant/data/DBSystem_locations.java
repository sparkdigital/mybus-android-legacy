package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Location;
import org.globant.models.System_location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBSystem_locations extends DBMaster {

	// Table name
	public static final String TABLE_NAME = "System_locations";

	public static final String CN_ID = "_id";
	public static final String CN_ID_LOCATION = "id_location";
	public static final String CN_ID_TYPE = "id_type";
	public static final String CN_NAME = "name";
	public static final String CN_IMAGE_URL = "image_url";

	public static String create_table() {
		String query = "CREATE TABLE "+TABLE_NAME+" ( "+
										CN_ID+" INTEGER PRIMARY KEY,"+
										CN_ID_LOCATION+" INTEGER NOT NULL, "+
										CN_ID_TYPE+" INTEGER NOT NULL, "+
										CN_NAME+" TEXT," + 
										CN_IMAGE_URL+" TEXT, "+
										"FOREIGN KEY ("+CN_ID_LOCATION+") REFERENCES "+DBLocations.TABLE_NAME +"("+DBLocations.CN_ID+") ON UPDATE CASCADE ON DELETE CASCADE,"+
										"FOREIGN KEY ("+CN_ID_TYPE+") REFERENCES "+DBLocation_types.TABLE_NAME +"("+DBLocation_types.CN_ID+") ON UPDATE CASCADE ON DELETE CASCADE" +
										" )";
		return query;
	}

	private String[] columns = new String[] { CN_ID, CN_ID_LOCATION, CN_ID_TYPE, CN_IMAGE_URL };

	private Context context;

	public DBSystem_locations(Context context) {
		this.context = context;
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(int id_location,int id_type,String name,String img_url) {
		ContentValues cont = new ContentValues();
		cont.put(CN_ID_LOCATION, id_location);
		cont.put(CN_ID_TYPE, id_type);
		cont.put(CN_NAME, name);
		cont.put(CN_IMAGE_URL, img_url);
		return cont;
	}

	public void Insert(int id_location,int id_type,String name,String img_url) {
		db.insert(TABLE_NAME, null, ValuesContainer(id_location,id_type,name,img_url));
	}

	@Override
	public void Delete(int id) {
		// TODO Implement deletion procedure
	}

	public List<System_location> CreateList(Cursor c) {
		List<System_location> List_systloc = new ArrayList<System_location>();
		if (c.moveToFirst()) {
			do {
				int id = c.getColumnIndex(CN_ID);
				int id_location = c.getColumnIndex(CN_ID_LOCATION);
				int id_type = c.getColumnIndex(CN_ID_TYPE);
				int img_url = c.getColumnIndex(CN_IMAGE_URL);
				int name = c.getColumnIndex(CN_NAME);
				DBLocations dbloc = new DBLocations(context);
				dbloc.ReadableMode();
				Location loc = dbloc.GiveLocation(c.getInt(id_location));
				System_location sys_loc = new System_location(c.getInt(id), loc, c.getInt(id_type), c.getString(img_url), c.getString(name));
				List_systloc.add(sys_loc);
			} while (c.moveToNext());
		}
		c.close();
		return List_systloc;
	}

	public List<System_location> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}

}
