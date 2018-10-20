import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.lang.*;
import java.nio.charset.StandardCharsets;

/*
 * DataSet.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class DataSet extends Object implements Serializable {
	protected String name;
	protected Attributes attributes;
	protected Examples examples;
	protected Random random;

	protected int folds; // new
	protected int[] partitions; // new

	public DataSet() {
		name = "";
		attributes = new Attributes();
		examples = new Examples(attributes);
		random = new Random();
		folds = 10; // new
		partitions = null; // new
	}

	public DataSet(Attributes attributes1) {
		name = "";
		attributes = attributes1;
		examples = new Examples(attributes1);
		random = new Random();
		folds = 10;
		partitions = null;
	}

	public TrainTestSets getCVSets(int p) throws Exception {
		partitions = new int[examples.size()];

		for (int i = 0; i < examples.size(); i++) {
			partitions[i] = random.nextInt(folds);
		}
		// create TrainTestSets object to return
		TrainTestSets tts = new TrainTestSets();

		// Initialize the train and test dataset of the object
		DataSet train = new DataSet(attributes);
		DataSet test = new DataSet(attributes);
		train.setFolds(folds);
		test.setFolds(folds);

		// Randomly select examples for the training and test sets
		for (int i = 0; i < examples.size(); i++) {
			if (partitions[i] == p) {
				test.add(examples.get(i));
			} else {
				train.add(examples.get(i));
			}
		}

		// set training and testing set for the TrainTestSets Object
		tts.setTestingSet(test);
		tts.setTrainingSet(train);

		return tts;
	}

	public int getFolds() {
		return folds; // this is a new function in P2
	}

	public void setFolds(int f) throws Exception {
		folds = f; // this is a new function in P2
	}

	public void add(Example example1) {
		// Adds the specified example to this data set.
		examples.add(example1);
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public Examples getExamples() {
		return examples;
	}

	public boolean getHasNumericAttributes() {
		return attributes.getHasNumericAttributes();
	}

	public void load(String filename) throws Exception {
		File file = new File(filename);
		try {
			Scanner sc = new Scanner(file);
			parse(sc);
			sc.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void setRandom(Random random1) {
		random = random1;
	}

	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("@dataset ");
		toReturn.append(name);
		toReturn.append("\n\n");
		toReturn.append(this.attributes.toString());
		toReturn.append("\n");
		toReturn.append(this.examples.toString());
		return toReturn.toString();
	}

	private void parse(Scanner scanner) throws Exception {
			Attributes scannerAttributes = new Attributes();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine(); // read in the line
				String[] words = line.split(" ");
				if (line.startsWith("@dataset")) {
					name = words[1];
				} else if (line.startsWith("@attribute")) {
					scannerAttributes
							.parse(new Scanner(new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8))));
					this.attributes = scannerAttributes;
				} else if (line.startsWith("@examples")) {
					this.examples = new Examples(this.attributes);
					// this.examples.parse(new Scanner(new
					// ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8))));
					this.examples.parse(scanner);
				} else if (line.trim().equals("")) {
					// skip line
				} else {

				}
			} // end of while
	} // End of Parse function

	public static void main(String[] args) {
		try {
			DataSet ds = new DataSet();
			ds.load("bikes-nominal.mff");
			System.out.println(ds.toString());
			System.out.println(ds.getExamples().get(0));

			// System.out.println(ds.getExamples().get(1).get(4));
			// System.out.println(ds.getExamples().get(1).set(4, 150.0));
			// System.out.println(ds.getExamples().get(1).get(4));
			// System.out.println(ds.toString());
			// javac DataSet.java Attribute.java AttributeFactory.java Attributes.java
			// Example.java Examples.java NominalAttribute.java NumericAttribute.java
			// OptionHandler.java TrainTestSets.java
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// End of main
} // End of class
