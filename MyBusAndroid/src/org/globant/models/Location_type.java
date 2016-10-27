package org.globant.models;

public class Location_type {

	private int Id;
	private String Name;
	
	public Location_type(int id,String name){
		this.Id = id;
		this.Name = name;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	
}
