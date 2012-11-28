package params;

public class TestParams extends Params{

	public TestParams() {
		popSize = 100;
		maxGenerations = 100;
		elitists = 0.05d;
		crossover = 0.95d;
		mutation = 0.05d;
		stop = 0.95d;
	}
}
