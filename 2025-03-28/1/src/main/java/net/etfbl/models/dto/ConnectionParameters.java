package net.etfbl.models.dto;

public class ConnectionParameters {
	private String address;
	private Integer port;
	
	public ConnectionParameters() {
		super();
	}

	public ConnectionParameters(String address, Integer port) {
		super();
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
