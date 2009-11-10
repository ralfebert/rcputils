package de.ralfebert.rcpsnippets.snippet01tableproperties;

import java.util.Date;

public class City {

	private String name;
	private Date foundingDate;
	private final CityStats stats;
	private String neighborCity;

	public City(String name, Date foundingYear, CityStats stats, String neighborCity) {
		super();
		this.name = name;
		this.foundingDate = foundingYear;
		this.stats = stats;
		this.neighborCity = neighborCity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(Date foundingYear) {
		this.foundingDate = foundingYear;
	}

	public CityStats getStats() {
		return stats;
	}

	public String getNeighborCity() {
		return neighborCity;
	}

	public void setNeighborCity(String neighborCity) {
		this.neighborCity = neighborCity;
	}

}