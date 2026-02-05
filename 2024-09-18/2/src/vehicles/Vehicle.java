package vehicles;

import java.io.Serializable;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = 4851641888895174186L;
	
	private String id;
	private String manufacturer;
	private String model;
	
	public Vehicle() {
		super();
	}

	public Vehicle(String id, String manufacturer, String model) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + "]";
	}
}
