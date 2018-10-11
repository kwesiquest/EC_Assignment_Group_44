import java.util.Random;

public class Mutation {

	Random rnd_;
	
	public Mutation() {
		
		rnd_ = new Random();


	}

	//METHODS
	//DONE: Uniform Mutation
	//DO: Nonuniform Mutation
	//DO: Self-Adaptive Mutation
	//gaussian
	//stepsized mutations
	
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



}