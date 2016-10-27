package org.globant.models;

public class Location {

	private int Id,isCritical,isFavorite,Search_count;
	private double Latitude,Longitude;
	private String Street,Observations,Last_search;
	
	public Location () {}
	
	public Location(int id,double latitude,double longitude,String street,int search_count,String last_search,int iscritical,int isfavorite,String observations){
		Id = id;
		isCritical = iscritical;
		isFavorite = isfavorite;
		Latitude = latitude;
		Longitude = longitude;
		Street = street;
//		Number = number;
		Search_count = search_count;
		setLast_search(last_search);
		Observations = observations;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getIsCritical() {
		return isCritical;
	}

	public void setIsCritical(int isCritical) {
		this.isCritical = isCritical;
	}

	public int getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getObservations() {
		return Observations;
	}

	public void setObservations(String observations) {
		Observations = observations;
	}

	public int getSearch_count()
	{
		return Search_count;
	}

	public void setSearch_count(int search_count)
	{
		Search_count = search_count;
	}

	public String getLast_search()
	{
		return Last_search;
	}

	public void setLast_search(String last_search)
	{
		Last_search = last_search;
	}
}
