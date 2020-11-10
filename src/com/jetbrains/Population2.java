package com.jetbrains;

import java.util.concurrent.ThreadLocalRandom;

public class Population2 {
    private double mutationRate;
    private int popSize;
    private int generation; // number of current generation
    private boolean finished;
    private Candidate2[] population;
    double bestFitness;
    double bestFitnessWeight;
    Candidate2 bestCandidate;
    int goodSolution;


    public Population2(int popSize, double mutationRate) {
        population = new Candidate2[popSize];
        this.mutationRate = mutationRate;
        this.popSize = popSize;
        generation = 1;
        bestFitness = Double.NEGATIVE_INFINITY;
        goodSolution = 209;
    }

    // fill population with new random elements
    // calculate their fitness
    public void fillPopulation() {
        for (int i = 0; i < popSize; i++) {
            Candidate2 candidate = new Candidate2();
            candidate.setElements();
            candidate.calculateFitness();
            population[i] = candidate;
        }
    }

    // pick one candidate between two random
    // the one with better fitness wins
    public Candidate2 tournamentSelection() {
        Candidate2 candidate1 = population[ThreadLocalRandom.current().nextInt(0, popSize)];
        Candidate2 candidate2 = population[ThreadLocalRandom.current().nextInt(0, popSize)];

        while (candidate1 == candidate2) {
            candidate2 = population[ThreadLocalRandom.current().nextInt(0, popSize)];
        }

        if (candidate1.getFitness()[1] > candidate2.getFitness()[1]) {
            return candidate1;
        } else {
            return candidate2;
        }
    }

    public Candidate2 getCandidate() {
        return tournamentSelection();
    }

    // reproduction process
    public void createNewPopulation() {
        Candidate2[] newPopulation = new Candidate2[popSize];
        for (int i = 0; i < popSize; i += 2) {
            Candidate2 candidate1 = getCandidate(); // selection
            Candidate2 candidate2 = getCandidate();
            while (candidate1 == candidate2) {
                candidate2 = getCandidate();
            }
            int splitPoint = ThreadLocalRandom.current().nextInt(0, 100);
            Candidate2 newCandidate1 = candidate1.crossover(candidate2, splitPoint); // crossover
            Candidate2 newCandidate2 = candidate2.crossover(candidate1, splitPoint); // crossover
            newCandidate1.mutate(mutationRate); // mutatation
            newCandidate2.mutate(mutationRate); // mutatation
            newPopulation[i] = newCandidate1; // adding to the new population
            newPopulation[i + 1] = newCandidate2; // adding to the new population
        }
        population = newPopulation;
        generation++;
        // escape from local maximum by resetting the population
        if (generation >= 500) {
            resetPopulation();
        }
    }


    public String getTopSolution() {
        for (Candidate2 candidate : population) {
            if (candidate.getFitness()[1] > bestFitness) {
                bestFitness = candidate.getFitness()[1];
                bestFitnessWeight = candidate.getFitness()[0];
                bestCandidate = candidate;
            }
        }
            return "Generation: " + generation + " ___ Best fitness: " + bestFitness + " ___ Weight: " + bestFitnessWeight;
    }

    public void checkIfFinished() {
        if (bestFitness >= goodSolution) {
            for (Candidate2 candidate: population) {
                double[] fitness = Assess.getTest2(candidate.getElements());
                if (fitness[1] >= goodSolution && fitness[0] <= 500) {
                    bestCandidate = candidate;
                    break;
                }
            }
            double[] fitness = Assess.getTest2(bestCandidate.getElements());
            if (fitness[1] >= goodSolution && fitness[0] <= 500) {
                finished = true;
            }
        }
    }

    // fill population with new random elements
    // restore default values for fields
    public void resetPopulation() {
        fillPopulation();
        generation = 1;
        bestFitness = Double.NEGATIVE_INFINITY;
        bestCandidate = new Candidate2();
    }

    public Candidate2 getBestCandidate() {
        return bestCandidate;
    }

    public boolean isFinished() {
        return finished;
    }
}
