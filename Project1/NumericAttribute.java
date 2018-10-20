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
		return "@attribute " + name + " numeric\n";
	}

	public boolean validValue(Double val) {
		return true;
	}
}
