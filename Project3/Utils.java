
/*
 * Utils.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */
import java.io.*;

public class Utils {
	// Return the index where the max value is located
	public static int maxIndex(double[] p) {
		double max = p[0];
		int indexOfMax = 0;
		for (int i = 0; i < p.length; i++) {
			if (p[i] > max) {
				max = p[i];
				indexOfMax = i;
			}
		}
		return indexOfMax;
	}

	// I copied this method from this website:
	// https://alvinalexander.com/java/java-deep-clone-example-source-code
	public static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
