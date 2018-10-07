//popSize public gemaakt voor selection methods 

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class Population{

	//VARIABLES
	public int popSize; 
	public String popType;
	private final int DIMENSIONS = 10;
	public List<Individual> population;
	
	//CONSTRUCTOR
	public Population(int popSize, String popType) {
		this.popSize = popSize;	
		this.popType = popType;
	}

	//METHODS
	// initialise
	// get fittest individuals
	// get worst individuals
		

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

	public double getStdFitness(){
		double averageFitness = getAverageFitness();
		double sumOfDeviances = 0;
		for (int i = 0; i < popSize; i++){
			Individual currIndividual = getIndividualAtIndex(i);
			double fitnessCurrIndividual = currIndividual.getFitness();
			sumOfDeviances += Math.pow(averageFitness - fitnessCurrIndividual, 2); 
		}
		double standardDeviation = Math.sqrt(sumOfDeviances / popSize);
		return standardDeviation;
	}

	public double getAverageFitness(){
		double sumFitness = 0;
		for (int i = 0; i < popSize; i++) {
			Individual currIndividual = getIndividualAtIndex(i);
			double fitnessCurrIndividual = currIndividual.getFitness();
			sumFitness += fitnessCurrIndividual;
		}

		double averageFitness = sumFitness / popSize;
		return averageFitness;
	}
	
	public double getMaxFitness(){
	double maxFitness = 0;
	for (int i = 0; i < popSize; i++) {
		Individual currIndividual = getIndividualAtIndex(i);
		double fitnessCurrIndividual = currIndividual.getFitness();
		if (fitnessCurrIndividual > maxFitness){
			maxFitness = fitnessCurrIndividual;
		}
	}
	return maxFitness;
	}

	public double cosineSimilarityMax(){
		double[] genotypeOneIndidivual = new double[DIMENSIONS]; 
		double[] genotypeOtherIndidivual = new double[DIMENSIONS]; 
		Individual oneIndividual = new Individual(genotypeOneIndidivual);
		Individual otherIndividual = new Individual(genotypeOtherIndidivual);
		double cosineSimilarityMax = 0.0;
		double cosineSimilarity = 0.0;

		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < popSize; j++){
				oneIndividual = getIndividualAtIndex(i);
				otherIndividual = getIndividualAtIndex(j);
				genotypeOneIndidivual = oneIndividual.getGenotype();
				genotypeOtherIndidivual = otherIndividual.getGenotype();
				cosineSimilarity = cosineSimilarityMetric(genotypeOneIndidivual, genotypeOtherIndidivual);
				if (Math.pow(cosineSimilarity, 2) > cosineSimilarityMax && Math.pow(cosineSimilarity, 2) < 1){
					cosineSimilarityMax = cosineSimilarity;
				}
			}
		}

		return cosineSimilarityMax;
	}
	
	public double getMaxDistance(String metric){
		double[] genotypeOneIndidivual = new double[DIMENSIONS]; 
		double[] genotypeOtherIndidivual = new double[DIMENSIONS]; 
		Individual oneIndividual = new Individual(genotypeOneIndidivual);
		Individual otherIndividual = new Individual(genotypeOtherIndidivual);
		double maxDistance = 0.0;
		double distance = 0.0;

		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < popSize; j++){
				oneIndividual = getIndividualAtIndex(i);
				otherIndividual = getIndividualAtIndex(j);
				genotypeOneIndidivual = oneIndividual.getGenotype();
				genotypeOtherIndidivual = otherIndividual.getGenotype();
				if (metric.equals("cosine")){
					distance = 1-(cosineSimilarityMetric(genotypeOneIndidivual, genotypeOtherIndidivual));
				} else {
					distance = euclideanDistanceMetric(genotypeOneIndidivual, genotypeOtherIndidivual);
				}
				if (distance > maxDistance){
					maxDistance = distance;
				}	
			}
		}

		return maxDistance;
	}
			

	public double cosineSimilarityMin(){
	double[] genotypeOneIndidivual = new double[DIMENSIONS]; 
	double[] genotypeOtherIndidivual = new double[DIMENSIONS]; 
	Individual oneIndividual = new Individual(genotypeOneIndidivual);
	Individual otherIndividual = new Individual(genotypeOtherIndidivual);
	double cosineSimilarityMin = 1;
	double cosineSimilarity = 0.0;

	for (int i = 0; i < popSize; i++) {
		for (int j = 0; j < popSize; j++){
			oneIndividual = getIndividualAtIndex(i);
			otherIndividual = getIndividualAtIndex(j);
			genotypeOneIndidivual = oneIndividual.getGenotype();
			genotypeOtherIndidivual = otherIndividual.getGenotype();
			cosineSimilarity = cosineSimilarityMetric(genotypeOneIndidivual, genotypeOtherIndidivual);
			if (Math.pow(cosineSimilarity, 2) < cosineSimilarityMin && Math.pow(cosineSimilarity, 2) > 0){
				cosineSimilarityMin = cosineSimilarity;
			}
		}
	}

	return cosineSimilarityMin;
}

	public double cosineSimilaritySmallestvsLargest(){

		double[] smallestGenotype = GetSmallestGenotype();
		double[] largestGenotype = GetLargestGenotype();
		return cosineSimilarityMetric(smallestGenotype, largestGenotype);
	}

	private static double cosineSimilarityMetric(double[] vectorA, double[] vectorB) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < vectorA.length; i++) {
	        dotProduct += vectorA[i] * vectorB[i];
	        normA += Math.pow(vectorA[i], 2);
	        normB += Math.pow(vectorB[i], 2);
	    }

	    double cosineSimilarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	    if (cosineSimilarity < 0.001){
	    	cosineSimilarity = 0.000;
	    }


	    return cosineSimilarity;
	}
	

	public double averageEuclideanDistance() {
		// double[] genotypeOneIndidivual = new double[DIMENSIONS]; 
		// double[] genotypeOtherIndidivual = new double[DIMENSIONS]; 

		double sumEuclideanDistances = 0;
		double nDistances = popSize * popSize;
		// double distance = 0;

		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < popSize; j++){
				Individual oneIndividual = getIndividualAtIndex(i);
				Individual otherIndividual = getIndividualAtIndex(j);
				double[] genotypeOneIndidivual = oneIndividual.getGenotype();
				double[] genotypeOtherIndidivual = otherIndividual.getGenotype();
				double distance = euclideanDistanceMetric(genotypeOneIndidivual, genotypeOtherIndidivual);
				sumEuclideanDistances += distance;
				}
			}
	
		// double average = sumEuclideanDistances / nDistances;

		return sumEuclideanDistances / nDistances;
	}


	public double averageCosineSimilarity() {
		// double[] genotypeOneIndidivual = new double[DIMENSIONS]; 
		// double[] genotypeOtherIndidivual = new double[DIMENSIONS]; 

		double sumCosineSimilarities = 0;
		double nDistances = popSize * popSize;
		// double distance = 0;

		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < popSize; j++){
				Individual oneIndividual = getIndividualAtIndex(i);
				Individual otherIndividual = getIndividualAtIndex(j);
				double[] genotypeOneIndidivual = oneIndividual.getGenotype();
				double[] genotypeOtherIndidivual = otherIndividual.getGenotype();
				double similarity = cosineSimilarityMetric(genotypeOneIndidivual, genotypeOtherIndidivual);
				sumCosineSimilarities += similarity;
				}
			}
		
		System.out.println(sumCosineSimilarities);
		// double average = sumEuclideanDistances / nDistances;

		return sumCosineSimilarities / nDistances;
	}


	private static double euclideanDistanceMetric(double[] genotypeA, double[] genotypeB) {
	    double subtraction = 0.0;
	    double subtractionSquared = 0.0;
	    double sumSubtractionsSquared = 0.0;
	    for (int i = 0; i < genotypeA.length; i++) {
	        subtraction = (genotypeA[i] - genotypeB[i]);
	        subtractionSquared = Math.pow(subtraction, 2);
	        sumSubtractionsSquared = (sumSubtractionsSquared + subtractionSquared);
	    }


	    double distance = Math.sqrt(sumSubtractionsSquared);

	    return distance;
	}



	private double[] GetSmallestGenotype(){

		double[] genotypeSmallestIndidivual = new double[DIMENSIONS]; 
		double sumGenotype = 0;
		double smallestSumGenotype = 50;
		for (int i = 0; i < popSize; i++) {

			Individual currIndividual = getIndividualAtIndex(i);
			double[] genotypeCurrIndividual = currIndividual.getGenotype();

			for(double g:genotypeCurrIndividual){
				sumGenotype += g;
			}

			if (sumGenotype < smallestSumGenotype) {
				smallestSumGenotype = sumGenotype;
				genotypeSmallestIndidivual = genotypeCurrIndividual;
			}
		}
		
		return genotypeSmallestIndidivual;
	}
	
	private double[] GetLargestGenotype(){

		double[] genotypeLargestIndidivual = new double[DIMENSIONS]; 
		double sumGenotype = 0;
		double largestSumGenotype = -50;
		for (int i = 0; i < popSize; i++) {

			Individual currIndividual = getIndividualAtIndex(i);
			double[] genotypeCurrIndividual = currIndividual.getGenotype();

			for(double g:genotypeCurrIndividual){
				sumGenotype += g;
			}

			if (sumGenotype > largestSumGenotype) {
				largestSumGenotype = sumGenotype;
				genotypeLargestIndidivual = genotypeCurrIndividual;
			}
		}
		
		return genotypeLargestIndidivual;
	}
}