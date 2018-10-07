

import org.vu.contest.ContestSubmission;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player44 implements ContestSubmission
{
	Random rnd_;
	NormalDistribution randn;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;
	
	public player44()
	{
		rnd_ = new Random();
		randn = new NormalDistribution();
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
		randn.reseedRandomGenerator(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;
		
		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
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
	
	public static void main(String[] args) {
		player44 ok = new player44();
		ok.run();
	}
	
	public int[] GetWorstIndexes(double[] fitnessarray, int mu){

		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[mu]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[mu]; //Stores fitness scores of fit individuals
		for (int i = 0; i < mu; i++) {
			topfitnesvaluessarray[i] = Double.POSITIVE_INFINITY;
		}
		double highestValue = Double.POSITIVE_INFINITY; //As fitness is 0 to 10.
		for (int i = 0; i < fitnessarray.length; i++) {

			double currFitness = fitnessarray[i];
			
			if( currFitness <= highestValue) {
				//So we will be inserting this individual into top 10 cause its fitness is higher than one or more of the current top 10
				for (int j = 0; j < topfitnesvaluessarray.length; j++) {
					if (currFitness <= topfitnesvaluessarray[j]) { //fitness of to insert element is higher than current array element			
						if (j == 0) { 
							//This value falls of the left of the array
							//No need to adjust it as the next cycle will replace this value anyway		
						}
						else if ( j == mu - 1) { //This new to insert value is the highest we have seen till now. So insert it at the end of array

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

						j = mu; //End the for loop for this
					}		
					highestValue = topfitnesvaluessarray[0];
					//print(highestValue);
				}
			}
		}
		
		int wag1[] = new int[mu];
		for (int i = 0; i < mu; i++) {
			wag1[i] = topfitnessindividualsarray[mu-1-i];
		}
		return wag1;
	}
	
	public int[] GetBestIndexes(double[] fitnessarray, int mu){
		
		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[mu]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[mu]; //Stores fitness scores of fit individuals
		for (int i = 0; i < mu; i++) {
			topfitnesvaluessarray[i] = -1;
		}
		double lowestValue = topfitnesvaluessarray[0]; //As fitness is 0 to 10.
		for (int i = 0; i < fitnessarray.length; i++) {

			double currFitness = fitnessarray[i];
			
			if( currFitness >= lowestValue) {
				//So we will be inserting this individual into top 10 cause its fitness is higher than one or more of the current top 10
				for (int j = 0; j < topfitnesvaluessarray.length; j++) {
					if (currFitness >= topfitnesvaluessarray[j]) { //fitness of to insert element is higher than current array element			
						if (j == 0) { 
							//This value falls of the left of the array
							//No need to adjust it as the next cycle will replace this value anyway		
						}
						else if ( j == mu - 1) { //This new to insert value is the highest we have seen till now. So insert it at the end of array

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

						j = mu; //End the for loop for this
					}		
					lowestValue = topfitnesvaluessarray[0];
					//print(highestValue);
				}
			}
		}
		
		//The lowest values are at the end, so turn the array around.
		//This way the lowest value is at the start and increasing
		int wag1[] = new int[mu];
		for (int i = 0; i < mu; i++) {
			wag1[i] = topfitnessindividualsarray[mu-1-i];
		}
		
		return wag1;
	}
	
	
	public double norm(double[] vector) {
		double sum = 0;
		for (int i = 0; i < vector.length; i ++) {
			sum = sum + Math.pow(vector[i], 2);
		}
		return Math.sqrt(sum);
	}
	
	public double sumvector(double vector[]) {
		double sum = 0;
		for (int i = 0; i < vector.length; i++) {
			sum = sum + vector[i];
		}
		return sum;
	}
	
	public static double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
	
	public void printmatrix(double matrix[][]) {
		System.out.println();
		for (int i = 0; i < matrix.length;i++) {
			for (int j = 0; j < matrix[0].length;j++) {
				System.out.print(matrix[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public void printvector(double vector[]) {
		for (int i = 0; i < vector.length;i++) {
			System.out.println(vector[i]);
		}
	}
	
	public void print(double value) {
		System.out.println(value);
	}
    
	public void run(){
		//inputs
		
		int N = 10; //Number of objective variables/problem dimensions
		double[] xmean = new double[N];//{-2.5,-2.0,-1.5,-1.0,-0.5,0.0,0.5,1.0,1.5,2.0}; //objective variables initial point
		for (int i = 0; i < xmean.length; i++) {
			xmean[i] = -5 + rnd_.nextDouble() * 10;
		}
		double sigma = 0.3; //coordinate wise standard deviation (step size)
		
		int lambda = (int) (4 + Math.floor(3 * Math.log(N))); //=7, population size
		double doublemu = lambda / 2.; //number of parents/points for recombination
		
		double touse[] = new double[(int)doublemu];
		for (int i = 0; i < (int)doublemu; i++) {
			touse[i] = Math.log(i+1);
		}
		
		
		double[] weights = new double[(int) doublemu]; // array for weighted recombination
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Math.log(doublemu + 0.5) - touse[i];
		}
		
		int mu = (int)Math.floor(doublemu);  //mu = floor(mu)
		
		double sumweights = sumvector(weights);
		for (int i = 0; i < weights.length; i++) {
			weights[i] = weights[i]/sumweights;
		}
		double weights2[] = new double[weights.length];
		for (int i = 0; i < weights.length; i++) {
			weights2[i] = Math.pow(weights[i],2);
		}
		double mueff = Math.pow(sumvector(weights),2) / sumvector(weights2);
		
		double cc = (4 + mueff/N) / (N+4 + 2 * mueff/N);
		double cs = (mueff+2) / (N+mueff+5);
		double cl = 2 / (Math.pow(N+1.3,2) + mueff);
		double cmu = Math.min(1-cl, 2 * (mueff-2+1/mueff) / (Math.pow(N+2,2) +mueff));
		double damps = 1 + 2 * Math.max(0, Math.sqrt((mueff-1)/(N+1))-1) + cs;
		
		double pc[] = new double[N];
		double ps[] = new double[N]; //% evolution paths for C and sigma
		double B[][] = new double[N][N]; //% B defines the coordinate system
		double D[][] = new double[N][N]; //% diagonal D defines the scaling
		for(int i = 0; i < N; i++) {
			B[i][i] = 1;
			D[i][i] = 1;
		}
		
		double C[][] = new double[N][N];
		double Btranspose[][] = transposeMatrix(B);
		for (int i = 0; i < N; i++) {
			for( int j = 0; j < N; j++) {
				C[i][j] = B[i][j] * Math.pow(D[i][j],2) * Btranspose[i][j];
			}
		}
		
		
		double Dpow10[][] = new double[D.length][D.length];
		for (int i = 0; i < D.length; i++) {
			Dpow10[i][i] = Math.pow(D[i][i], -1);
		}
		
		double invsqrtC[][] = transposeMatrix(B);
		for (int i = 0; i < N; i++) {
			for( int j = 0; j < N; j++) {
				invsqrtC[i][j] = B[i][j] * Dpow10[i][j] * Btranspose[i][j];
			}
		}
		
		double eigeneval = 0;
		double chiN = Math.pow(N, 0.5) * (1. - 1./(4*N)+1. / (21.*Math.pow(N, 2)));
		
		
		double max = 0.;
		double min = 2929293332.;
		double best = 349439349439349.;
		double bestgenotype[] = new double[N];
		
		int counteval = 0;
		while (counteval < evaluations_limit_) {
			double arx[][] = new double[N][lambda];
			double arfitness[] = new double[lambda];
			for (int k = 0; k < lambda; k++) {
				
				double draws[] = new double[N];
				for (int i = 0; i < draws.length; i++) {
					draws[i] = D[i][i] * randn.sample();
				}
				for (int i = 0; i < N; i++) {
					
					for (int j = 0; j < N; j++) {
						arx[i][k] = arx[i][k] + sigma * B[i][j] * draws[j];
					}
					
					arx[i][k] = arx[i][k] + xmean[i];
				}
				
				double genotype[] = new double[N];
				for (int i = 0; i < genotype.length; i++) {
					genotype[i] = arx[i][k];
				}
				arfitness[k] = (double) evaluation_.evaluate(genotype);
				if (arfitness[k] < best) {
					best = arfitness[k];
					for (int i = 0; i < N; i++) {
						bestgenotype[i] = arx[i][k]; 
					}
					
				}
				counteval = counteval + 1;
				if (counteval == evaluations_limit_ - 1) {
					//System.out.println("bestfitness:");
					//print(best);
					//printvector(bestgenotype);
					return;
				}
			}
			
			int[] arindex = GetBestIndexes(arfitness, mu);
			for (int i = 0; i < arindex.length; i++) {
				//print(arindex[i]);
				if (arfitness[i] == Double.POSITIVE_INFINITY) {
					return; //if one of the values are infinite, sorting doenst work
				}
			}
			//printvector(arfitness);
			
			double xold[] = xmean.clone();
			
			
			for (int i = 0; i < N; i++) {
				
				xmean[i] = 0;
				for(int j = 0; j < mu; j++) {
					
					xmean[i] = xmean[i] + arx[i][arindex[j]] * weights[j];
					
				}
				
				
			}
			
			double sum0x[] = new double[N];
			for (int i = 0; i < N; i++) {
				
				sum0x[i] = 0;
				for(int j = 0; j < N; j++) {
					
					sum0x[i] = sum0x[i] + invsqrtC[i][j] * (xmean[i]-xold[i]) / sigma;
					
				}
				
				
				ps[i] = (1.-cs)*ps[i] + Math.sqrt(cs*(2.-cs)*mueff) * sum0x[i];
				
			}
			
			boolean hsig = norm(ps)/Math.sqrt(1.-Math.pow(1.-cs,2.*counteval/lambda)) /chiN < 1.4 + 2./(N+1.);
			for(int i = 0; i < N; i++) {
				
				pc[i] = (1.-cc)*pc[i];
				
				if(hsig) { //if true, then we must do something, first iteration is false
					
					pc[i] = pc[i] + Math.sqrt(cc*(2.-cc)*mueff) * (xmean[i]-xold[i]) / sigma;
					
				}
			}
			
			//repmat(xold, 1, mu);
			double repmatted[][] = new double[N][mu];
			for (int i = 0; i < mu; i++) {
				for (int j = 0; j < xold.length; j++) {
					repmatted[j][i] = xold[j];
				}
			}

			double artmp[][] = new double[N][mu];
			for (int i = 0; i < xold.length; i++) {
				for (int j = 0; j < mu; j++) {
					artmp[i][j] = artmp[i][j] + (1/sigma) * ( arx[i][arindex[j]] - repmatted[i][j] );
				}
			}
			
			double first[][] = new double[C.length][C.length];
			for (int i = 0; i < C.length; i++) {
				
				for (int j = 0; j < C.length; j++) {
					
					first[i][j] = (1.-cl-cmu) * C[i][j];
					
				}
				
			}
			
			double second[][] = new double[pc.length][pc.length];
			for (int i = 0; i < pc.length; i++) {
				
				for (int j = 0; j < pc.length; j++) {
					
					second[i][j] =  pc[i] * pc[j] ; //cl applied later
					
				}
				
			}
			//hsig is false first iteration, so its zero
			
			if (hsig == false) {
				double third[][] = new double[C.length][C.length];
				
				for (int i = 0; i < C.length; i++) {
					
					for (int j = 0; j < C.length; j++) {
						
						third[i][j] = cc*(2.-cc) * C[i][j];
						second[i][j] = second[i][j] + third[i][j];
						
					}
					
				}

			}
			
			
			double weightsdiagonal[][] = new double[weights.length][weights.length];
			for (int i = 0; i < weights.length; i++) {
				
				weightsdiagonal[i][i] = weights[i];
				
			}
			
			
			
			double fourth1[][] = new double[artmp.length][artmp[0].length];
			for (int i = 0; i < artmp.length; i++) {
				
				for (int j = 0; j < artmp[0].length; j++) {
					
					double sum9 = 0;
					for (int k = 0; k < artmp[0].length; k++) { //what column of weights to multiply
						
						sum9 = sum9 + artmp[i][k] * weightsdiagonal[k][j];
						
					}
					fourth1[i][j] = sum9;
				}
				
			}
			
			
			double artmptranspose[][] = transposeMatrix(artmp);
			double fourth2[][] = new double[N][N];
			for (int i = 0; i < fourth1.length; i++) {
				for (int j = 0; j < fourth1.length; j++) {
					double sum8 = 0;
					for (int k = 0; k < fourth1[0].length; k++) { //what column of weights to multiply
						sum8 = sum8 + fourth1[i][k] * artmptranspose[k][j];
					}
					fourth2[i][j] = cmu * sum8;	
				}	
			}
			
			
			
			for (int i = 0; i < C.length; i++) {
				for (int j = 0; j < C.length; j++) {	
					C[i][j] = first[i][j] + cl * second[i][j] + fourth2[i][j];
				}	
			}
			sigma = sigma * Math.exp((cs/damps)*(norm(ps)/chiN - 1));
			
			
			
			if (counteval - eigeneval > lambda / (cl + cmu )/N/10) {
				
				//Enforce symmetry
				eigeneval = counteval;
				double triuC[][] = new double[C.length][C.length];
				for (int i = 0; i < C.length; i++) {
					for (int j = 0 + i; j < C.length; j++) {
						triuC[i][j] = C[i][j];
					}
					
				}

				double triuC1[][] = new double[C.length][C.length];
				for (int i = 0; i < C.length; i++) {
					for (int j = 0 + i + 1; j < C.length; j++) {
						triuC1[i][j] = C[i][j];
					}
				}
				
				
				double triuC1transpose[][] = transposeMatrix(triuC1);
				
				for (int i = 0; i < C.length; i++) {
					for (int j = 0 + i + 1; j < C.length; j++) {
						C[i][j] = triuC[i][j] + triuC1transpose[i][j];
					}
					
				}
				
				

				//Eigenvalues
				RealMatrix test = MatrixUtils.createRealMatrix(C);
				EigenDecomposition ok = new EigenDecomposition(test);
				
				
				double hm[] = ok.getRealEigenvalues();
				for (int i = 0; i < D.length; i++) {
					D[i][i] = hm[i]; //lambda
				}
				
				RealMatrix eigenvector = ok.getV();
				for (int i = 0; i < C.length; i++) {
					double oh[] = eigenvector.getColumn(i);
					for (int j = 0; j < C.length; j++) {
						B[j][i] = oh[j]; //P
					}
				}
				
				for (int i = 0; i < D.length; i++) {
					D[i][i] = Math.sqrt(D[i][i]);
				}
				
				
				double btranspose[][] = transposeMatrix(B);
				
				double Dpow1[][] = new double[D.length][D.length];
				for (int i = 0; i < D.length; i++) {
					Dpow1[i][i] = Math.pow(D[i][i], -1);
				}
				
				
				
				double sumleft[][] = new double[C.length][C.length];
				for (int i = 0; i < C.length; i++) {
					
					for (int j = 0; j < C.length; j++) { //Which col of the left matrix; which row of the right matrix
						
						double sume = 0;
						for (int k = 0; k < C.length; k++) { //Which col of the left matrix, which col of the 
							
							sume = sume + B[i][k] * Dpow1[k][j];
							
						}
						sumleft[i][j] = sume;
					}
				}
				
				
				double sumright[][] = new double[C.length][C.length];
				for (int i = 0; i < C.length; i++) {
					
					for (int j = 0; j < C.length; j++) { //Which col of the left matrix; which row of the right matrix
						
						double sume = 0;
						for (int k = 0; k < C.length; k++) { //Which col of the left matrix, which col of the 
							
							sume = sume + sumleft[i][k] * btranspose[k][j];
							
						}
						sumright[i][j] = sume;
					}
				}
				
				for (int i = 0; i < C.length; i++) {
					for (int j = 0; j < C.length; j++) { //Which col of the left matrix; which row of the right matrix
						invsqrtC[i][j] = sumright[i][j];
					}
				}
			}
			
			min = 3003030303989899898.;
			max = -9999999999999999999.;
			
			for (int i = 0; i < D.length; i++) {
				if (D[i][i] < min) {
					min = D[i][i];
				}
				else if(D[i][i] > max){
					max = D[i][i];
				}
			}
			if (max > 1*Math.exp(7)*min ) {
				break;
			}
		}
		//System.out.println("bestfitness:");
		//print(best);
		//printvector(bestgenotype);
	}
}
