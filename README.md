# EC_Assignment_Group_44

### step 0: copy files (player44.java, Individual.java, Population.java) into assignmentfiles_2017

### step 1: Paste in cmd
javac -cp contest.jar player44.java Population.java Individual.java

### For Windows extra step ###
jar uf contest.jar Individual.class 
jar uf contest.jar Population.class 

### step 2: Paste in cmd
jar cmf MainCLass.txt submission.jar player44.class Population.class Individual.class

### step 3: Paste in cmd
java -jar testrun.jar >storeresults.txt -submission=player44 -evaluation=BentCigarFunction -seed=1


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
