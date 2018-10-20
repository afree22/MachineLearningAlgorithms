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
		try {
			String line = scanner.nextLine();
			String[] words = line.split(" ");
			if (!(words[0].equals("@attribute"))) {
				return null;
			} else {
				if (words[2].equals("numeric")) {
					NumericAttribute nomAtt = new NumericAttribute(words[1]);
					return nomAtt;

				} else {
					NominalAttribute nomAtt = new NominalAttribute(words[1]);
					for (int i = 2; i < words.length; i++) {
						nomAtt.addValue(words[i]);
					}
					return nomAtt;
				}
			}
		} catch (java.lang.Exception e) {
			System.out.println(e);
			System.out.println("Error with make function in Attribute Factory");
		}
		return null;
	}

} // End Class Declaration
