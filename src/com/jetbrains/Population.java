package com.jetbrains;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
    private double mutationRate;
    private double totalFitness;
    private int popSize;
    private int generation;
    private int perfectSolution;
    private boolean finished;
    private Candidate[] population;

    public Population(int popSize, double mutationRate) {
        population = new Candidate[popSize];
        this.mutationRate = mutationRate;
        this.popSize = popSize;
        this.generation = 1;
        this.perfectSolution = 0;
    }

    public void fillPopulation() {
        for (int i = 0; i<popSize; i++) {
            Candidate candidate = new Candidate();
            candidate.setElements();
            population[i] = candidate;
        }
    }

    // invert the values so that higher fitness is better
    public void invertFitnessValues() {
        for (Candidate candidate : population) {
            candidate.setFitness(1 / candidate.getFitness());
        }

//        // normalize fitness to get the value of probability for each candidate
//        for(int i = 0; i < this.population.length; i++) {
//            population[i].setProbability(population[i].getFitness() / totalFitness);
//        }
    }

    public void calculateFitness() {
        for (Candidate candidate : population) {
            candidate.calculateFitness();
        }
    }

    // calculate the sum of fitness values
    public void calculateFitnessSum() {
        for (Candidate candidate : population) {
            totalFitness += candidate.getFitness();
        }
    }

    // pick one candidate from population based on probability
    public Candidate pickOneCandidate() {
        int index = 0;
        // generate random number between 0 and sum of fitness values
        double random = ThreadLocalRandom.current().nextDouble(0, totalFitness);
        while (random > 0) {
            random -= population[index].getFitness();
            index++;
        }
        index--;
        return population[index];
    }

    public void createNewPopulation() {
        Candidate[] newPopulation = new Candidate[popSize];
        for (int i = 0; i < population.length; i++) {
            Candidate candidate1 = pickOneCandidate(); // selection
            Candidate candidate2 = pickOneCandidate();
            Candidate newCandidate = candidate1.crossover(candidate2); // crossover
            newCandidate.mutate(mutationRate); // mutatation
            newPopulation[i] = newCandidate; // adding to the new population
        }
        population = newPopulation;
        setTotalFitness(0.0);
        generation++;
    }

    public void setTotalFitness(double totalFitness) {
        this.totalFitness = totalFitness;
    }

    public String getTopSolution() {
        double bestFitness = Double.POSITIVE_INFINITY;
        for (Candidate candidate: population) {
            if(candidate.getFitness() < bestFitness) {
                bestFitness = candidate.getFitness();
            }
        }
        if (bestFitness == perfectSolution) {
            finished = true;
        }
        return "Generation: "+generation+" ___ Best fitness: "+bestFitness;
    }
    public boolean isFinished() {
        return finished;
    }
}
