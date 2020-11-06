package com.jetbrains;

import java.lang.Math;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Candidate {
    private int size = 20; // number of dials on black box
    private double[] elements;
    private double fitness;

    public Candidate() {
        elements = new double[size];
    }

    public void setElements() {
        for (int i = 0; i < size; i++) {
            elements[i] = newElement();
        }
    }

    public double newElement() {
        return Math.random() * Math.round(5.12 * (Math.random() - Math.random()));
    }

    public Candidate crossover(Candidate partner) {
        Candidate newCandidate = new Candidate();
            int midIndex = ThreadLocalRandom.current().nextInt(0, size);
            for (int i = 0; i < size; i++) {
                if (i < midIndex) {
                    newCandidate.elements[i] = elements[i];
                } else {
                    newCandidate.elements[i] = partner.elements[i];
                }
            }
        return newCandidate;
    }

    public void mutate(double mutationRate) {
            for (int i = 0; i < size; i++) {
                if (Math.random() < mutationRate) {
                    elements[i] = newElement();
                }
            }
    }

    public void calculateFitness() {
        fitness = Assess.getTest1(elements);
        fitness = Math.pow(fitness, 4.0);
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public String toString() {
        return Arrays.toString(this.elements);
    }
}
