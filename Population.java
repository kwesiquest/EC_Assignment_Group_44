
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population{

	//VARIABLES
	private int popSize;
	private final int DIMENSIONS = 10;
	private List<Individual> population;

	//CONSTRUCTOR
	public Population(int popSize) {
		this.popSize = popSize;	
	}

	//METHODS

	public List<Individual> UniformParentSelection(int numberOfParentsToSelect, Random rnd){
		List<Individual> result = new ArrayList<Individual>();

		List<Integer> selection = new ArrayList<Integer>();
		for (int i = 0; i<this.popSize; i++) {
			selection.add(i);
		}

		for(int i=0; i<numberOfParentsToSelect; i++) {
			int a = rnd.nextInt(selection.size());
			int b = selection.remove(a);

			result.add(getIndividualAtIndex(b));

		}
		return result;
	}


	public List<Individual> GetFittestIndividuals(int numberOfParentsToSelect){

		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[numberOfParentsToSelect]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[numberOfParentsToSelect]; //Stores fitness scores of fit individuals
		double lowestValue = 0; //As fitness is 0 to 10.
		for (int i = 0; i < popSize; i++) {

			Individual currIndividual = getIndividualAtIndex(i);
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
			fittestIndividuals.add(getIndividualAtIndex(topfitnessindividualsarray[i]));
		}
		return fittestIndividuals;
	}

	public List<Individual> GetWorstIndividuals(int count){

		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[count]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[count]; //Stores fitness scores of fit individuals
		for (int i = 0; i < topfitnesvaluessarray.length; i++) {
			topfitnesvaluessarray[i] = 11;
		}
		double highestValue = 11; //As fitness is 0 to 10.
		for (int i = 0; i < popSize; i++) {

			Individual currIndividual = getIndividualAtIndex(i);
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
			fittestIndividuals.add(getIndividualAtIndex(topfitnessindividualsarray[i]));
		}
		return fittestIndividuals;
	}

	public void initialiseNewRandomPopulation(Random rnd) {
		this.population = new ArrayList<Individual>();
		for (int i = 0; i < this.popSize; i++) {					//FOR ALL DESIRED POPULATION SIZE
			double[] randomValues = new double[DIMENSIONS];			//INITIATE RANDOM X ARRAY WITH CORRECT DIMENSIONS
			for (int j = 0; j < DIMENSIONS; j++) {					//CREATE RANDOM X VALUES
				randomValues[j] = -5 + rnd.nextDouble() * 10;		//ASSIGN VALUES TO EACH POSITION IN ARRAY
			}
			Individual newInd = new Individual(randomValues);		//CREATE NEW INDIVIDUAL WITH RANDOM X VALUES
			this.population.add(newInd);							//ADD INDIVIDUAL TO POPULATION
		}
	}

	public void addIndividual(Individual individual) {
		this.population.add(individual);
	}

	public void addIndividuals(List<Individual> individuals) {
		for (int i = 0; i < individuals.size(); i++) {
			this.population.add(individuals.get(i));
		}
		this.popSize = this.population.size();
	}

	public void removeIndividual(Individual individual) {
		this.population.remove(individual);
	}

	public void removeIndividuals(List<Individual> individuals) {
		for (int i = 0; i < individuals.size(); i++) {
			this.population.remove(individuals.get(i));
		}
		this.popSize = this.population.size();
	}

	public Individual getIndividualAtIndex(int index) {
		return this.population.get(index);
	}

	public int getPopSize() {
		return this.popSize;
	}
}