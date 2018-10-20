
/*
 * Examples.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Examples extends ArrayList<Example> {
	private Attributes attributes;

	public Examples(Attributes attributes1) {
		attributes = attributes1;
	}

	public String toString() {
		String toReturn = "@example\n\n";
		for (int i = 0; i < size(); i++) {
			Example e1 = get(i); // example in the index of the arraylist

			for (int j = 0; j < e1.size(); j++) {
				double d1 = e1.get(j); // double in the first index of the example

				Attribute a1 = attributes.get(j);
				if (a1 instanceof NumericAttribute) {
					if (j == 0) {
						toReturn += d1;
					} else {
						toReturn += " " + d1;
					}
				} else {
					if (j == 0) {
						toReturn += ((NominalAttribute) a1).getValue((int) d1);
					} else {
						toReturn += " " + ((NominalAttribute) a1).getValue((int) d1);
					}
				}

			}
			toReturn += "\n";

		}
		return toReturn;
	}

	// Nominal values are stored as Doubles and are indices of the value in the
	// attributes structure.
	public void parse(Scanner scanner) throws Exception {
		// Get rid of empy line
		String line = scanner.nextLine();
		line = scanner.nextLine();
		try {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				String[] words = line.split(" ");
				// each line is an example to be parse
				Attribute a1 = null;
				Example e1 = new Example(words.length);

				for (int i = 0; i < words.length; i++) {
					a1 = attributes.get(i);
					if (a1 instanceof NumericAttribute) {
						Double j = Double.parseDouble(words[i]);
						e1.add(j);
					} else {
						int j = ((NominalAttribute) a1).getIndex(words[i]);
						e1.add((double) j);
					}
				}
				// add the example before the next line
				add(e1);
			} // end of while
		} catch (java.lang.Exception e) {
			System.out.println(e);
		}
	}
}
