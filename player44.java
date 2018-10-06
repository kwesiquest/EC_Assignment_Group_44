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
	SelectionMethods select;
	CrossOver crossOver;
	Mutation mutation;
	Island island;

	public player44()
	{
		rnd_ = new Random();
		select = new SelectionMethods();
		crossOver = new CrossOver();
		mutation = new Mutation();
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

	private Population setup(int popSize) {
		Population population = new Population(popSize);
		population.initialiseNewRandomPopulation(rnd_);
		return population;
	}


	// Run your algorithm here
	public void run()
	{
		int popSize = 100;
		int dimensions = 10; //	All functions take 10 input variables, so 10 dimensions
		int generation = 0;
		boolean multiplePopulations = true;

		int evals = popSize; // As every new individual of the newly formed population had to be evaluated
		int numberOfParentsToSelect = 12;

		//System.out.println("Fitness At Evals:");

		int migrationRate = 10;
		int migrationSize = 5;

		Population population1 = setup(popSize);
		Island island = new Island(migrationRate, migrationSize);
		island.addPopulation(population1);

		if (multiplePopulations){
			Population population2 = setup(popSize);
			island.addPopulation(population2);
		}


		while(evals < evaluations_limit_){
			
			island.generations ++;

			for (Population population:island.populations){

				
				//	Parent selection
				List<Individual> selectedParents = select.GetFittestIndividuals(numberOfParentsToSelect, population);
								
				// Reproduction
				int numberOfParentsPerOffspring = 2;
				Individual parents[] = new Individual[numberOfParentsPerOffspring];
				
				List<Individual> childrenOffspring = new ArrayList<Individual>();
				////While valid group of parents can be found
				while (selectedParents.size() >= numberOfParentsPerOffspring) {
					//Get which individuals will bare this loop's child
					for (int i = 0; i < numberOfParentsPerOffspring; i++) {
						int popthis = rnd_.nextInt(selectedParents.size());
						parents[i] = selectedParents.remove(popthis);
					}
					
					//	Crossover
					List<Individual> newChildren = crossOver.onePointCrossOver(parents, rnd_); 
					childrenOffspring.addAll(newChildren);	
				}


				//	Apply (potential) mutation to the children, one child at a time
				List<Individual> childrenMutated = new ArrayList<Individual>();
				for (int i = 0; i < childrenOffspring.size(); i++) {
					Individual mutatedChild = childrenOffspring.get(i);
					mutation.uniformMutation(mutatedChild);
					childrenMutated.add(mutatedChild);
				}

				// Evaluate children
				List<Individual> children = new ArrayList<Individual>();
				for (int i = 0; i < childrenMutated.size(); i++) {
					Individual evaluatedChild = childrenMutated.get(i);
					double[] genotypeEvaluatedChild = evaluatedChild.getGenotype();
					double fitnessEvaluatedChild = (double) evaluation_.evaluate(genotypeEvaluatedChild);
					evaluatedChild.setFitness(fitnessEvaluatedChild);
					children.add(evaluatedChild);
					evals++;				
					if (evals == evaluations_limit_){
						return;
					}
				}

				// Check for migration
				if (multiplePopulations){
					island.migrate();
				}

				List<Individual> ok = select.GetWorstIndividuals(children.size(), population);
				population.removeIndividuals(ok);
				population.addIndividuals(children);

				double cosineDistanceMax = population.getMaxDistance("cosine");
				System.out.print("cosineDistanceMax: ");
				System.out.println(cosineDistanceMax);
				
				double euclidieanDistanceMax = population.getMaxDistance("euclidean");
				System.out.print("euclidieanDistanceMax: ");
				System.out.println(euclidieanDistanceMax);

				double averageEuclDistance = population.averageEuclideanDistance();
				System.out.print("average Euclidean Distance: ");
				System.out.println(averageEuclDistance);

				double averageFitness = population.getAverageFitness();
				System.out.print("averageFitness: ");
				System.out.println(averageFitness);

				double stdFitness = population.getStdFitness();
				System.out.print("stdFitness: ");
				System.out.println(stdFitness);

				double maxFitness = population.getMaxFitness();
				System.out.print("maxFitness: ");
				System.out.println(maxFitness);

				double cosineSimMin = population.cosineSimilarityMin();
				System.out.print("cosineSimMin: ");
				System.out.println(cosineSimMin);

			}
		}

	}

}