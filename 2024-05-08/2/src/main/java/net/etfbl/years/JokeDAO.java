package net.etfbl.years;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JokeDAO {
	private static final String FILE_NAME = "jokes.txt";
	
	public static final String FULL_PATH = System.getProperty("user.home") 
			+ File.separator + "jokes" 
			+ File.separator + FILE_NAME;
	
	public void writeJoke(JokeEntity joke) {
		File folder = new File(System.getProperty("user.home") + File.separator + "jokes");
		if(!folder.exists())
			folder.mkdirs();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FULL_PATH, true))) {
			bw.write(joke.getId());
			bw.newLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> readJokes() {
		try(BufferedReader br = new BufferedReader(new FileReader(FULL_PATH))) {
			List<String> result = new LinkedList<String>();
			String line = "";
			while((line = br.readLine()) != null)
				result.add(line);
			return result;
		} catch(IOException e) {
//			e.printStackTrace();
			return new LinkedList<String>();
		}
	}

}
