package com.jetbrains;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
    private double mutationRate;
    private int popSize;
    private int generation; // number of current generation
    private double goodSolution;
    private boolean finished;
    private Candidate[] population;
    double bestFitness; // current best fitness
    Candidate bestCandidate; // current best candidate

    public Population(int popSize, double mutationRate) {
        population = new Candidate[popSize];
        this.mutationRate = mutationRate;
        this.popSize = popSize;
        generation = 1;
        goodSolution = 0.0;
        bestFitness = Double.POSITIVE_INFINITY;
    }

    // fill population with new random elements
    // calculate their fitness
    public void fillPopulation() {
        for (int i = 0; i<popSize; i++) {
            Candidate candidate = new Candidate();
            candidate.setElements();
            candidate.calculateFitness();
            population[i] = candidate;
        }
    }

    // pick one candidate between two random
    // the one with better fitness wins
    public Candidate tournamentSelection() {
        Candidate candidate1 = population[ThreadLocalRandom.current().nextInt(0, popSize)];
        Candidate candidate2 = population[ThreadLocalRandom.current().nextInt(0, popSize)];

        while (candidate1 == candidate2) {
            candidate2 = population[ThreadLocalRandom.current().nextInt(0, popSize)];
        }

        if (candidate1.getFitness() < candidate2.getFitness()) {
            return candidate1;
        } else {
            return candidate2;
        }
    }

    public Candidate getCandidate() {
        return tournamentSelection();
    }

    // reproduction process
    public void createNewPopulation() {
        Candidate[] newPopulation = new Candidate[popSize];
        for (int i = 0; i < population.length; i+=2) {
            Candidate candidate1 = getCandidate(); // selection
            Candidate candidate2 = getCandidate();
            while (candidate1 == candidate2) {
                candidate2 = getCandidate();
            }
            int splitPoint = ThreadLocalRandom.current().nextInt(0, 20);
            Candidate newCandidate1 = candidate1.crossover(candidate2, splitPoint); // crossover
            Candidate newCandidate2 = candidate2.crossover(candidate1, splitPoint); // crossover
            newCandidate1.mutate(mutationRate); // mutatation
            newCandidate2.mutate(mutationRate); // mutatation
            newPopulation[i] = newCandidate1; // adding to the new population
            newPopulation[i+1] = newCandidate2; // adding to the new population
        }
        // idea of elitism, add to the new population only the solutions
        // that are the best
        // from both new and old population
        Candidate[] populations = new Candidate[popSize*2];
        System.arraycopy(population, 0, populations, 0, popSize);
        System.arraycopy(newPopulation, 0, populations, popSize, popSize);
        Arrays.sort(populations);
        System.arraycopy(populations, 0, population, 0, popSize);
        generation++;
        // escape from local maximum by resetting the population
        if (generation >= 500) {
            resetPopulation();
        }
    }


    public String getTopSolution() {
        for (Candidate candidate: population) {
            if (candidate.getFitness() < bestFitness) {
                bestFitness = candidate.getFitness();
                bestCandidate = candidate;
            }
        }
        return "Generation: "+generation+" ___ Best fitness: "+bestFitness;
    }

    public void checkIfFinished() {
        if (bestFitness == goodSolution) {
            for (Candidate candidate: population) {
                if (Assess.getTest1(candidate.getElements()) == goodSolution) {
                    bestCandidate = candidate;
                    break;
                }
            }
            if (Assess.getTest1(bestCandidate.getElements()) == goodSolution) {
                finished = true;
            }
        }
    }

    // fill population with new random elements
    // restore default values for fields
    public void resetPopulation() {
        fillPopulation();
        generation = 1;
        bestFitness = Double.POSITIVE_INFINITY;
        bestCandidate = new Candidate();
    }

    public Candidate getBestCandidate() {
        return this.bestCandidate;
    }

    public boolean isFinished() {
        return finished;
    }
}
