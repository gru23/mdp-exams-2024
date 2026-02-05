package net.etfbl.server.country;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import net.etfbl.models.Country;
import net.etfbl.models.dto.Currency;

public class CountryService {
	private final CountryDAO countryDAO;
	
	public CountryService() {
		this.countryDAO = new CountryDAO();
		
	}
	
	public List<String> getAllRegions() {
		return countryDAO.getRegions();
	}
	
	public void printAllCountries() {
		countryDAO.getCountries().values().forEach(c -> {
			String countryName = c.getName().getCommon();
			StringBuilder sb = new StringBuilder();
			Collection<Currency> currencyCollection = c.getCurrenices().values();
			currencyCollection.forEach(cc -> sb.append(cc.getName() + " [" + cc.getSymbol() + "] "));
			System.out.println(countryName + " " + sb.toString());
		});
	}
	
	public boolean isRegionValid(String region) {
		return countryDAO.getRegions().contains(region);
	}
	
	public List<Country> getAllCountriesByRegion(String region) {
		return countryDAO.getCountries().values()
				.stream()
				.filter(c -> region.equalsIgnoreCase(c.getRegion()))
				.collect(Collectors.toList());
	}
}
