package org.globant.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Station implements Parcelable
{
	@SerializedName("Id")
	private int id;

	@SerializedName("Name")
	private String name;

	@SerializedName("Adress")
	private String address;

	@SerializedName("Lat")
	private double lat;

	@SerializedName("Lng")
	private double lng;

	@SerializedName("OpenTime")
	private String availability;

	@SerializedName("Distance")
	private double distance;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public double getLat()
	{
		return lat;
	}

	public void setLat(double lat)
	{
		this.lat = lat;
	}

	public double getLng()
	{
		return lng;
	}

	public void setLng(double lng)
	{
		this.lng = lng;
	}

	public String getAvailability()
	{
		return availability;
	}

	public void setAvailability(String availability)
	{
		this.availability = availability;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public Station(Parcel in)
	{
		String[] data = new String[7];
		
		in.readStringArray(data);
		
		this.id = Integer.parseInt(data[0]);
		this.name = data[1];
		this.address = data[2];
		this.lat = Double.parseDouble(data[3]);
		this.lng = Double.parseDouble(data[4]);
		this.availability = data[5];
		this.distance = Double.parseDouble(data[6]);
	}
	
	@Override
	public int describeContents()
	{
		// TODO "Figure out wot da zog is dis fing for."
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeStringArray(new String[] { 
				Integer.toString(this.id), 
				this.name, 
				this.address, 
				Double.toString(this.lat), 
				Double.toString(this.lng), 
				this.availability, 
				Double.toString(this.distance) } );
	}

	static public final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>()
	{

		@Override
		public Station createFromParcel(Parcel source)
		{
			return new Station(source);
		}

		@Override
		public Station[] newArray(int size)
		{
			return new Station[size];
		}
	};
	
	public LatLng getLatLng(){
		return new LatLng(getLat(), getLng());
	}
}