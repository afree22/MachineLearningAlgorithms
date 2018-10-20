
/*
 * IBk.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */
import java.io.Serializable;

public class IBk extends Classifier implements Serializable, OptionHandler {
	protected DataSet dataset;
	protected int k;

	public IBk() {
		dataset = new DataSet();
		k = 3;
	}

	public IBk(String[] options) throws Exception {
		dataset = new DataSet();
		k = 3;
		this.setOptions(options);
	}

	public Performance classify(DataSet ds) throws Exception {
		Performance p1 = new Performance(ds.getAttributes());
		Examples examples = ds.getExamples();
		for (int i = 0; i < examples.size(); i++) {
			double predicted = classify(examples.get(i));
			double actual = examples.get(i).get(ds.getAttributes().getClassIndex());
			p1.add(actual, predicted);
		}
		return p1;
	}

	public int classify(Example query) throws Exception {
		double[] labels = new double[k]; // holds the labels for nearest k neighbors
		double[] distances = new double[k]; // holds the distances for nearest k neighbors
		int classIdx = dataset.getAttributes().getClassIndex();
		int numClassClassify = dataset.getAttributes().getClassAttribute().size();
		// Initialize to 0
		for (int i = 0; i < k; i++) {
			distances[i] = Double.MAX_VALUE;
			labels[i] = 0;
		}

		// go through each of the examples
		for (int i = 0; i < dataset.getExamples().size(); i++) {
			Example e1 = dataset.getExamples().get(i);
			double distance = 0.0;
			// calculate distance at each attribute;
			for (int j = 0; j < dataset.getAttributes().size(); j++) {
				if (dataset.getAttributes().getClassIndex() == j) {
					// skip
				} else {
					if (query.get(j).equals(e1.get(j))) {
						distance += 0;
					} else {
						distance += 1;
					}
				}
			}
			int indexMax = Utils.maxIndex(distances);

			if (distance < distances[indexMax]) {
				distances[indexMax] = distance;
				labels[indexMax] = e1.get(classIdx);
			}
		} // end for loop

		// System.out.println("New Classifying of an Example");
		// for (int i = 0; i < k; i++) {
		// System.out.println("Labels: " + labels[i] + " Distances: " + distances[i]);
		// }

		// Find the majority class in labels[]
		int maxCount = 0;
		int maxClassIdx = -1;
		for (int i = 0; i < k; i++) {
			int count = 0;
			for (int j = 0; j < k; j++) {
				if (labels[i] == labels[j])
					count++;
			}
			if (count > maxCount) {
				maxCount = count;
				maxClassIdx = i;
			}
		}
		return (int) labels[maxClassIdx];
	}

	public void train(DataSet ds) throws Exception {
		this.dataset = ds;
	}

	public Classifier clone() {
		Classifier copyIBK = (Classifier) Utils.deepClone(this);
		return copyIBK;
	}

	public void setK(int k1) {
		this.k = k1;
	}

	public void setOptions(String args[]) throws Exception {
		// arguments must be in pairs
		if (args.length % 2 != 0) {
			System.out.println("Incorrect argument pairings");
			throw new Exception("Incorrect Arguments");
		} else {
			for (int i = 0; i < args.length; i = i + 2) {
				if (args[i].equals("-k")) {
					// get the value of the next integer
					k = Integer.valueOf(args[i + 1]);
				}
			}
		}
	}

	// Main in IBk
	public static void main(String[] args) {
		try {
			Evaluator evaluator = new Evaluator(new IBk(), args);
			Performance performance = evaluator.evaluate();
			System.out.println(performance);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
