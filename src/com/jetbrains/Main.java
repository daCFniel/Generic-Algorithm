package com.jetbrains;

public class Main {

    public static void main(String[] args) {
        //Do not delete/alter the next line
        long startT=System.currentTimeMillis();

        //Edit this according to your name and login
        String name="Daniel Bielech";
        String login = "db662";

        System.out.println(" ");
        // CONSTANTS
        final int POPULATION_SIZE = 3000;
        final double MUTATION_RATE = 0.01;

        // Create initial population
        Population population = new Population(POPULATION_SIZE, MUTATION_RATE);
        population.fillPopulation();

        while(!population.isFinished()) {
            population.calculateFitness();
            System.out.println(population.getTopSolution());
            population.invertFitnessValues();
            population.calculateFitnessSum();
            population.createNewPopulation();
        }

        //get the fitness for a candidate solution in problem 1 like so
//        double fit = population.getPopulation()[0].getFitness();
//
//
//        System.out.println("First problem");
//        System.out.println(population.getPopulation()[0]);
//        System.out.println("The fitness of your example Solution is: "+ fit);
//        System.out.println(" ");
        // Now let us turn to the second problem
        // A sample solution in this case is a boolean array of size 100
        // Create a random sample solution and get the weight and utility

        //Creating a sample solution for the second problem
        //The higher the fitness, the better, but be careful of  the weight constraint!
//        boolean[] sol2 = new boolean[100];
//        for(int i=0;i< sol2.length; i++){
//            sol2[i]= (Math.random()>0.5);
//        }
//
//        //Now checking the fitness of the candidate solution
//        double[] tmp =(Assess.getTest2(sol2));
//
//        //The index 0 of tmp gives the weight. Index 1 gives the utility
//        System.out.println("Second problem");
//        System.out.println("The weight is: " + tmp[0]);
//        System.out.println("The utility is: " + tmp[1]);
//        System.out.println(" ");
//
//        //Once completed, your code must submit the results you generated, including your name and login:
//        //Use and adapt the function below:
//        //Assess.checkIn(name,login,sols[0],sol2);
//
        //Do not delete or alter the next line
        long endT= System.currentTimeMillis();
        System.out.println("Total execution time was: " +  ((endT - startT)/1000.0) + " seconds");
    }
}
