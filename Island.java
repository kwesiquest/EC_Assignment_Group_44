import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Island {

	public List<Population> populations;
	public int migrationRate;
	public int migrationSize;
	public int generations = 0;
	private List<Individual> migratingIndividuals;

	SelectionMethods select;
	

	//CONSTRUCTOR	
	public Island(int migrationRate, int migrationSize) {
		select = new SelectionMethods();
		this.populations = new ArrayList<Population>();

		this.migrationRate = migrationRate;
		this.migrationSize = migrationSize;
	}

	//METHODS
	// migrate

	public void addPopulation(Population population){
		this.populations.add(population);
	}

	public void migrate(){
		if(generations % migrationRate == 0 && generations > 0){
			Population population1 = this.populations.get(0);
			Population population2 = this.populations.get(1);

			migratingIndividuals = new ArrayList<Individual>();
			migratingIndividuals = select.GetFittestIndividuals(5, population1);
			population2.addIndividuals(migratingIndividuals);
			population1.removeIndividuals(migratingIndividuals);

			migratingIndividuals = new ArrayList<Individual>();
			migratingIndividuals = select.GetFittestIndividuals(5, population2);
			population1.addIndividuals(migratingIndividuals);
			population2.removeIndividuals(migratingIndividuals);
		}
	}



}
