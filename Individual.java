public class Individual{

	//VARIABLES
	public double[] x;
	public double fitnessVal;
	private final double LOWERBOUND = -5;
	private final double UPPERBOUND = 5;


	//CONSTRUCTOR
	public Individual(double[] x) {
		this.x = x;
		this.fitnessVal = 0.;
	}


	//METHODS
	public double getFitness() {
		return this.fitnessVal;
	}

	public void setFitness(double fitness) {
		this.fitnessVal = fitness;
	}
}
