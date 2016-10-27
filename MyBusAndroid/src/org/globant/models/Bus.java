package org.globant.models;

public class Bus
{

	protected String busLineName;
	protected String busLineColor;
	protected int idBusLine;

	public Bus()
	{
		super();
	}


	public Bus(int idBusLine, String busLineName, String busLineColor)
	{
		super();
		this.busLineName = busLineName;
		this.busLineColor = busLineColor;
		this.idBusLine = idBusLine;
	}


	public int getId()
	{
		return idBusLine;
	}

	public void setId(int id)
	{
		idBusLine = id;
	}

	public String getName()
	{
		return busLineName;
	}

	public void setName(String name)
	{
		busLineName = name;
	}

	public String getColor()
	{
		return busLineColor;
	}

	public void setColor(String color)
	{
		busLineColor = color;
	}

}