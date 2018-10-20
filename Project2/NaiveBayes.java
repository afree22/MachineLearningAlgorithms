
/*
 * NaiveBayes.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class NaiveBayes extends Classifier implements Serializable, OptionHandler {
	protected Attributes attributes;
	protected CategoricalEstimator classDistribution;
	protected ArrayList<ArrayList<Estimator>> classConditionalDistributions;

	public NaiveBayes() {
		attributes = new Attributes();
		classDistribution = new CategoricalEstimator();
		classConditionalDistributions = new ArrayList<ArrayList<Estimator>>();
	}

	public NaiveBayes(String[] options) throws Exception {
		this();
		this.setOptions(options);
	}

	public Performance classify(DataSet dataSet) throws Exception {
		Performance p1 = new Performance(dataSet.getAttributes());

		Example e1 = new Example();
		for (int i = 0; i < dataSet.getExamples().size(); i++) {
			e1 = dataSet.getExamples().get(i);
			double predicted = classify(e1);
			double actual = e1.get(dataSet.getAttributes().getClassIndex());
			p1.add(actual, predicted);
		}
		return p1;
	}

	public int classify(Example example) throws Exception {
		int numClassesClassify = attributes.getClassAttribute().size();
		double[] classify = new double[numClassesClassify]; // holds the probabilities for classes
		double probabilityProduct;

		for (int i = 0; i < numClassesClassify; i++) {
			probabilityProduct = 1.0; // multiply the conditional probability for each attribute
			for (int j = 0; j < attributes.size() - 1; j++) {
				probabilityProduct *= classConditionalDistributions.get(i).get(j).getProbability(example.get(j));
			}
			classify[i] = classDistribution.getProbability(i) * probabilityProduct;
		}

		int classLabel = Utils.maxIndex(classify);
		return classLabel;
	}

	public void train(DataSet dataset) throws Exception {
		// initialize Naive Bayes Variables
		attributes = dataset.getAttributes();
		int numClassesClassify = attributes.getClassAttribute().size();
		int classIdx = dataset.getAttributes().getClassIndex();
		classDistribution = new CategoricalEstimator(numClassesClassify);
		classConditionalDistributions = new ArrayList<ArrayList<Estimator>>();

		// establish the estimators
		for (int i = 0; i < numClassesClassify; i++) {
			classConditionalDistributions.add(new ArrayList<Estimator>());
			for (int j = 0; j < attributes.size() - 1; j++) {
				CategoricalEstimator ce1 = new CategoricalEstimator(attributes.get(j).size());
				classConditionalDistributions.get(i).add(ce1);
			}
		}

		// Go through the examples and their attributes
		// to populate the Categorical Estimators.

		for (int i = 0; i < dataset.getExamples().size(); i++) {
			Example e1 = dataset.getExamples().get(i);
			for (int j = 0; j < attributes.size(); j++) {
				if (j == classIdx) {
					classDistribution.add(e1.get(classIdx));
				} else {
					classConditionalDistributions.get(e1.get(classIdx).intValue()).get(j).add(e1.get(j));
				}
			}
		}
		
	} // end function train()

	public Classifier clone(){
		NaiveBayes copy = (NaiveBayes) Utils.deepClone(this);
		return copy;
	}

	public void setOptions(String[] options) throws Exception {
		if (options.length % 2 != 0) {
			System.out.println("Incorrect argument pairings");
			throw new Exception("Incorrect Arguments");
		}
	}

	// main NaiveBayes
	public static void main(String[] args) {
		try {
			Evaluator evaluator = new Evaluator(new NaiveBayes(), args);
			Performance performance = evaluator.evaluate();
			System.out.println(performance);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
