package org.globant.mybus.responses;

import java.util.List;

import org.globant.models.Station;

import com.google.gson.annotations.SerializedName;

public class StationResponse extends NexusResponse
{
	@SerializedName("Results")
	public List<Station> results;
}
