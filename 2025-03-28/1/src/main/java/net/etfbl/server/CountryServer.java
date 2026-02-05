package net.etfbl.server;

import java.util.List;
import java.util.Scanner;

import net.etfbl.models.Country;
import net.etfbl.server.country.CountryService;

public class CountryServer {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		CountryService service = new CountryService();
		service.printAllCountries();
		
		String region = "";
		boolean invalidRegion = true;
		System.out.println("\nRegions: ");
		service.getAllRegions().forEach(System.out::println);
		while(invalidRegion) {
			System.out.print("Enter region: ");
			region = scan.nextLine();
			if(service.isRegionValid(region))
				invalidRegion = false;
		}
		
		List<Country> countries = service.getAllCountriesByRegion(region);
		CountryServerThread thread = new CountryServerThread(countries);
		thread.start();
		
		String cmd = "";
		while(!"STOP".equalsIgnoreCase(cmd)) {
			System.out.print("Command: ");
			cmd = scan.nextLine();
		}
		thread.stopRunning();
		scan.close();
	}
}
