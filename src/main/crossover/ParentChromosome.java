package main.crossover;

import representations.Chromosome;

public class ParentChromosome<R extends Chromosome> {

	private R leftToRight;
	private R rightToLeft;
	
	public ParentChromosome(R leftToRight, R rightToLeft) {
		this.leftToRight = leftToRight;
		this.rightToLeft = rightToLeft;
	}
	
	public R getChromOfDirection(Direction dir) {
		switch(dir) {
			case LEFT_TO_RIGHT:
				return leftToRight;
			case RIGHT_TO_LEFT:
				return rightToLeft;
			default:
				return null;
		}
	}
	
	public String toString() {
		return "normal: " + leftToRight + " inverse: " + rightToLeft;
	}
	
}
