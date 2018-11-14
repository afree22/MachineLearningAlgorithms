
/*
 * DT.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

import java.util.ArrayList;
import java.io.Serializable;

public class DT extends Classifier implements Serializable, OptionHandler {

	protected Attributes attributes;
	protected Node root;

	public DT() {
		root = new Node();
	}

	public DT(String[] options) throws Exception {
		this();
		this.setOptions(options);
	}

	// need this method because DT extends Classifier
	public Classifier clone() {
		Classifier copyDT = (Classifier) Utils.deepClone(this);
		return copyDT;
	}
	
	public Performance classify(DataSet ds) throws Exception {
		Performance p1 = new Performance(ds.getAttributes());
		int classIndex = ds.getAttributes().getClassIndex();

		for (int i = 0; i < ds.getExamples().size(); i++) {
			Example e1 = ds.getExamples().get(i);
			double actual = e1.get(classIndex);
			int predicted = this.classify(e1);
			p1.add((int) actual, predicted);
		}
		return p1;
	}

	public int classify(Example example) throws Exception {
		Node n1 = root;
		while (n1.isLeaf() == false) {
			int attributeSplit = n1.attribute;
			double exampleVal = example.get(attributeSplit);
			n1 = n1.children.get((int) exampleVal);
		}
		return n1.label;
	}

	public void train(DataSet ds) throws Exception {
		root = train_aux(ds);
	}

	// private recursive method -- builds decision tree
	private Node train_aux(DataSet ds) throws Exception {
		// stopping condition -- homogenous dataset or less than 4 examples
		if (ds.homogeneous() || (ds.getExamples().size() <= 3)) {
			Node n1 = new Node();
			n1.label = ds.getMajorityClassLabel();
			return n1;
		}

		// Split on the best splitting attribute
		ArrayList<DataSet> nodesFromSplit = ds.splitOnAttribute(ds.getBestSplittingAttribute());
		Node splitNode = new Node();
		splitNode.label = ds.getMajorityClassLabel();
		splitNode.attribute = ds.getBestSplittingAttribute();

		for (int i = 0; i < nodesFromSplit.size(); i++) {
			if (nodesFromSplit.get(i).isEmpty()) {
				Node splitNodeChild = new Node();
				splitNodeChild.label = ds.getMajorityClassLabel();
				splitNodeChild.attribute = ds.getBestSplittingAttribute();
				splitNode.children.add(splitNodeChild);
			} else {
				splitNode.children.add(train_aux(nodesFromSplit.get(i)));
			}
		}
		return splitNode;
	}

	public void setOptions(String[] options) throws Exception {
		// arguments must be in pairs
		if (options.length % 2 != 0) {
			System.out.println("Incorrect argument pairings");
			throw new Exception("Incorrect Arguments");
		}
	}

	// Main in DT
	public static void main(String[] args) {
		try {
			Evaluator evaluator = new Evaluator(new DT(), args);
			Performance performance = evaluator.evaluate();
			System.out.println(performance);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}