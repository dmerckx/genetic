package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class History {

	private List<Double> bestList;
	private List<Double> meanList;
	private List<Double> worstList;
	
	private final String filePath;
	
	
	public History(String filePath) {
		this.filePath = filePath;
		
		this.bestList = new ArrayList<Double>();
		this.meanList = new ArrayList<Double>();
		this.worstList = new ArrayList<Double>();
			
	}
	
	public void write(double best, double mean, double worst) {
		bestList.add(best);
		meanList.add(mean);
		worstList.add(worst);
	}
	
	public String fileString(double val) {
		return val + " ";
	}
	
	public void printResults(double factor){
		for(int i=0; i < bestList.size(); i++){
			System.out.println(i + " : " + bestList.get(i) * factor + " | " + meanList.get(i) * factor + " | " + worstList.get(i) * factor);
		}
	}
	
	public void writeFile(){

		try {
			FileWriter writer = new FileWriter(new File(filePath));
			for(int i = 0; i < bestList.size(); i++){
				writer.write(fileString(bestList.get(i)));
				writer.write(fileString(meanList.get(i)));
				writer.write(fileString(worstList.get(i)) + "\r\n");
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
