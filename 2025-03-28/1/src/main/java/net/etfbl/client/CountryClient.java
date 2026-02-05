package net.etfbl.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.etfbl.models.Country;
import net.etfbl.models.dto.ConnectionParameters;

public class CountryClient {
	private static final String PARAMTERES_URL = "http://localhost:8080/2025-03-28_1/api/connections/parameters";

	public static void main(String[] args) {
		ConnectionParameters params = getConnectionParams();
		try(MulticastSocket socket = new MulticastSocket(params.getPort())) {
			InetAddress address = InetAddress.getByName(params.getAddress());
			socket.joinGroup(address);
			Gson gson = new Gson();
			byte[] buffer = new byte[8 * 1024];
			while(true) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String json = new String(packet.getData(), 0, packet.getLength());
				Type type = new TypeToken<List<Country>>() {}.getType();
				List<Country> countries = gson.fromJson(json, type);
				countries.forEach(System.out::println);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ConnectionParameters getConnectionParams() {
		try(InputStreamReader in = new InputStreamReader(new URL(PARAMTERES_URL).openStream())) {
			return new Gson().fromJson(in, ConnectionParameters.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
