/*
 * Example.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

import java.util.ArrayList;
//Stores the attribute values of an example. Numeric values are stored as is. 
//Nominal values are stored as Doubles and are indices of the value in the attributes structure.
public class Example extends ArrayList<Double>
{
	  public Example()
	  {
		  super();
		  new ArrayList<Double>();
		  // default constructor
		  
	  }
	  public Example( int n )
	  {
		  super(n);
		  this.ensureCapacity(n);
	  }
	  
}


