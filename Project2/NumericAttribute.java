/*
 * NumericAttribute.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class NumericAttribute extends Attribute {
	public NumericAttribute() {
		super();
	}

	public NumericAttribute(String n1) {
		super(n1);
	}

	public String toString() {
		// Make a call to the super class
		//super();

		String superResult = super.toString();
				
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(superResult);
		toReturn.append(" numeric\n");
		return toReturn.toString();
	}

	public boolean validValue(Double val) {
		return true;
	}
}
