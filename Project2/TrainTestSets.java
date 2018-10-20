/*
 * TrainTestSets.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class TrainTestSets implements OptionHandler {

	protected DataSet train; // the train data set
	protected DataSet test; // the test data set

	public TrainTestSets() {
		train = new DataSet();
		test = new DataSet();
	}

	public TrainTestSets(String[] options) throws Exception {
		this();
		setOptions(options);
	}

	public TrainTestSets(DataSet train1, DataSet test1) {
		train = train1;
		test = test1;
	}

	public DataSet getTrainingSet() {
		return train;
	}

	public DataSet getTestingSet() {
		return test;
	}

	public void setTrainingSet(DataSet train1) {
		train = train1;
	}

	public void setTestingSet(DataSet test1) {
		test = test1;
	}

	public void setOptions(String[] options) throws Exception {
		if (options.length < 2) {
			throw new Exception("not enough arguments");
		} else if (options.length % 2 != 0) {
			throw new Exception("Incorrect Argument pairing");
		} else {
			for (int i = 0; i < options.length; i = i + 2) {
				if (options[i].equals("-t")) {
					// load training data set
					train.load(options[i + 1]);
				}
				if (options[i].equals("-T")) {
					// load testing data set
					test.load(options[i + 1]);
				}
			}
		}
	}

	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		if (train.name.equals("")) {
			// training dataset never populated;
		} else {
			toReturn.append(train.toString());
			if (test.name != "") {
				toReturn.append("\n\n" + test.toString());
			}
		}
		return toReturn.toString();
	}
}
