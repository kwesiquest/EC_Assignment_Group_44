package ec;

import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.LUDecomposition;

public class CMA_ES {
	
	Random rnd_;
	NormalDistribution randn;
	
	public CMA_ES() {
		rnd_ = new Random();
		randn = new NormalDistribution();
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		CMA_ES ok = new CMA_ES();
		ok.CMA_ES();
	}
	
	public int[] sortfitnessbyindex(double[] fitnessarray){
		
		//	Both topfitness arrays will sort by fittest at the end of the array
		int[] topfitnessindividualsarray = new int[fitnessarray.length]; //Stores id's of fit individuals
		double[] topfitnesvaluessarray = new double[fitnessarray.length]; //Stores fitness scores of fit individuals
		double lowestValue = 0; //As fitness is 0 to 10.
		for (int i = 0; i < fitnessarray.length; i++) {

			double currFitness = fitnessarray[i];
			
			if( currFitness > lowestValue) {
				
				//So we will be inserting this individual into top 10 cause its fitness is higher than one or more of the current top 10
				for (int j = 0; j < topfitnesvaluessarray.length; j++) {
					if (currFitness > topfitnesvaluessarray[j]) { //fitness of to insert element is higher than current array element
						if (j == 0) { 
							//This value falls of the left of the array
							//No need to adjust it as the next cycle will replace this value anyway
						}
						else if ( j == fitnessarray.length - 1) { //This new to insert value is the highest we have seen till now. So insert it at the end of array

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
						j = fitnessarray.length; //End the for loop for this
					}		
					lowestValue = topfitnesvaluessarray[0];
				}
			}
		}

		return topfitnessindividualsarray;
	}
	
	public double norm(double[] vector) {
		double sum = 0;
		for (int i = 0; i < vector.length; i ++) {
			sum = sum + Math.pow(vector[i], 2);
		}
		return Math.sqrt(sum);
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
	
	
	
	public void CMA_ES(){
		//inputs
		int N = 10; //Number of objective variables/problem dimensions
		double[] xmean = {-2.5,-2.0,-1.5,-1.0,-0.5,0.0,0.5,1.0,1.5,2.0}; //objective variables initial point
		double sigma = 0.3; //coordinate wise standard deviation (step size)
		double stopfitness = 0.1; //stop if fitness < stopfitness (minimisation)
		int stopeval = 1000; //stop after #evals
		
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
		boolean hiu = true;
		
		int counteval = 0;
		while (counteval < stopeval) {
			
			double arx[][] = new double[N][lambda];
			double arfitness[] = new double[lambda];
			for (int k = 0; k < lambda; k++) {
				
				double draws[] = new double[N];
				for (int i = 0; i < draws.length; i++) {
					//draws[i] = D[i][i] * randn.sample();
					draws[i] = D[i][i] * 0.5 * i;
				}
				
				
				
				for (int i = 0; i < N; i++) {
					
					for (int j = 0; j < N; j++) {
						arx[i][k] = arx[i][k] + sigma * B[i][j] * draws[i];
					}
					
					arx[i][k] = arx[i][k] + xmean[i];
				}
				
				
				double genotype[] = new double[N];
				for (int i = 0; i < genotype.length; i++) {
					genotype[i] = arx[i][k];
				}
				arfitness[k] = bentCigarFunction(genotype, N);
				if (arfitness[k] < best) {
					best = arfitness[k];
				}
				counteval = counteval + 1;
			}
			
			
			int[] arindex = sortfitnessbyindex(arfitness);
			double xold[] = xmean.clone();

			for (int i = 0; i < N; i++) {
				
				xmean[i] = 0;
				for(int j = 0; j < mu; j++) {
					
					xmean[i] = xmean[i] + arx[i][arindex[j]] * weights[j];
					
				}
				
				
			}
			
			//printvector((1.-cs)*ps[i]);
			
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
					
					pc[i] = pc[i] * Math.sqrt(cc*(2.-cc)*mueff) * (xmean[i]-xold[i]) / sigma;
					
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
					artmp[i][j] = artmp[i][j] + (1/sigma) * ( arx[i][j] - repmatted[i][j] );
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
					
					second[i][j] =  pc[j] * pc[i] ; //cl applied later
					
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
				
				if (false == true) {
					B = new double[][] {
						{0., 0., 0., 1.0, 0., 0., 0., 0., 0., 0.},
						{0.2506, 0.0191, 0.7156, 0.0, 0.2256, -0.5993, 0.0559, -0.0882, -0.0151, 0.0592},
						{-0.1674, 0.0858, 0.4547, 0, -0.6375, 0.1774,0.1118 ,0.5198  ,  0.1525 ,0.1185},
						{-0.3562, 0.0606, 0.0641, 0, -0.4293, -0.0886, 0.1467, -0.7803, 0.1063, 0.1777},
						{0.7931, 0.0166, -0.2668, 0, -0.3156, -0.0606, 0.2026, -0.0682, 0.3074, 0.2369},
						{-0.3503, -0.1679,-0.4125,0, -0.0265, -0.6550, 0.2167, 0.3236,  0.0992, 0.2962},
						{-0.1333 ,   0.6486,    0.0238  ,       0  ,  0.4282 ,   0.2026   , 0.2935  ,  0.0384  ,  0.3503  ,  0.3554},
						{ 0.1099,    0.2346,   -0.0449   ,      0 ,  -0.0980 ,   0.0369 ,   0.1612  ,  0.0231 ,  -0.8494  ,  0.4146},
						{-0.0286  , -0.6944 ,   0.1821   ,      0 ,   0.2543  ,  0.3542  ,  0.2526 ,  -0.0425 ,   0.0590 ,   0.4739},
						{-0.0010 ,   0.0469 ,   0.0030   ,      0 ,  -0.0206 ,  -0.0311 ,  -0.8360  , -0.0009  ,  0.1152  ,  0.5331}
					};
					

					D = new double[][] {
						{0.9722, 0., 0., 0., 0., 0., 0., 0., 0., 0.},
						{0., 0.9722, 0., 0., 0., 0., 0., 0., 0., 0.},
						{0., 0., 0.9722, 0., 0., 0., 0., 0., 0., 0.},
						{0., 0., 0., 0.9722, 0., 0., 0., 0., 0., 0.},
						{0., 0., 0., 0., 0.9722, 0., 0., 0., 0., 0.},
						{0., 0., 0., 0., 0., 0.9722, 0., 0., 0., 0.},
						{0., 0., 0., 0., 0., 0., 0.9722, 0., 0., 0.},
						{0., 0., 0., 0., 0., 0., 0., 0.9722, 0., 0.},
						{0., 0., 0., 0., 0., 0., 0., 0., 0.9722, 0.},
						{0., 0., 0., 0., 0., 0., 0., 0., 0., 2.4082}
					};
					hiu = false;
				}
				
				
				
				
				//double resu[][] = new double[N][N];
				//for (int i = 0; i < C.length; i++) {
				//	for (int j = 0; j < C.length; j++) {
				//		resu[i][j] = C[i][j] - D[i][j];
				//	}
				//}
				
				//RealMatrix test2 = MatrixUtils.createRealMatrix(resu);
				//LUDecomposition okokok = new LUDecomposition(test2);
				//print(okokok.getDeterminant());
				
				
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
			
			
			min = 3003030303.;
			max = -20203939;
			
			for (int i = 0; i < D.length; i++) {
				if (D[i][i] < min) {
					min = D[i][i];
				}
				else if(D[i][i] > max){
					max = D[i][i];
				}
			}
			if (arfitness[0] <= stopfitness || max > 1*Math.exp(7)*min ) {
				break;
			}
			print(arfitness[0]);
			//counteval = stopeval;		
		}
	}
}
