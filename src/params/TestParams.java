package params;

public class TestParams extends Params{

	public TestParams() {
		popSize = 1000;
		maxGenerations = 5000;
		elitists = 0.05d;
		crossover = 0.95d;
		mutation = 0.05d;
		stop = 0.95d;
		detectLoops = false;
	}
}
