package org.eclipselabs.commons.rcp.snippets.snippet01tableviewerbuilder;

import java.util.Date;

public class City {

	private final int id;
	private String name;
	private Date foundingDate;
	private final CityStats stats;
	private String neighborCity;

	public City(int id, String name, Date foundingYear, CityStats stats, String neighborCity) {
		super();
		this.id = id;
		this.name = name;
		this.foundingDate = foundingYear;
		this.stats = stats;
		this.neighborCity = neighborCity;
	}

	public int getId() {
		return id;
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