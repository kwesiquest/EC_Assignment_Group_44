
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectionMethods {

	public SelectionMethods(){
		
	}
	
	//METHODS
	// roulette
	// tournament
	

	public List<Individual> UniformParentSelection(int numberOfParentsToSelect, Random rnd, Population population){
		List<Individual> result = new ArrayList<Individual>();

		List<Integer> selection = new ArrayList<Integer>();
		for (int i = 0; i< population.popSize; i++) {
			selection.add(i);
		}

		for(int i=0; i<numberOfParentsToSelect; i++) {
			int a = rnd.nextInt(selection.size());
			int b = selection.remove(a);

			result.add(population.getIndividualAtIndex(b));

		}
		return result;
		
	}
	
	public List<Individual> GetFittestIndividuals(int numberOfParentsToSelect, Population population){

		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[numberOfParentsToSelect]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[numberOfParentsToSelect]; //Stores fitness scores of fit individuals
		double lowestValue = 0; //As fitness is 0 to 10.
		for (int i = 0; i < population.popSize; i++) {

			Individual currIndividual = population.getIndividualAtIndex(i);
			double currFitness = currIndividual.getFitness();

			if( currFitness > lowestValue) {
				//So we will be inserting this individual into top 10 cause its fitness is higher than one or more of the current top 10
				for (int j = 0; j < topfitnesvaluessarray.length; j++) {
					if (currFitness > topfitnesvaluessarray[j]) { //fitness of to insert element is higher than current array element
						if (j == 0) { 
							//This value falls of the left of the array
							//No need to adjust it as the next cycle will replace this value anyway
						}
						else if ( j == numberOfParentsToSelect - 1) { //This new to insert value is the highest we have seen till now. So insert it at the end of array

							topfitnesvaluessarray[j-1] = topfitnesvaluessarray[j];
							topfitnesvaluessarray[j] = currFitness;

							topfitnessindividualsarray[j-1] = topfitnessindividualsarray[j];
							topfitnessindividualsarray[j] = i;
						}
						else { 
							topfitnesvaluessarray[j-1] = topfitnesvaluessarray[j];
							topfitnessindividualsarray[j-1] = topfitnessindividualsarray[j];
						}
					}
					else { //fitness of to insert element is lower than current array element
						topfitnesvaluessarray[j-1] = currFitness; //Settle in previous slot
						topfitnessindividualsarray[j-1] = i;
						j = numberOfParentsToSelect; //End the for loop for this
					}		
					lowestValue = topfitnesvaluessarray[0];
				}
			}
		}

		//	Gather the fittest individuals in a list and return it.
		//	List is sorted from fittest to fit (highest fitness value at [0].
		List<Individual> fittestIndividuals = new ArrayList<Individual>();
		for (int i = topfitnessindividualsarray.length - 1; i > -1; i--) {
			fittestIndividuals.add(population.getIndividualAtIndex(topfitnessindividualsarray[i]));
		}
		return fittestIndividuals;
	}

	public List<Individual> GetWorstIndividuals(int count, Population population){

		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[count]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[count]; //Stores fitness scores of fit individuals
		for (int i = 0; i < topfitnesvaluessarray.length; i++) {
			topfitnesvaluessarray[i] = 11;
		}
		double highestValue = 11; //As fitness is 0 to 10.
		for (int i = 0; i < population.popSize; i++) {

			Individual currIndividual = population.getIndividualAtIndex(i);
			double currFitness = currIndividual.getFitness();

			if( currFitness < highestValue) {
				//So we will be inserting this individual into top 10 cause its fitness is higher than one or more of the current top 10
				for (int j = 0; j < topfitnesvaluessarray.length; j++) {
					if (currFitness < topfitnesvaluessarray[j]) { //fitness of to insert element is higher than current array element			
						if (j == 0) { 
							//This value falls of the left of the array
							//No need to adjust it as the next cycle will replace this value anyway		
						}
						else if ( j == count - 1) { //This new to insert value is the highest we have seen till now. So insert it at the end of array

							topfitnesvaluessarray[j-1] = topfitnesvaluessarray[j];
							topfitnesvaluessarray[j] = currFitness;

							topfitnessindividualsarray[j-1] = topfitnessindividualsarray[j];
							topfitnessindividualsarray[j] = i;
						}
						else { 

							topfitnesvaluessarray[j-1] = topfitnesvaluessarray[j];
							topfitnessindividualsarray[j-1] = topfitnessindividualsarray[j];
						}
					}
					else { //fitness of to insert element is lower than current array element
						topfitnesvaluessarray[j-1] = currFitness; //Settle in previous slot

						topfitnessindividualsarray[j-1] = i;

						j = count; //End the for loop for this
					}		
					highestValue = topfitnesvaluessarray[0];
				}
			}
		}


		//	Gather the fittest individuals in a list and return it.
		//	List is sorted from fittest to fit (highest fitness value at [0].
		List<Individual> fittestIndividuals = new ArrayList<Individual>();
		for (int i = topfitnessindividualsarray.length - 1; i > -1; i--) {
			fittestIndividuals.add(population.getIndividualAtIndex(topfitnessindividualsarray[i]));
		}
		return fittestIndividuals;
	}
	
	//DONE: 1-point CrossOver
	//DO: n-point Cross
	//DO: Uniform CrossOver
	
	
	
}