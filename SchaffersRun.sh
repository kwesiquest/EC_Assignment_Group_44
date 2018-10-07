javac -cp contest.jar player44.java Population.java Individual.java CrossOver.java Mutation.java SelectionMethods.java Particle.java

jar cmf MainClass.txt submission.jar player44.class Population.class Individual.class CrossOver.class Mutation.class SelectionMethods.class Particle.class

java -Dvar1=2000 -Dvar2=0.729 -Dvar3=2.041 -Dvar4=0.948 -jar testrun.jar >storeresults.txt -submission=player44 -evaluation=SchaffersEvaluation -seed=1
