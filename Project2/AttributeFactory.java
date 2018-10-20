import java.util.Scanner;

/*
 * AttributeFactory.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class AttributeFactory extends Object {
	public AttributeFactory() {
		// default constructor
	}

	// Processes a single attribute declaration
	public static Attribute make(Scanner scanner) throws Exception {
		String line = scanner.nextLine();
		String[] words = line.split(" ");

		if (line.startsWith("@attribute")) {
			if (words[2].equals("numeric")) {
				return new NumericAttribute(words[1]);
			} else {
				NominalAttribute nomAtt = new NominalAttribute(words[1]);
				for (int i = 2; i < words.length; i++) {
					nomAtt.addValue(words[i]);
				}
				return nomAtt;
			}
		} // end if
		else {
			return null;
		}
	}

} // End Class Declaration
