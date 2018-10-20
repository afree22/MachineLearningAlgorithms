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
		try {
			if (options.length <= 1) {
				Exception e = new Exception("not enough arguments");
				throw e;
			} else {
				if (options.length == 2) {
					if (options[0].equals("-t") && options[1] != null) {
						train.load(options[1]);
					}
					else
					{
						System.out.println("Please enter valid training or testing options");
					}
				}

				if (options.length == 3) {
					if (options[2].equals("-T")) {
						System.out.println("In order to run testing data you must enter a filename");
						System.out.println("Running the training data now");
						System.out.println("");
					}
				}
				if (options.length == 4) {
					if (options[2].equals("-T") && options[3] != null) {
						test.load(options[3]);
					}
					else
					{
						System.out.println("Please enter valid training or testing options");
					}
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String toString() {
		String toReturn = "";
		if (train.name.equals("?z?z?z?")) {
			// training dataset never populated;
		} else {
			toReturn = train.toString();
			if (test.name != "?z?z?z?") {
				toReturn += "\n\n" + test.toString();
			}
		}
		return toReturn;
	}
}
