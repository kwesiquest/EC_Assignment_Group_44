import org.vu.contest.ContestSubmission;

import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.List;
import java.lang.Math;
import java.lang.reflect.Array;

public class player44 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
	private int evaluations_limit_; //10.000

	public player44()
	{
		rnd_ = new Random();
	}

	public static void main(String[] args) {
		System.out.println("Start");
		player44 player44Object = new player44();
		player44Object.run();
	}

	public void setSeed(long seed)
	{
		// Set seed of algorithms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();
		// Get evaluation limit
		evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations")); //10.000
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
		
		//This for loop calculates what is inside the summation sign
		for(int i = 1; i < dimensions ; i++) {
			result += Math.pow(phenotype[i],2);
		}
		//10^6 * result of summation sum
		result *= Math.pow(10, 6);
		
		//+ left side of function (x1^2)
		result +=  Math.pow(phenotype[0],2);
		
		return result;
	}
	
	
	
	public List<Individual> onePointCrossOver(Individual[] parents){
		//parents[0].length already provides dimensions
		int dimensions = parents[0].getDimensionsCount(); //is 10
		
		//Draw a random integer which will signify AFTER which dimension we will be cutting
		//So if random integer = 2, you cut between 2 and 3.
		//This means the random integer can take values between 0 to 8, as a 9 will cut behind the 9 which is null
		int cutBehindThisDimension = rnd_.nextInt(dimensions - 1);
		
		//Lets create the childrens genotype
		double genotype[][] = new double[2][dimensions];
		
		//Child 0 gets the first cutBehindThisDimensions number of dimensions from parent 0
		//While child 1 gets the first cutBehindThisDimensions number of dimensions from parent 1
		for (int i = 0; i < cutBehindThisDimension; i++) {
			genotype[0][i] = parents[0].getAlleleAtDim(i);
			genotype[1][i] = parents[1].getAlleleAtDim(i);
		}
		
		//Child 0 gets the rest of his dimensions from parent 1
		//Child 1 gets the rest of his dimensions from parent 0
		for (int i = cutBehindThisDimension; i < dimensions; i++) {
			genotype[1][i] = parents[1].getAlleleAtDim(i); //Note the parents are switched
			genotype[1][i] = parents[0].getAlleleAtDim(i); //Note the parents are switched
		}
		
		//Instantiate child
		List <Individual> children = new ArrayList<Individual>();
		for (int i = 0; i < genotype.length; i++) {
			children.add(new Individual(genotype[i]));
		}
		return children;
	}
	
	public void uniformMutation(Individual child){
		
		double[] genotype = child.getGenotype();
		int dimensions = genotype.length; //is 10
		float chance = 1.0f / dimensions; //is 0.1
		
		//	All dimensions/alleles have an equal chance (10%) to mutate
		int mutationsReceived = 0;
		for (int i = 0; i < dimensions; i++) {
			if (rnd_.nextFloat() < chance) {
				genotype[i] = -5 + rnd_.nextDouble() * 10;
				mutationsReceived++;
			}
		}
	}
	

	private Population setup(int popSize, int dimensions) {
		Population population = new Population(popSize = 100);
		population.initialiseNewRandomPopulation(rnd_);
		
		// Generate random values for each dimension for each individual between [-5, 5]
		for (int i = 0; i < population.getPopSize(); i++) {
			
			Individual individual = population.getIndividualAtIndex(i); 
			double genotype[] = individual.getGenotype();
			//individual.setFitness((double) evaluation_.evaluate(genotype));  
			individual.setFitness((double) evaluation_.evaluate(individual.getGenotype()));
		}
		return population;
	}
	
	
	// Run your algorithm here
	public void run()
	{
		int popSize = 100;
		int dimensions = 10; //	All functions take 10 input variables, so 10 dimensions
		Population population = setup(popSize, dimensions);
		int evals = popSize; // As every new individual of the newly formed population had to be evaluated
		
		while(evals<evaluations_limit_){
			
			//	Parent selection
			//	First we see who are the fittest individuals
			List<Individual> fittestIndividuals = population.GetFittestIndividuals(10);
			
			//	Ok, so who will breed with who
			int numberOfParentsPerOffspring = 2;
			Individual parents[] = new Individual[numberOfParentsPerOffspring];
			List<Individual> children = new ArrayList<Individual>();
			////While valid group of parents can be found
			while (fittestIndividuals.size() >= numberOfParentsPerOffspring) {
				
				//Get which individuals will bare this loop's child
				for (int i = 0; i < numberOfParentsPerOffspring; i++) {
					int popthis = rnd_.nextInt(fittestIndividuals.size());
					parents[i] = fittestIndividuals.remove(popthis);
				}
				
				//	Make two children from the two parents
				List<Individual> newChildren = onePointCrossOver(parents); 
				
				//	Add the children found in this iteration to the collection of children
				children.addAll(newChildren);	
			}
			
			
			
			//	Apply (potential) mutation to the children, one child at a time
			for (int i = 0; i < children.size(); i++) {
				uniformMutation(children.get(i));
				children.get(i).setFitness((double) evaluation_.evaluate(children.get(i).getGenotype()));
				evals++;
			}
			
			
			List<Individual> ok = population.GetWorstIndividuals(10);
			population.removeIndividuals(ok);
			population.addIndividuals(children);
			
			//double child[] = genes;
			//double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			
			// Check fitness of unknown fuction
			//Double fitness = (double) evaluation_.evaluate(child);
			//evals++;
			// Select survivors
		}
	}
}