package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class History {

	public List<Double> bestList;
	public List<Double> meanList;
	public List<Double> worstList;
	
	private final String filePath;
	
	public History() {
		this(null);
			
	}
	
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
	
	public void printResults(){
		for(int i=0; i < bestList.size(); i++){
			print(i);
		}
	}
	
	public void printShort(){
		print(0);
		print(bestList.size()-1);
	}
	
	private void print(int i){
		System.out.println(i + " : " + bestList.get(i) + " | " + meanList.get(i) + " | " + worstList.get(i));
	}
	
	public void writeFile(){
		if(filePath == null) throw new UnsupportedOperationException("Only histories with a filePath can be written to file");

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

	public int size() {
		return bestList.size();
	}
}
