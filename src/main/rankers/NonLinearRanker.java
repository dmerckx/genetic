package main.rankers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.RankedChrom;
import params.Params;
import representations.Chromosome;
import util.PolynomialRootFinder;

public class NonLinearRanker<R extends Chromosome> implements Ranker<R> {

	private int selectivePressure;
	private Params params;
	private double X;
	private double[] fit;
	private HashMap<Integer,Double> selectivePressures;
	
	public NonLinearRanker(Params params) {
		this(2, params);
	}
	
	public NonLinearRanker(int selectivePressure, Params params) {
		this.selectivePressure = selectivePressure;
		this.params = params;
		selectivePressures = new HashMap<Integer,Double>();
		selectivePressures.put(1,1d);
//		selectivePressures.add(1.0333); //2
		selectivePressures.put(3,1.0602); //3
//		selectivePressures.add(1.0854); //4
//		selectivePressures.put(5,1.1105); //5
		selectivePressures.put(6,1.1361); //6
//		selectivePressures.put(7,1.1627); //7
//		selectivePressures.put(9,1.2195); //9
		selectivePressures.put(11, 1.2820); //11
//		selectivePressures.put(13, 1.3514); //13
//		selectivePressures.put(15, 1.4286); //15
		selectivePressures.put(16,1.4706); //16
//		selectivePressures.put(17, 1.5152); //17
//		selectivePressures.put(19, 1.6129); // 19
		selectivePressures.put(21,  1.7241); //21
//		selectivePressures.put(23,  1.8519);//23
//		selectivePressures.put(25,  2.0d);   //25
		selectivePressures.put(26, 2.0833);
//		selectivePressures.put(27,  2.1739);   //27
//		selectivePressures.put(29,  2.3810);   
		
		X = selectivePressures.get(selectivePressure);
		fit = new double[params.popSize];
		for (int i = 0; i < params.popSize; i++) {
			fit[params.popSize-i-1] = Math.pow(X, i);
		}
		double sum = makeSum(fit);
		for (int i = 0; i < params.popSize; i++) {
			fit[i] = (fit[i]/sum)*params.popSize;
//			System.out.println(fit[i]);
		}
	}
	
	public double makeSum(double[] param) {
		double sum = 0;
		for (int i = 0; i < param.length; i++) {
			sum += param[i];
		}
		return sum;
	}
	
	@Override
	public List<RankedChrom<R>> rank(List<R> unrankedPop) {
		List<RankedChrom<R>> ranked = new ArrayList<RankedChrom<R>>();
		int size = unrankedPop.size();
		for(int i = 0; i < size; i++){
			double fit = this.fit[i];
			ranked.add(new RankedChrom<R>(fit, unrankedPop.get(i)));
		}
		
		return ranked;
	}
	
	private double computeX() {
		double x = 0d;
		for (double iterable_element : PolynomialRootFinder.findRoots(initCoefficients())) {
			if(/*iterable_element.isReal() &&*/ iterable_element > x)
				x = iterable_element;
		}
		return x;
	}

	private double[] initCoefficients() {
		double[] coefficients = new double[params.popSize];
		coefficients[0] = selectivePressure-params.popSize;
		for (int i = 1; i < coefficients.length; i++) {
				coefficients[i] = selectivePressure;
		}
		return coefficients;
	}

	private double sum(double X) {
		double sum = 0;
		for (int i = 1; i <= params.popSize; i++) {
			sum += Math.pow(X, i-1);
		}
		return sum;
	}
	
}
