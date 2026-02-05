package infohotel;

import java.util.LinkedList;
import java.util.List;

import hotels.Hotel;

public class HotelDAO {
	private static List<Hotel> hotels;
	
	public HotelDAO() {
		if(hotels == null) {
			hotels = new LinkedList<Hotel>();
			hotels.add(new Hotel("Hotel 1", "New York", "Description of Hotel 1", 2));
			hotels.add(new Hotel("Hotel 2", "Miami", "Description of Hotel 2", 3));
			hotels.add(new Hotel("Lux Hotel", "New York", "Description of Hotel 3", 4));
			hotels.add(new Hotel("Star Hotel", "Paris", "Description of Hotel 4", 3));
			hotels.add(new Hotel("Hotel 5", "London", "Description of Hotel 5", 5));
			hotels.add(new Hotel("Kastel Hotel", "Banja Luka", "Description of Hotel 6", 4));
			hotels.add(new Hotel("Borik Hotel", "Banja Luka", "Description of Hotel 7", 2));
		}
	}
	
	public List<Hotel> findAll() {
		return new LinkedList<Hotel>(hotels);
	}
}
