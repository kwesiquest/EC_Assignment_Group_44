# EC_Assignment_Group_44

### step 0: copy files (player44.java, Individual.java, Population.java) into assignmentfiles_2017

### step 1: Paste in cmd
javac -cp contest.jar player44.java CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java Island.java 

### For Windows extra step ###
jar uf contest.jar Individual.class 
jar uf contest.jar Population.class
jar uf contest.jar CrossOver.class 
jar uf contest.jar Mutation.class 
jar uf contest.jar SelectionMethods.class 
jar uf contest.jar Island.class 

### step 2: Paste in cmd
jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class Island.class;


### step 3: Paste in cmd
java -DpopSize=10 -DnPopulations=50 -DinertiaWeight=4 -DcognativeConstant=20 -DsocialConstant=25 -jar testrun.jar -submission=player44 -evaluation=KatsuuraEvaluation -seed=1 > analyze_results/storeresults_pso_mutation.txt; 


# parameter tuning (Example)

for i in {1..30};
do for cc in 15 20 25;
do for sc in 15 20 25;
do for p in 30 100;
do for iw in 4 8;
do javac -cp contest.jar player44.java CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java Island.java; 
jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class Island.class;
java -DpopSize="$p" -DinertiaWeight="$iw" -DcognativeConstant="$cc" -DsocialConstant="$sc" -DnPopulations=1 -jar testrun.jar -submission=player44 -evaluation=KatsuuraEvaluation -seed=1 > analyze_results/storeresults_i"$i"_cc"$cc"_sc"$sc"_p"$p".txt; 
done; done; done; done; done;


# https://docs.google.com/document/d/1BEhSlbOyf5n7zEDs93WLa7zZ0y41FEYdjFyq24QEm_I/edit






Evolutionary Algorithm Cycle:

Initialise population
Determine fitness of all individuals in this population

while(programrunning = true){
  
  Select parents who you wish to make children with
  
  Make child(ren) from those selected parents by mixing parent's genotype together
  
  Mutate children by altering their genotype
  
  Determine fitness of all the new children
  
  Determine which individuals of the population (or children) you wish to keep
  
}
