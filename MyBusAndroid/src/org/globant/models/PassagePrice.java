package org.globant.models;

public class PassagePrice {

	private int Id;
	private String Description;
	private double Price;
	
	public PassagePrice(int id,String description,double price){
		Id = id;
		Description = description;
		Price = price;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}
	
}
