package hotels;

import java.io.Serializable;

public class Hotel implements Serializable {
	private static final long serialVersionUID = 2698174535792002184L;
	
	private String name;
	private String city;
	private String description;
	private Integer stars;
	
	public Hotel() {
		super();
	}

	public Hotel(String name, String city, String description, Integer stars) {
		super();
		this.name = name;
		this.city = city;
		this.description = description;
		this.stars = stars;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return "Hotel [name=" + name + ", city=" + city + ", description=" + description + ", stars=" + stars + "]";
	}
}
