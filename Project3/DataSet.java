import java.io.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
/*
 * DataSet.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */


public class DataSet extends Object implements Serializable {
	protected String name;
	protected Attributes attributes;
	protected Examples examples;
	protected Random random;

	protected int folds;
	protected int[] partitions;

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
				scannerAttributes.parse(new Scanner(new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8))));
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

	// ************
	// New functions for the Decision Tree
	// ************

	// This function returns a TTS
	// with p% examples in the train set and (1-p)% examples in the test set
	public TrainTestSets getFoldSet(float p) throws Exception {
		// Shuffle examples using random
		Collections.shuffle(examples, random);

		// Initialize the train and test dataset of the object
		TrainTestSets tts = new TrainTestSets();
		DataSet train = new DataSet(attributes);
		DataSet test = new DataSet(attributes);

		// Select examples (after shuffled) for train and test sets
		for (int i = 0; i < examples.size(); i++) {
			if (i <= Math.round(examples.size() * p)) {
				train.add(examples.get(i));
			} else {
				test.add(examples.get(i));
			}
		}

		// set training and testing set for the TrainTestSets Object
		tts.setTestingSet(test);
		tts.setTrainingSet(train);
		return tts;
	}

	public boolean isEmpty() {
		if (this.getExamples().size() == 0) {
			return true;
		}
		return false;
	}

	public boolean homogeneous() throws Exception {
		boolean homogenous = true;
		double classLabel = this.getExamples().get(0).get(attributes.getClassIndex());
		for (int i = 0; i < this.getExamples().size(); i++) {
			Example e1 = this.getExamples().get(i);
			if (e1.get(attributes.getClassIndex()) != classLabel) {
				homogenous = false;
			}
		}
		return homogenous;
	}

	public int getMajorityClassLabel() throws Exception {
		double[] classCounts = new double[attributes.getClassAttribute().size()];

		for (int i = 0; i < this.getExamples().size(); i++) {
			Example e1 = this.getExamples().get(i);
			classCounts[e1.get(attributes.getClassIndex()).intValue()]++;
		}

		double majorityClass = Utils.maxIndex(classCounts);

		return (int) majorityClass;
	}

	public double gainRatio(int attribute) throws Exception // new
	{
		// *******
		// Get the counts that are required for the calculations
		// *******
		
		int[] classCounts = new int[attributes.getClassAttribute().size()];
		int[] attDomainCount = new int[attributes.get(attribute).size()];
		int[][] classDomainCount = new int[attributes.getClassAttribute().size()][attributes.get(attribute)
				.size()];
		
		for (int i = 0; i < this.getExamples().size(); i++) {
			// for each example, see which class it is in
			double classLabel = this.getExamples().get(i).get(attributes.getClassIndex());
			double attributeLabel = this.getExamples().get(i).get(attribute);
			classCounts[(int)classLabel]++;
			attDomainCount[(int)attributeLabel]++;
			classDomainCount[(int)classLabel][(int)attributeLabel]++;
		}
		

		// *******
		// Calculate the current entropy of the beginning dataset/tree
		// *******

		double currentEntropy = 0.0;
		for (int i = 0; i < attributes.getClassAttribute().size(); i++) {
			double prob = (double)classCounts[i] / (double)examples.size();
			if (prob != 0) {
				currentEntropy += (-1 * prob * (Math.log(prob) / (double) Math.log(2)));
			}
		}

		// *******
		// Calculate the entropies the occur when you split on the attribute
		// *******
		double[] potentialEntropies = new double[attributes.get(attribute).size()];
		double attributeEntropy = 0.0;

		for (int i = 0; i < attributes.get(attribute).size(); i++) {
			attributeEntropy = 0.0;
			for (int j = 0; j < attributes.getClassAttribute().size(); j++) {
				double p1 = 0.0;
				if (attDomainCount[i] == 0) {
					p1 = 0.0;
				} else {
					p1 = classDomainCount[j][i] / (double) attDomainCount[i];
					//p1 = (double) attDomainCount[i]/ (double) examples.size();
					if(p1 != 0)
					{
						attributeEntropy += (-1 * p1 * (Math.log(p1) / (double) Math.log(2)));
					}
				}
			}
			potentialEntropies[i] = (double)attributeEntropy;
		}

		// This loop multiplies the entropy(Si) by Size(new dataset)/size(old data set)
		// The new dataset will contain only examples in one domain
		double totalEntropyLevel = 0.0;
		for (int i = 0; i < attributes.get(attribute).size(); i++) {
			totalEntropyLevel += (attDomainCount[i] / (double) examples.size()) * potentialEntropies[i];
		}

		// *******
		// Calculate the gain
		// *******
		double gain = currentEntropy - totalEntropyLevel;

		// *******
		// Calculate the splitInformation for the attribute
		// *******
		double splitInformation = 0.0;
		for (int i = 0; i < attributes.get(attribute).size(); i++) {
			double p2 = attDomainCount[i] / (double) examples.size();
			if(p2 != 0)
			{
				splitInformation += (-1 * p2 * (Math.log(p2) / (double) Math.log(2)));
			}
		}
		

		double gainRatio = 0.0;
		if (splitInformation == 0.0) {
			gainRatio = 0.0;
		}
		else {
			gainRatio = gain / splitInformation;
		}

//		System.out.println("Starting Entropy: " + currentEntropy);
//		System.out.println("Remaining Entropy:" + totalEntropyLevel);
//		System.out.println("Gain: " + gain);
//		System.out.println("splitInformation: " + splitInformation);
		return gainRatio;
	}
	
	// returns an array of datasets split on the attribute value
	// Use to create the children when building the tree
	public ArrayList<DataSet> splitOnAttribute(int attribute) throws Exception {
		ArrayList<DataSet> children = new ArrayList<DataSet>();
		for (int i = 0; i < attributes.get(attribute).size(); i++) {
			children.add(new DataSet(attributes));
		}
		for (int i = 0; i < this.getExamples().size(); i++) {
			Example e1 = examples.get(i);
			children.get(e1.get(attribute).intValue()).add(e1);
		}
		return children;
	}

	public int getBestSplittingAttribute() throws Exception {
		// Find the attribute with the greatest gainRatio
		double max = Double.MIN_VALUE;
		int index = 0;
		for (int i = 0; i < this.attributes.size(); i++) {
			if (i == attributes.getClassIndex()) {
				// skip because class index
			} else if (this.gainRatio(i) > max) {
				max = this.gainRatio(i);
				index = i;
			}
		}
		return index;
	}

	// ************
	// Main method of DataSet -- Testing purposes only
	// ************
	public static void main(String[] args) {
		try {
			DataSet ds = new DataSet();
			ds.load("bikes-nominal.mff");
//			System.out.println(ds.toString());
			
//			System.out.println("");
//			System.out.println("");
			System.out.println("Checking P3 Functions");
			System.out.println("Gain Ratio of make: " + ds.gainRatio(0));
//			System.out.println("");
//			System.out.println("Gain Ratio of tires: " + ds.gainRatio(1));
//			System.out.println("");
			
			
//			System.out.println("Gain Ratio of bars: " + ds.gainRatio(2));
//			System.out.println("");
//			System.out.println("Gain Ratio of bottles: " + ds.gainRatio(3));
//			System.out.println("");

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
