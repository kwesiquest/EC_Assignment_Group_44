javac -cp contest.jar player44.java Population.java Individual.java CrossOver.java Mutation.java SelectionMethods.java Particle.java

jar cmf MainClass.txt submission.jar player44.class Population.class Individual.class CrossOver.class Mutation.class SelectionMethods.class Particle.class

java -Dvar1=300 -Dvar2=0.3 -Dvar3=1.0 -Dvar4=1.5 -jar testrun.jar >storeresults.txt -submission=player44 -evaluation=BentCigarFunction -seed=1
