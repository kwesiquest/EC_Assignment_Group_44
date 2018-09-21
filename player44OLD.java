package ec;
import org.vu.contest.ContestSubmission;

import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.List;
import java.lang.Math;

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
	
	
	
	public double[][] onePointCrossOver(double [][] parents){
		//parents[0].length already provides dimensions
		int dimensions = parents[0].length; //is 10
		
		//Draw a random integer which will signify AFTER which dimension we will be cutting
		//So if random integer = 2, you cut between 2 and 3.
		//This means the random integer can take values between 0 to 8, as a 9 will cut behind the 9 which is null
		int cutBehindThisDimension = rnd_.nextInt(dimensions - 1);
		
		double children[][] = new double[2][dimensions]; //For now we create two children
		
		//Child 0 gets the first cutBehindThisDimensions number of dimensions from parent 0
		//While child 1 gets the first cutBehindThisDimensions number of dimensions from parent 1
		for (int i = 0; i < cutBehindThisDimension; i++) {
			children[0][i] = parents[0][i];
			children[1][i] = parents[1][i];
		}
		
		//Child 0 gets the rest of his dimensions from parent 1
		//Child 1 gets the rest of his dimensions from parent 0
		for (int i = cutBehindThisDimension; i < dimensions; i++) {
			children[0][i] = parents[1][i]; //Note the parents are switched
			children[1][i] = parents[0][i]; //Note the parents are switched
		}
		return children;
	}
	
	
	//Returns the IDs of the fittest individuals, note that individuals towards the end of the array are most fittest.
	public int[] returnFittestIndividualIDs(double[][] populations, double fitnessPop[],int numberOfParentsToSelect){
		
		int[] topfitnessindividualsarray = new int[numberOfParentsToSelect]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[numberOfParentsToSelect]; //Stores fitness scores of fit individuals
		double lowestValue = 0; //As fitness is 0 to 10.
		for (int i = 0; i < fitnessPop.length; i++) {
			
			if( fitnessPop[i] > lowestValue) {
				
				//So we will be inserting this
				
				for (int j = 0; j < topfitnesvaluessarray.length; j++) {
					
					if (fitnessPop[i] > topfitnesvaluessarray[j]) {
						
						if (j == 0) { 
							
							//This value falls of the left of the array
							
						}
						else if ( j == 9) {
							
							topfitnesvaluessarray[j-1] = topfitnesvaluessarray[j];
							topfitnesvaluessarray[j] = fitnessPop[i];
							
							topfitnessindividualsarray[j-1] = topfitnessindividualsarray[j];
							topfitnessindividualsarray[j] = i;
							
						}
						else {
							
							topfitnesvaluessarray[j-1] = topfitnesvaluessarray[j];
							
							topfitnessindividualsarray[j-1] = topfitnessindividualsarray[j];
							
						}
					
					}
					else { //fitness of to insert element is lower than current array element
						
						topfitnesvaluessarray[j-1] = fitnessPop[i]; //Settle in previous slot
						
						topfitnessindividualsarray[j-1] = i;
						
						j = 10;
					}		
				lowestValue = topfitnesvaluessarray[0];
				}
			}
		}
		return topfitnessindividualsarray;
	}
	
	
	
	// Run your algorithm here
	public void run()
	{
		//	Number of evaluations done on individuals till now
		//	Program will stop at 10.000 evaluations
		int evals = 0;
		
		// Initialise population
		int popSize = 100;
		int dimensions = 10; //	All functions take 10 input variables, so 10 dimensions
		
		// Create the array which will hold all the individuals and their respective genotype/phenotype
		double populations[][] = new double[popSize][dimensions]; 

		// Generate random values for each dimension for each individual between [-5, 5]
		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < dimensions; j++) {
				populations[i][j] = -5 + rnd_.nextDouble() * 10;
			}
		}
		
		// Calculate fitness of all the individuals in the population
		double fitnessPop[] = new double[popSize];
		for (int i = 0; i < popSize; i++) {
			fitnessPop[i] = rnd_.nextDouble();//(double) evaluation_.evaluate(populations[i]);
			evals++;
		}
		int[] fittestIndividuals = returnFittestIndividualIDs(populations, fitnessPop, 10);
		
		//How do you instantiate a list from an array? is possible in other languages :thinking:
		List<Integer> fittestIndividualsList = new ArrayList<>();
		for (int i = 0; i < fittestIndividuals.length; i++) {
			fittestIndividualsList.add(fittestIndividuals[i]);
		}
		
		int numberOfParentsPerOffspring = 2;
		
		double parents[][] = new double[numberOfParentsPerOffspring][dimensions];
		//While valid group of parents can be found
		while (fittestIndividualsList.size() >= numberOfParentsPerOffspring) {
			
			//Get which individuals will bare this loop's child
			for (int i = 0; i < numberOfParentsPerOffspring; i++) {
				int popthis = rnd_.nextInt(fittestIndividualsList.size());
				int individualID = fittestIndividualsList.remove(popthis);
				parents[i] = populations[individualID];
			}
			
			//Make two children from the two parents
			double children[][] = onePointCrossOver(parents);
		}
		
		
		
		
		//System.out.println(selectBestParents(populations, fitnessPop, 10));
		//System.out.println(selectBestParents(populations, fitnessPop, 10));
		
		//use CMA-ES says teacher
		//10.000 evaluation allowed
		while(evals<evaluations_limit_){
			
			// Select parents
			
			
			
			int parentID = rnd_.nextInt(popSize);
			
			// Apply crossover / mutation operators
			double genes[] = populations[parentID].clone();
			
			for(int i = 0; i < genes.length; i++) {
				double chanceToMutateOneAllele = rnd_.nextDouble();
				if (chanceToMutateOneAllele < 0.05) {
					genes[i] = -5 + rnd_.nextDouble() * 10;
				}
			}
			
			double child[] = genes;
			//double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			
			
			// Check fitness of unknown fuction
			Double fitness = (double) evaluation_.evaluate(child);
			System.out.println(fitness);
			evals++;
			// Select survivors
		}
		System.out.println(evals);
		//System.out.println(fitnessPop[0]);

	}
}
