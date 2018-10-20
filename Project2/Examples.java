
/*
 * Examples.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Examples extends ArrayList<Example> {
	private Attributes attributes;

	public Examples(Attributes attributes) {
		super();
		this.attributes = attributes;
	}

	// Nominal values are stored as Doubles and are indices of the value in the
	// attributes structure.
	public void parse(Scanner scanner) throws Exception {
		while (scanner.hasNext()) {
			Attribute a1 = null;
			Example e1 = new Example(attributes.size());
			for (int i = 0; i < attributes.size(); i++) {
				String atrib = scanner.next();
				a1 = attributes.get(i);
				if (a1 instanceof NumericAttribute) {
					Double j = Double.parseDouble(atrib);
					e1.add(j);
				} else {
					int j = ((NominalAttribute) a1).getIndex(atrib);
					e1.add((double) j);
				}
			} // end for loop
			this.add(e1);
		} // end while loop
	}

	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("@examples\n\n");
		for (int i = 0; i < this.size(); i++) {
			Example e1 = this.get(i); // example in the index of the arraylist

			for (int j = 0; j < e1.size(); j++) {
				double d1 = e1.get(j); // double in the first index of the example

				Attribute a1 = attributes.get(j);
				if (a1 instanceof NumericAttribute) {
					if (j == 0) {
						toReturn.append(d1);
					} else {
						toReturn.append(" " + d1);
					}
				} else {
					if (j == 0) {
						toReturn.append(((NominalAttribute) a1).getValue((int) d1));
					} else {
						toReturn.append(" " + ((NominalAttribute) a1).getValue((int) d1));
					}
				}

			}
			toReturn.append("\n");
		}
		return toReturn.toString();
	}
}
