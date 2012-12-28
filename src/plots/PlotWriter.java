package plots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PlotWriter {

	public static void writeList(String filePath, List<String> list) {
		try {
			FileWriter writer = new FileWriter(new File(filePath));
			for(int i = 0; i < list.size(); i++){
				writer.write(list.get(i));
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void writeLists(String filePath, List<Double> bestList, List<Double> meanList, List<Double> worstList) {
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
	
	public static String fileString(double val) {
		return val + " ";
	}
	
}
