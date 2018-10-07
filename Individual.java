import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;


public class Individual{

	//VARIABLES
	public double[] genotype;
	private int dimensions;
	public double fitnessVal;
	private final double LOWERBOUND = -5;
	private final double UPPERBOUND = 5;

	//particle
	public double[] velocity;
	public double[] pBest;
	public double fitnessValBest;
	public Random rnd;


	//CONSTRUCTOR
	public Individual(double[] genotype) {
		this.genotype = genotype;
		this.dimensions = genotype.length;
		this.fitnessVal = 0.;

		//particle
		rnd = new Random();
		this.velocity = new double[10];	
		this.pBest = new double[10]; //10?
		fitnessValBest = -1;
		fitnessVal = -1;
		double rangeMin = -1;
		double rangeMax = 1;

		for(int i = 0; i < 10; i++) {
			this.velocity[i] = rangeMin + (rangeMax - rangeMin) * rnd.nextDouble();
		}

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

	//particle METHODS
	// public void evaluate(ContestEvaluation evaluation_) {
	// 	this.fitnessVal = (double) evaluation_.evaluate(this.genotype);
	
	// 	if(this.fitnessVal > this.fitnessValBest || this.fitnessValBest==-1) { // >>>>>>
	// 		this.pBest = this.genotype;
	// 		this.fitnessValBest = this.fitnessVal;
	// 	}
	// }

	public void checkBestFitness() {
		if(this.fitnessVal > this.fitnessValBest || this.fitnessValBest==-1) { // >>>>>>
			this.pBest = this.genotype;
			this.fitnessValBest = this.fitnessVal;
		}
	}

	public void updateVelocity(double[] pos_best_g) {
		//W SHOULD BE BETWEEN 0.2 AND 0.9, ELSE BAD RESULTS
		double w = 0.4; //Double.parseDouble(System.getProperty("var2"));       // constant inertia weight (how much to weigh the previous velocity)	(0.4 for random (fitness 4))
		double c1= 1.5; //Double.parseDouble(System.getProperty("var3"));        // cognative constant <--mYB 1?		(1.5 for random (fitness 4))
		double c2= 1.5; //Double.parseDouble(System.getProperty("var4"));        // social constant					(1.5 for random (fitness 4))

		for(int i=0; i < 10; i++) {
			double r1 = Math.random();
			double r2 = Math.random();

			double velCognitive = c1*r1*(this.pBest[i]-this.genotype[i]);
			double velSocial = c2*r2*(pos_best_g[i]-this.genotype[i]);
			this.velocity[i]  = w*this.velocity[i]+velCognitive+velSocial;
		}

	}


	public void updateGenotype() {

		for(int i =0; i < 10; i++) {
			this.genotype[i] = this.genotype[i]+this.velocity[i];

			if(this.genotype[i]>5) {
				this.genotype[i] = 5;
			}

			if(this.genotype[i] < -5) {
				this.genotype[i] = -5;
			}
		}
	}

}