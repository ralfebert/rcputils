package de.ralfebert.rcpsnippets.snippet01tableproperties;


public class CityStats {

	private int population;
	private double areaKm2;

	public CityStats(int population, double areaKm2) {
		super();
		this.population = population;
		this.areaKm2 = areaKm2;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getAreaKm2() {
		return areaKm2;
	}

	public void setAreaKm2(double areaKm2) {
		this.areaKm2 = areaKm2;
	}

}
