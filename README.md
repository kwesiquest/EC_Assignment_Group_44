# EC_Assignment_Group_44

### step 0: copy files (player44.java, Individual.java, Population.java) into assignmentfiles_2017

### step 1: Paste in cmd
javac -cp contest.jar:commons-math3-3.6.1.jar:. player44.java  CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java

### For Windows extra step ###
jar uf contest.jar Individual.class 
jar uf contest.jar Population.class
jar uf contest.jar CrossOver.class 
jar uf contest.jar Mutation.class 
jar uf contest.jar SelectionMethods.class 

### step 2: Paste in cmd
jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class ./org/

### step 3: Paste in cmd
java -jar submission.jar -submission=player44 -evaluation=SchaffersEvaluation -seed=1 > analyze_results/storeresults_schaffers_cma.txt

