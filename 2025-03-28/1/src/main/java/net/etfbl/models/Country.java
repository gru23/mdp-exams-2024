package net.etfbl.models;

import java.util.Map;

import net.etfbl.models.dto.Currency;
import net.etfbl.models.dto.Name;

public class Country {
	private Name name;
	private Map<String, Currency> currencies;
	private String region;
	
	public Country() {
		super();
	}

	public Country(Name name, Map<String, Currency> currencies, String region) {
		super();
		this.name = name;
		this.currencies = currencies;
		this.region = region;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Map<String, Currency> getCurrenices() {
		return currencies;
	}

	public void setCurrenices(Map<String, Currency> currencies) {
		this.currencies = currencies;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", currencies=" + currencies + ", region=" + region + "]";
	}
}
