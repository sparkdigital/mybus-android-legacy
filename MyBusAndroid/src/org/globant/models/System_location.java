package org.globant.models;

public class System_location {

	private int Id,Id_type;
	private Location location;
	private String name, Image_url;
	
	public System_location(int id,Location loc,int id_type,String name,String img_url){
		this.Id = id;
		this.location = loc;
		this.Id_type = id_type;
		this.Image_url = img_url;
		this.name = name;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getId_type() {
		return Id_type;
	}

	public void setId_type(int id_type) {
		Id_type = id_type;
	}

	public String getImage_url() {
		return Image_url;
	}

	public void setImage_url(String image_url) {
		Image_url = image_url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
}
