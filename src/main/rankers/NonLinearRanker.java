package main.rankers;

import java.util.ArrayList;
import java.util.List;

import org.ejml.data.Complex64F;

import params.Params;

import main.RankedChrom;
import representations.Chromosome;
import util.PolynomialRootFinder;

public class NonLinearRanker<R extends Chromosome> implements Ranker<R> {

	private int selectivePressure;
	private Params params;
	
	public NonLinearRanker(Params params) {
		this(2, params);
	}
	
	public NonLinearRanker(int selectivePressure, Params params) {
		this.selectivePressure = selectivePressure;
		this.params = params;
	}
	
	@Override
	public List<RankedChrom<R>> rank(List<R> unrankedPop) {
		List<RankedChrom<R>> ranked = new ArrayList<RankedChrom<R>>();
		
		int size = unrankedPop.size();
		for(int i = 0; i < size; i++){
			//least fit individual has Pos=1
			//the fittest individual Pos=Nind
			//http://www.pohlheim.com/Papers/mpga_gal95/gal2_3.html#Non-linear Ranking
			int pos = size-i;
			double X = computeX();
			double fit = params.popSize * Math.pow(X, pos-1)/sum(X);
			ranked.add(new RankedChrom<R>(fit, unrankedPop.get(i)));
		}
		
		return ranked;
	}
	
	private double computeX() {
		return PolynomialRootFinder.findRoots(initCoefficients())[0];
	}

	private double[] initCoefficients() {
		double[] coefficients = new double[params.popSize];
		for (int i = 0; i < coefficients.length; i++) {
			if(i == 0)
				coefficients[i] = selectivePressure-1;
			else
				coefficients[i] = selectivePressure;
		}
		return coefficients;
	}

	private double sum(double X) {
		double sum = 0;
		for (int i = 1; i <= params.popSize; i++) {
			sum += Math.pow(X, i);
		}
		return sum;
	}
	
}
