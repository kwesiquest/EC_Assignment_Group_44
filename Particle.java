
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;


public class Particle{

	public double[] position;
	public double[] velocity;
	public double[] pBest;
	public double fitnessIndBest;
	public double fitnessInd;
	public Random rnd;

	public Particle(double[] x0) {
		this.position = new double[10];
		this.velocity = new double[10];	
		this.pBest = new double[10]; //10?
		fitnessIndBest = -1;
		fitnessInd = -1;
		rnd = new Random();


		double rangeMin = -1;
		double rangeMax = 1;

		for(int i = 0; i < 10; i++) {
			this.velocity[i] = rangeMin + (rangeMax - rangeMin) * rnd.nextDouble();
			this.position[i] = x0[i];
		}
	}


	public void evaluate(ContestEvaluation evaluation_) {
		this.fitnessInd = (double) evaluation_.evaluate(this.position);
		
		if(this.fitnessInd > this.fitnessIndBest || this.fitnessIndBest==-1) { // >>>>>>
			this.pBest = this.position;
			this.fitnessIndBest = this.fitnessInd;
		}
	}


	public void update_velocity(double[] pos_best_g) {
		//W SHOULD BE BETWEEN 0.2 AND 0.9, ELSE BAD RESULTS
		double w = Double.parseDouble(System.getProperty("var2"));       // constant inertia weight (how much to weigh the previous velocity)	(0.4 for random (fitness 4))
		double c1=Double.parseDouble(System.getProperty("var3"));        // cognative constant <--mYB 1?		(1.5 for random (fitness 4))
		double c2=Double.parseDouble(System.getProperty("var4"));        // social constant					(1.5 for random (fitness 4))

		for(int i=0; i < 10; i++) {
			double r1 = Math.random();
			double r2 = Math.random();

			double velCognitive = c1*r1*(this.pBest[i]-this.position[i]);
			double velSocial = c2*r2*(pos_best_g[i]-this.position[i]);
			this.velocity[i]  = w*this.velocity[i]+velCognitive+velSocial;
		}

	}


	public void update_position() {

		for(int i =0; i < 10; i++) {
			this.position[i] = this.position[i]+this.velocity[i];

			if(this.position[i]>5) {
				this.position[i] = 5;
			}

			if(this.position[i] < -5) {
				this.position[i] = -5;
			}
		}




	}
}
