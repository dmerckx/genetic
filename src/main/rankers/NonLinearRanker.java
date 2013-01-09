package main.rankers;

import java.util.ArrayList;
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
	
	public NonLinearRanker(Params params) {
		this(2, params);
	}
	
	public NonLinearRanker(int selectivePressure, Params params) {
		this.selectivePressure = selectivePressure;
		this.params = params;
//		X = computeX();
		X = 1.0163; //TODO hardcoded for popsize 100
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
			//least fit individual has Pos=1
			//the fittest individual Pos=Nind
			//http://www.pohlheim.com/Papers/mpga_gal95/gal2_3.html#Non-linear Ranking
			int pos = size-(i);
//			double fit = params.popSize * Math.pow(X, pos-1)/sum(X);
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
