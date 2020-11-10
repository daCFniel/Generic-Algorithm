package com.jetbrains;

import java.lang.Math;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Candidate implements  Comparable<Candidate>{
    private int size = 20; // number of dials on black box
    private double[] elements;
    private double fitness;

    public Candidate() {
        elements = new double[size];
    }

    public int compareTo(Candidate other) {
        if(this.getFitness() > other.getFitness())
            return 1;
        else if (this.getFitness() == other.getFitness())
            return 0 ;
        return -1 ;
    }

    public void setElements() {
        for (int i = 0; i < size; i++) {
            elements[i] = newElement();
        }
    }

    public double newElement() {
        return ThreadLocalRandom.current().nextDouble(-5.0, 5.0);
    }

    public Candidate crossover(Candidate partner, int splitPoint) {
        Candidate newCandidate = new Candidate();
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
        if (Math.random() < 0.5) { // 50% chance to mutate by adjusting the dial value
            for (int i = 0; i < size; i++) {
                if (Math.random() < mutationRate+fitness) {
                    // adjust dial value according to current fitness
                    // as candidate's fitness gets better, the mutation is becoming less effective
                    if (fitness >= 1.0) {
                        elements[i] = elements[i] = ThreadLocalRandom.current().nextDouble(elements[i] - Math.random() / 100, elements[i] + Math.random() / 100);
                    } else if (1e-5 < fitness && fitness < 1.0) {
                        elements[i] = ThreadLocalRandom.current().nextDouble(elements[i] - Math.random() / 100000, elements[i] + Math.random() / 100000);
                    } else if (1e-10 < fitness && fitness < 1e-5) {
                        elements[i] = ThreadLocalRandom.current().nextDouble(elements[i] - Math.random() / 1000000, elements[i] + Math.random() / 1000000);
                    } else if (1e-15 < fitness && fitness < 1e-10) {
                        elements[i] = ThreadLocalRandom.current().nextDouble(elements[i] - Math.random() / 10000000, elements[i] + Math.random() / 10000000);
                    } else {
                        elements[i] = ThreadLocalRandom.current().nextDouble(elements[i] - Math.random() / 100000000, elements[i] + Math.random() / 100000000);
                    }

                }
            }
        } else { // 50% chance to mutate by copying one dial value to other dial
            if (Math.random() < mutationRate+fitness) {
                int r1 = ThreadLocalRandom.current().nextInt(0, size);
                int r2 = ThreadLocalRandom.current().nextInt(0, size);
                while (r1 == r2) {
                    r2 = ThreadLocalRandom.current().nextInt(0, size);
                }
                elements[r1] = elements[r2];
            }
        }
    }

    public void calculateFitness() {
        fitness = Assess.getTest1(elements);
    }

    public double getFitness() {
        return this.fitness;
    }

    public double[] getElements() {
        return this.elements;
    }

    public String toString() {
        return Arrays.toString(this.elements);
    }
}
