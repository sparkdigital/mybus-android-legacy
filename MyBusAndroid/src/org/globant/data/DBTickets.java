package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Ticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBTickets extends DBMaster
{
	public static final String TABLE_NAME = "TicketPrices",

	CN_ID = "_id", CN_DESCRIPTION = "description", CN_PRICE = "price";

	public static String create_table()
	{
		String query = "CREATE TABLE " + TABLE_NAME + " ( " + CN_ID
				+ " INTEGER PRIMARY KEY," + CN_PRICE + " REAL NOT NULL,"
//				+ CN_DESCRIPTION + " TEXT NOT NULL" + ")";
				+ CN_DESCRIPTION + " TEXT NOT NULL,"
				+ "UNIQUE(" + CN_DESCRIPTION + ")" + ")";

		return query;
	}
	
	private String[] columns = new String[] { CN_ID, CN_PRICE, CN_DESCRIPTION };

	private Context context;

	public DBTickets(Context context)
	{
		this.context = context;
		helper = new DBHelper(context);
	}

	private ContentValues ValuesContainer(double price, String description)
	{
		ContentValues cont = new ContentValues();
		cont.put(CN_DESCRIPTION, description);
		cont.put(CN_PRICE, price);
		return cont;
	}

	public void Insert(double price, String description)
	{
		db.insert(TABLE_NAME, null, ValuesContainer(price, description));
	}
	
	public void insertNoDuplicates(double price, String description)
	{
		db.rawQuery("INSERT OR IGNORE INTO " + TABLE_NAME + "(" + CN_PRICE + "," + CN_DESCRIPTION + ") VALUES(" + price + "," + description +")", null);
	}

	@Override
	void Delete(int id)
	{
	}

	public List<Ticket> CreateList(Cursor c)
	{
		List<Ticket> listTickets = new ArrayList<Ticket>();

		if (c.moveToFirst())
		{
			do
			{
				int id = c.getInt(c.getColumnIndex(CN_ID));
				double price = c.getDouble(c.getColumnIndex(CN_PRICE));
				String description = c.getString(c.getColumnIndex((CN_DESCRIPTION)));

				Ticket t = new Ticket();
				
				t.setId(id);
				t.setPrice(price);
				t.setDescription(description);
				
				listTickets.add(t);
			}
			while (c.moveToNext());
		}
		c.close();
		return listTickets;
	}

	public List<Ticket> getList() {
		Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
		return CreateList(c);
	}
	
	public Ticket getTicket(int id)
	{
		Cursor c = db.query(TABLE_NAME, columns, CN_ID+" = "+id, null, null, null, null);
		List<Ticket> l = CreateList(c);
		return l.get(0);
	}
	
}
