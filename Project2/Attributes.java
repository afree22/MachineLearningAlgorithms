import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Attributes.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class Attributes extends Object implements Serializable {
	private ArrayList<Attribute> attributes; // List of attributes
	private boolean hasNumericAttributes; // a flag indicating that the data set has one or more numeric attributes
	private int classIndex; // stores the position of the class label

	public Attributes() {
		attributes = new ArrayList<Attribute>();
		hasNumericAttributes = false;
	}

	public void add(Attribute attribute1) {
		attributes.add(attribute1);
	}

	public int getClassIndex() {
		return classIndex;
	}

	public boolean getHasNumericAttributes() {
		return hasNumericAttributes;
	}

	public Attribute get(int i) {
		return attributes.get(i);
	}

	public Attribute getClassAttribute() {
		return attributes.get(classIndex);
	}

	public int getIndex(String name) throws Exception {
		int attributeIndex = -1;
		for (int i = 0; i < attributes.size(); i++) {
			if (attributes.get(i).getName().equals(name)) {
				attributeIndex = i;
			}
		}
		if (attributeIndex == -1) {
			throw new java.lang.Exception("attribute not found");
		}
		return attributeIndex;
	}

	public int size() {
		return attributes.size();
	}

	public void parse(Scanner scanner) throws Exception {
		// Parses the attribute declarations in the specified scanner.
		// By convention, the last attribute is the class label after parsing.
		Attribute a1 = new Attribute();
		while (scanner.hasNextLine()) {
			a1 = AttributeFactory.make(scanner);
			if (a1 == null) {
				return;
			} else {
				attributes.add(a1);
				if (a1 instanceof NumericAttribute) {
					hasNumericAttributes = true;
				}
			}
			this.classIndex = this.attributes.size()-1;
		} // end of while
	}

	public void setClassIndex(int classIndex) throws Exception {
		try {
			if (classIndex < 0 | classIndex >= attributes.size()) {
				throw new java.lang.Exception("Index out of bounds");
			} else {
				this.classIndex = classIndex;
			}
		} catch (java.lang.Exception e) {
			System.out.println(e);
		}
	}

	public String toString() {
		StringBuilder toReturn = new StringBuilder();

		for (int i = 0; i < attributes.size(); i++) {
			toReturn.append(attributes.get(i).toString());
		}

		return toReturn.toString();
	}

} // End of Attributes class
