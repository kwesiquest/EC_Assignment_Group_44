# EC_Assignment_Group_44

### step 0: copy files (player44.java, Individual.java, Population.java) into assignmentfiles_2017

### step 1: Paste in cmd
javac -cp contest.jar player44.java Population.java Individual.java CrossOver.java Mutation.java SelectionMethods.java

### For Windows extra step ###
jar uf contest.jar Individual.class 
jar uf contest.jar Population.class
jar uf contest.jar CrossOver.class 
jar uf contest.jar Mutation.class 
jar uf contest.jar SelectionMethods.class 

### step 2: Paste in cmd
jar cmf MainClass.txt submission.jar player44.class Population.class Individual.class CrossOver.class Mutation.class SelectionMethods.class

### step 3: Paste in cmd
java -jar testrun.jar >storeresults.txt -submission=player44 -evaluation=BentCigarFunction -seed=1


# parameter tuning

### step 0: name parameters in the files "iw", "cc", "sc" and "popSize" and divide the floats by 10: 
double w = Double.parseDouble(System.getProperty("iw"))/10;      
double c1=Double.parseDouble(System.getProperty("cc"))/10; 
double c2=Double.parseDouble(System.getProperty("sc"))/10;
Integer.parseInt(System.getProperty("popSize"));

### step 1: paste in terminal
for iteration in {1..30};
do for cc in 15 20 25; 
do for sc in 15 20 25; 
do for p in 30 100; 
do for iw in 4 8; 
do javac -cp contest.jar player44.java CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java Particle.java;  
jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class Particle.class; 
java -DpopSize="$p" -Diw="$iw" -Dcc="$cc" -Dsc="$sc" -jar testrun.jar -submission=player44 -evaluation=KatsuuraEvaluation -seed=1 > analyze_results/storeresults_i"$iteration"_cc"$cc"_sc"$sc"_p"$p".txt;  
done; done; done; done; done;

### step 2: run python analysis file over all outputs
cd analyze_results; python3 return_scores_over_all_runs.py

### step 3: check results
results_param_combi_tuning.txt

