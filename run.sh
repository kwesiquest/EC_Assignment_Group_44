#!/usr/bin/env bash
#javac -cp contest.jar player44.java Population.java Individual.java CrossOver.java Mutation.java SelectionMethods.java
# jar uf contest.jar Individual.class jar uf contest.jar Population.class jar uf contest.jar CrossOver.class jar uf contest.jar Mutation.class jar uf contest.jar SelectionMethods.class
#jar cmf MainClass.txt submission.jar player44.class Population.class Individual.class CrossOver.class Mutation.class SelectionMethods.class
#java -jar testrun.jar >storeresults.txt -submission=player44 -evaluation=BentCigarFunction -seed=1

for i in {1..100}
do
   javac -cp contest.jar player44.java CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java
   jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class
   java -jar testrun.jar -submission=player44 -evaluation=SphereEvaluation -seed=1 > results/storeresults"$i".txt
done
