package buffer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MDPBufferServer {
	private static final int TCP_PORT = 9000;
	
	private static Queue<String> messages = new ArrayDeque<String>(10);

	public static void main(String[] args) {
		MDPBufferServerReceiver receiver = new MDPBufferServerReceiver();
		receiver.start();
		
		try(ServerSocket ss = new ServerSocket(TCP_PORT)) {
			while(true) {
				Socket socket = ss.accept();
				new MDPBufferServerThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void addMessage(String message) {
		if(messages.size() == 10)
			messages.remove();
		messages.add(message);
	}
	
	public static synchronized List<String> getMessages(int amount) {
		int size = messages.size();
		amount = Math.min(amount, size);

	    List<String> result = new LinkedList<String>();
	    int skip = size - amount;

	    for (String msg : messages) {
	        if (skip > 0) {
	            skip--;
	            continue;
	        }
	        result.add(msg);
	    }
	    return result;
	}

}
