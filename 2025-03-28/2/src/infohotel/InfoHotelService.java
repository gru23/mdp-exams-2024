package infohotel;

import java.util.List;
import java.util.stream.Collectors;

import hotels.Hotel;

public class InfoHotelService {
	private final HotelDAO hotelDAO;
	
	public InfoHotelService() {
		this.hotelDAO = new HotelDAO();
	}
	
	public List<Hotel> getAll() {
		return hotelDAO.findAll();
	}
	
	public List<Hotel> getAllByCategory(Integer category) {
		return hotelDAO.findAll()
				.stream()
				.filter(h -> category.equals(h.getStars()))
				.collect(Collectors.toList());
	}
	
	public List<Hotel> getAllByCity(String city) {
		return hotelDAO
				.findAll()
				.stream()
				.filter(h -> city.equalsIgnoreCase(h.getCity()))
				.collect(Collectors.toList());
	}
}
