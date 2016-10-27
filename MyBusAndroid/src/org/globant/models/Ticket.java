package org.globant.models;

public class Ticket
{
	private int _id;
	
	private double price;
	
	private String description;

	public int getId()
	{
		return _id;
	}

	public void setId(int _id)
	{
		this._id = _id;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
