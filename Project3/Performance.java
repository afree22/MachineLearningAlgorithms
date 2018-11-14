/*
 * Performance.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class Performance extends Object {
	private Attributes attributes;
	private int[][] confusionMatrix;
	private int corrects = 0;
	private double sum = 0.0;
	private double sumSqr = 0.0;
	private int c; // number of classes
	private int n = 0; // number of predictions
	private int m = 0;

	public Performance(Attributes a1) throws Exception {
		this.attributes = a1;

		// set number of classes
		c = attributes.getClassAttribute().size();

		// create confusion matrix and fill with zeros
		confusionMatrix = new int[c][c];
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < c; j++) {
				confusionMatrix[i][j] = 0;
			}
		}
	}

	public void add(int actual, int prediction) {
		n++; // increment number of predictions

		confusionMatrix[actual][prediction]++; // increment confusionMatrix at spot

		if (actual == prediction) {
			corrects++; // increment number correct
		}

	}

	public void add(double actual, double prediction) {
		int actualInt = Double.valueOf(actual).intValue(); // convert to int
		int predInt = Double.valueOf(prediction).intValue(); // convert to int

		this.add(actualInt, predInt);
	}

	public void add(Performance p) throws Exception {
		this.corrects += p.corrects;
		this.sum += p.getAccuracy();
		this.sumSqr += p.getAccuracy() * p.getAccuracy();
		this.m++;
		this.n += p.n;

		// add values from p confusionMatrix
		for (int i = 0; i < this.confusionMatrix.length; i++) {
			for (int j = 0; j < this.confusionMatrix[i].length; j++) {
				this.confusionMatrix[i][j] += p.confusionMatrix[i][j];
			}
		}
	}

	public double getAccuracy() {
		// Accuracy = # correct / all observations
		// n > 0 signals that it is coming out of classify, have at least one prediction
		if (n > 0) {
			return ((double) corrects) / n;
		} else if (m > 0) {
			return sum / m;
		}

		return 0;
	}

	public double getSDAcc() {
		// M > 0 signifys that it is an accumulating performance object
		// have at least one addition to the performance set
		if (m > 0) {
			double variance = (sumSqr - (Math.pow(sum, 2) / m)) / (m - 1.0);
			double sd = Math.sqrt(variance);
			return sd;
		}
		return 0;
	}

	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("Accuracy: ");
		toReturn.append(this.getAccuracy());
		toReturn.append("\n");
		toReturn.append("SD: ");
		toReturn.append(this.getSDAcc());
		toReturn.append("\n");

		return toReturn.toString();
	}
}
