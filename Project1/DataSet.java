import java.io.*;
import java.util.Scanner;
import java.lang.*;
import java.util.*;

/*
 * DataSet.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class DataSet extends Object {
	protected String name;
	protected Attributes attributes;
	protected Examples examples;
	protected Random random;

	public DataSet() {
		name = "?z?z?z?";
		attributes = new Attributes();
		examples = new Examples(null);
		random = new Random();
	}

	public DataSet(Attributes attributes1) {
		attributes = attributes1;
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

	 public void setRandom( Random random1 )
	 {
		 random = random1;
	 }
	
	 public String toString()
	 {
		return "@dataset " + name + "\n\n" + attributes.toString() + "\n" + examples.toString();
	 }

	private void parse(Scanner scanner) {
		try {
			String line = scanner.nextLine();
			String[] words = line.split(" ");
			if (words[0].equals("@dataset")) {
				name = words[1];
				line = scanner.nextLine(); // empty line after the name of the dataset
				
				Attributes scannerAttributes = new Attributes();
				scannerAttributes.parse(scanner);
				attributes = scannerAttributes;
							
				Examples dataExample = new Examples(attributes);
				examples = dataExample;
				examples.parse(scanner);				
			} // END OF IF
			else {
				System.out.println("Please enter valid .mff dataset");
			} // END OF ELSE
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	} // End of Parse function

	
	public static void main(String[] args) {
		try {
			DataSet ds = new DataSet();
			ds.load("lenses.mff");
			System.out.println(ds.toString());
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}// End of main
} // End of class






