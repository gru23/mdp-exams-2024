package buffer.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MDPBufferServerThread extends Thread {
	private Socket socket;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public MDPBufferServerThread(Socket socket) {
		try {
			this.socket = socket;
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			String cmd = "";
			while(true) {
				cmd = (String) in.readObject();
				switch(cmd) {
					case "TIME":
						String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
						out.writeObject(time);
						out.flush();
						break;
					case "PING":
						String ip = "IPv4: " + socket.getInetAddress().getHostAddress();
						String port = "\nPort: " + socket.getPort();
						out.writeObject(ip + port);
						out.flush();
						break;
					case "INFO":
						int amount = in.readInt();
						if(isValidAmountOfMessages(amount)) {
							List<String> messages = MDPBufferServer.getMessages(amount);
							out.writeObject("OK");
							out.flush();
							out.writeObject(messages);
							out.flush();
						} else {
							out.writeObject("NOK");
							out.flush();
							out.writeObject("Invalid parametar!");
							out.flush();
						}
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isValidAmountOfMessages(int amount) {
		return amount >= 0 && amount <= 10;
	}
}
