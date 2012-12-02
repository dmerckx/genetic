package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class History {

	
	private FileWriter writer;
	
	private final String filePath;
	
	private boolean firstCall = true;
	
	public History(String filePath) {
		this.filePath = filePath;
	}
	
	public void write(double best, double mean, double worst) {
		
		try {
			writer = new FileWriter(new File(filePath), !firstCall);
			writer.write(fileString(best));
			writer.write(fileString(mean));
			writer.write(fileString(worst) + "\r\n");
			writer.close();
			firstCall = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String fileString(double val) {
		return val + " ";
	}
	
}
