
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
	
	
	
	public double[][] oneCutCrossOver(double [][] parents, int dimensions){
		
		
		
		return parents;
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
	
	
	

	public void run()
	{
		// Run your algorithm here
		int evals = 0;
		
		// init population
		int popSize = 100;
		int dimensions = 10;
		double populations[][] = new double[popSize][dimensions];

		//generate random values for each dimension for each individual between [-5, 5]
		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < dimensions; j++) {
				populations[i][j] = -5 + rnd_.nextDouble() * 10;
			}
		}
		
		// calculate fitness
		double fitnessPop[] = new double[popSize];
		for (int i = 0; i < popSize; i++) {
			fitnessPop[i] = rnd_.nextDouble();//(double) evaluation_.evaluate(populations[i]);//bentCigarFunction(populations[i], dimensions);
			evals++;
		}
		int[] parents = returnFittestIndividualIDs(populations, fitnessPop, 10);
		
		//How do you instantiate a list from an array? is possible in other languages :thinking:
		List<Integer> myList = new ArrayList<>();
		for (int i = 0; i < parents.length; i++) {
			myList.add(parents[i]);
		}
		
		for (int i = 0; i < myList.size(); i++) {
			int popthis = rnd_.nextInt(myList.size());
			myList.remove(popthis);
		}
		
		
		
		
		//System.out.println(selectBestParents(populations, fitnessPop, 10));
		//System.out.println(selectBestParents(populations, fitnessPop, 10));
		
		
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