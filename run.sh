#!/usr/bin/env bash

#forloops for gridsearch of parameters
for i in {1..30};
do for cc in 15 20 25;
do for sc in 15 20 25;
do for p in 30 100;
do for iw in 4 8;
do javac -cp contest.jar player44.java CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java Island.java; 
jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class Island.class;
java -DpopSize="$p" -DinertiaWeight="$iw" -DcognativeConstant="$cc" -DsocialConstant="$sc" -DnPopulations=1 -jar testrun.jar -submission=player44 -evaluation=KatsuuraEvaluation -seed=1 > analyze_results/storeresults_i"$i"_cc"$cc"_sc"$sc"_p"$p".txt; 
done; done; done; done; done;

#single try
javac -cp contest.jar player44.java CrossOver.java Individual.java Mutation.java Population.java SelectionMethods.java Island.java; 
jar cmf MainClass.txt submission.jar player44.class CrossOver.class Individual.class Mutation.class Population.class SelectionMethods.class Island.class;
java -DpopSize=10 -DnPopulations=50 -DinertiaWeight=4 -DcognativeConstant=20 -DsocialConstant=25 -jar testrun.jar -submission=player44 -evaluation=KatsuuraEvaluation -seed=1 > analyze_results/storeresults_pso_mutation.txt; 


