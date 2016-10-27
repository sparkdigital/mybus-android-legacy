package org.globant.models;

public class Search {
	
	private int Id,Id_origin,Id_destiny;
	
	public Search(int id,int id_origin,int id_destiny){
		Id = id;
		Id_origin = id_origin;
		Id_destiny = id_destiny;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getId_origin() {
		return Id_origin;
	}

	public void setId_origin(int id_origin) {
		Id_origin = id_origin;
	}

	public int getId_destiny() {
		return Id_destiny;
	}

	public void setId_destiny(int id_destiny) {
		Id_destiny = id_destiny;
	}
	
}
