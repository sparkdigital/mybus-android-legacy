package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.PassagePrice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBPassagePrices extends DBMaster {

	// Table name
	public static final String TABLE_NAME = "PassagePrices";

	public static final String CN_ID = "_id";
	public static final String CN_DESCRIPTION = "description";
	public static final String CN_PRICE = "price";

	public static String create_table() {
		String query = "CREATE TABLE "+TABLE_NAME+" ( "+
								CN_ID+" INTEGER PRIMARY KEY,"+
								CN_DESCRIPTION+" TEXT NOT NULL,"+
								CN_PRICE+" REAL NOT NULL"+
								")";
		return query;
	}

	private String[] columns = new String[] { CN_ID, CN_DESCRIPTION, CN_PRICE };

	public DBPassagePrices(Context context) {
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(String description,double price) {
		ContentValues cont = new ContentValues();
		cont.put(CN_DESCRIPTION, description);
		cont.put(CN_PRICE, price);
		return cont;
	}

	public void Insert(String description,double price) {
		db.insert(TABLE_NAME, null, ValuesContainer(description,price));
	}
	
	@Override
	public void Delete(int id) {
		// TODO Implement deletion procedure
	}

	public List<PassagePrice> CreateList(Cursor c) {
		List<PassagePrice> List_pass = new ArrayList<PassagePrice>();
		if (c.moveToFirst()) {
			do {
				int id = c.getColumnIndex(CN_ID);
				int description = c.getColumnIndex(CN_DESCRIPTION);
				int price = c.getColumnIndex(CN_PRICE);
				PassagePrice pp = new PassagePrice(c.getInt(id), c.getString(description), c.getDouble(price));
				List_pass.add(pp);
			} while (c.moveToNext());
		}
		c.close();
		return List_pass;
	}

	public List<PassagePrice> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}
	
}
