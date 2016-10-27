package org.globant.models;

public class Setting {

	private int Id,showFavorites,showStations;
	
	public Setting(int id,int showfavorites,int showstations){
		Id = id;
		showFavorites = showfavorites;
		showStations = showstations;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getShowFavorites() {
		return showFavorites;
	}

	public void setShowFavorites(int showFavorites) {
		this.showFavorites = showFavorites;
	}

	public int getShowStations() {
		return showStations;
	}

	public void setShowStations(int showStations) {
		this.showStations = showStations;
	}
	
	
}
