
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population{

	//VARIABLES
	public int popSize;
	private final int DIMENSIONS = 10;
	private List<Individual> population;
	//private Random rnd;


	//CONSTRUCTOR
	public Population(int popSize) {
		this.popSize = popSize;	
		this.population = new ArrayList<Individual>();
	}


	//METHODS
//	public void populate(Random rnd) {
//		for (int i = 0; i < this.popSize; i++) {					//FOR ALL INDIVIDUALS
//			double[] randomValues = new double[DIMENSIONS];			//INITIATE RANDOM X ARRAY WITH CORRECT DIMENSIONS
//			for (int j = 0; j < DIMENSIONS; j++) {					//CREATE RANDOM X VALUES
//				randomValues[j] = -5 + rnd.nextDouble() * 10;		//ASSIGN VALUES TO EACH POSITION IN ARRAY
//			}
//			Individual newInd = new Individual(randomValues);		//CREATE NEW INDIVIDUAL WITH RANDOM X VALUES
//			this.population.add(newInd);							//ADD INDIVIDUAL TO POPULATION
//		}
//	}
}