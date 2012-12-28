package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.Problem;

/**
 * Generates a Problem instance from a given file.
 * @author Kristof
 *
 */
public class ProblemGenerator {
	
	public static Problem generate(String filePath) {

		List<Point> cities = new ArrayList<Point>();

		Problem problem = new Problem(cities);
		
		try{

			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				cities.add(extractPoint(strLine));
			}
			in.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}

		return problem;
	}

	private static Point extractPoint(String strLine) {
		strLine = strLine.trim();
		String[] points = strLine.split(" +");
		return new Point(Double.valueOf(points[0]), Double.valueOf(points[1]));
		
	}

}
