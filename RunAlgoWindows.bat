javac -cp contest.jar;"commons-math3-3.6.1.jar" player44.java Population.java Individual.java CrossOver.java Mutation.java SelectionMethods.java

jar uf contest.jar Individual.class
jar uf contest.jar Population.class
jar uf contest.jar CrossOver.class 
jar uf contest.jar Mutation.class 
jar uf contest.jar SelectionMethods.class

jar cmf MainCLass.txt submission.jar player44.class Population.class Individual.class CrossOver.class Mutation.class SelectionMethods.class

java -jar testrun.jar >storeresults.txt -submission=player44 -evaluation=BentCigarFunction -seed=1
@pause