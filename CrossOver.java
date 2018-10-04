import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossOver {

	int numberOfParents;
	int numberOfChildren;
	
	public CrossOver() {
		numberOfParents = 2;
		numberOfChildren = 2;
	}

	//METHODS
	//DONE: 1-point CrossOver
	//DO: n-point Cross
	//DO: Uniform CrossOver
	//DO: averaging
	
	
	public List<Individual> onePointCrossOver(Individual[] parents,  Random rnd_){
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
	
	


}