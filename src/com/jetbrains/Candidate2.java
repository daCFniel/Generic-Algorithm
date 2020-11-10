package com.jetbrains;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Candidate2 {
    private int size = 100; // number of items in the sack
    private boolean[] elements;
    private double[] fitness;  // The index 0 of fitness gives the weight. Index 1 gives the utility.

    public Candidate2() {
        elements = new boolean[size];
    }

    public void setElements() {
        for (int i = 0; i < size; i++) {
            elements[i] = newElement();
        }
    }

    public boolean newElement() {
        return Math.random()>0.5;
    }

    public Candidate2 crossover(Candidate2 partner, int splitPoint) {
        Candidate2 newCandidate = new Candidate2();
        for (int i = 0; i < size; i++) {
            if (i < splitPoint) {
                newCandidate.elements[i] = elements[i];
            } else {
                newCandidate.elements[i] = partner.elements[i];
            }
        }
        newCandidate.calculateFitness();
        return newCandidate;
    }

    public void mutate(double mutationRate) {
        if (Math.random() < 0.8) { // 80% chance to mutate by flipping the value
            for (int i = 0; i < size; i++) {
                if (Math.random() < mutationRate) {
                    if (fitness[0] > 500) {
                        elements[i] = false;
                    } else if (fitness[0] < 500) {
                        elements[i] = true;
                    }
                }
            }
        } else { // 20% chance to mutate by swapping two values
            if (Math.random() < mutationRate) {
                int r1 = ThreadLocalRandom.current().nextInt(0, size);
                int r2 = ThreadLocalRandom.current().nextInt(0, size);
                while (r1 == r2) {
                    r2 = ThreadLocalRandom.current().nextInt(0, size);
                }
                boolean temp = elements[r1];
                elements[r1] = elements[r2];
                elements[r2] = temp;
            }
        }
    }

    public void calculateFitness() {
        fitness = Assess.getTest2(elements);
        if (fitness[0] > 500) {
            fitness[1] = 0;
        }
    }

    public double[] getFitness() {
        return this.fitness;
    }

    public boolean[] getElements() {
        return this.elements;
    }

    public String toString() {
        return Arrays.toString(this.elements);
    }
}
