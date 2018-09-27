public class Individual{

	//VARIABLES
	private double[] genotype;
	private int dimensions;
	private double fitnessVal;
	private final double LOWERBOUND = -5;
	private final double UPPERBOUND = 5;


	//CONSTRUCTOR
	public Individual(double[] genotype) {
		this.genotype = genotype;
		this.dimensions = genotype.length;
		this.fitnessVal = 0.;
	}
	
	//METHODS
	public double[] getGenotype() {
		return this.genotype;
	}

	public int getDimensionsCount() {
		return this.dimensions;
	}
	
	public double getAlleleAtDim(int dim) {
		return this.genotype[dim];
	}
	
	public void setAlleleAtDim(int dim, double newAlleleValue) {
		genotype[dim] = newAlleleValue;
	}

	public double getFitness() {
		return this.fitnessVal;
	}

	public void setFitness(double fitness) {
		this.fitnessVal = fitness;
	}
}