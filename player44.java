package ec;
import org.vu.contest.ContestSubmission;

import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.lang.Math;

public class player44 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
	private int evaluations_limit_;

	public player44()
	{
		rnd_ = new Random();
	}

	public static void main(String[] args) {
		System.out.println("Start");
		System.out.println(Double.doubleToRawLongBits(2D));
		System.out.println(Long.toBinaryString(Double.doubleToRawLongBits(20D)));
		player44 player44Object = new player44();
		player44Object.run();
	}

	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();
		// Get evaluation limit
		evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
		boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
		if(isMultimodal){
			// Do sth
		}else{
			// Do sth else
		}
	}

	public double bentCigarFunction(double phenotype[], int dimensions){
		double result = 0;
		result += Math.pow(phenotype[0],2);
		double sum = 0;
		for(int i = 1; i < dimensions ; i++) {
			sum += Math.pow(phenotype[i],2);
			//System.out.println(sum);
		}
		sum *= (Math.pow(10, 6));
		
		return result + sum;
	}

	public void run()
	{
		// Run your algorithm here

		int evals = 0;
		// init population



		int popSize = 100;
		int dimensions = 10;
		double populations[][] = new double[popSize][dimensions];

		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < dimensions; j++) {
				populations[i][j] = 5 - rnd_.nextDouble() * 10;

			}
			System.out.println(populations[i]);

		}

		double fitnessPop[] = new double[popSize];
		for (int i = 0; i < popSize; i++) {
			fitnessPop[i] = bentCigarFunction(populations[i], dimensions);
			//System.out.println(fitnessPop[i]);
		}

		// calculate fitness
		while(evals<evaluations_limit_){
			// Select parents
			// Apply crossover / mutation operators
			double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			// Check fitness of unknown fuction
			Double fitness = (double) evaluation_.evaluate(child);
			evals++;
			// Select survivors
		}

	}
}
