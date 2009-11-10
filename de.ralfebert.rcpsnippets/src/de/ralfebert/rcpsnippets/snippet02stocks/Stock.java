package de.ralfebert.rcpsnippets.snippet02stocks;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

public class Stock {

	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private String name;
	private BigDecimal rate;

	public Stock(String name, BigDecimal rate) {
		super();
		this.name = name;
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		changes.firePropertyChange("name", this.name, this.name = name);
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		changes.firePropertyChange("rate", this.rate, this.rate = rate);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changes.removePropertyChangeListener(listener);
	}

}
