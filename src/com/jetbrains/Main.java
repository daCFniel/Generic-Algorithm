package com.jetbrains;

public class Main {

    public static void main(String[] args) {
        //Do not delete/alter the next line
        long startT = System.currentTimeMillis();

        //Edit this according to your name and login
        String name = "Daniel Bielech";
        String login = "db662";
        System.out.println(" ");


        // PROBLEM 1 /////////////////////////////////////
        // CONSTANTS
        final int POPULATION_SIZE = 2000;
        final double MUTATION_RATE = 0.05;

        // Create initial population
        Population population = new Population(POPULATION_SIZE, MUTATION_RATE);
        population.fillPopulation();

        // Main loop for GA to reproduce
        while (!population.isFinished()) {
            population.getTopSolution();
            population.checkIfFinished();
            population.createNewPopulation();
        }


        /// PROBLEM 2 ////////////////////////////////////
        // CONSTANTS
        final int POPULATION_SIZE2 = 2000;
        final double MUTATION_RATE2 = 0.01;

        // Create initial population and calculate fitness of first generation
        Population2 population2 = new Population2(POPULATION_SIZE2, MUTATION_RATE2);
        population2.fillPopulation();

        // Main loop for GA to reproduce
        while (!population2.isFinished()) {
            population2.getTopSolution();
            population2.checkIfFinished();
            // create new population and calculate its fitness
            population2.createNewPopulation();
        }

        Assess.checkIn(name, login, population.getBestCandidate().getElements(), population2.getBestCandidate().getElements());

        //Do not delete or alter the next line
        long endT = System.currentTimeMillis();
        System.out.println("Total execution time was: " + ((endT - startT) / 1000.0) + " seconds");
    }
}
