package org.globant.data;

import java.util.ArrayList;
import java.util.List;

import org.globant.models.Location_type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBLocation_types extends DBMaster {

		//Table name
		public static final String TABLE_NAME = "Location_types";

		public static final String CN_ID = "_id"; 
		public static final String CN_NAME = "name"; 

		public static String create_table(){
			String query = "CREATE TABLE "+TABLE_NAME+"("+
														CN_ID+" INTEGER PRIMARY KEY,"+
														CN_NAME+" TEXT NOT NULL);";
			return query;
		}

		private String[] columns = new String[]{CN_ID,CN_NAME};

		public DBLocation_types(Context context){
			helper = new DBHelper(context);
		}

		private ContentValues ValuesContainer(String name){
			ContentValues cont = new ContentValues();
			cont.put(CN_NAME, name);
			return cont;
		}

		public void Insert(String name){   
			db.insert(TABLE_NAME, null, ValuesContainer(name));
		}

		@Override
		public void Delete(int id){
			//TODO Implement deletion procedure
		}

		public List<Location_type> CreateList(Cursor c){
			List<Location_type> List_LT = new ArrayList<Location_type>();
			if(c.moveToFirst()){
				do{
					int id = c.getColumnIndex(CN_ID);
					int name = c.getColumnIndex(CN_NAME);
					Location_type loc_type =new Location_type(c.getInt(id), c.getString(name));
					List_LT.add(loc_type);
				}while(c.moveToNext());
			}
			c.close();
			return List_LT;
		}

		public List<Location_type> getList(){
			Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
			return CreateList(c);
		}

}
