/*
 * CategoricalEstimator.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class CategoricalEstimator extends Estimator implements Serializable
{
	protected ArrayList<Integer> dist;

  public CategoricalEstimator()
  {
	  dist = new ArrayList<Integer>();
  }
  
  public CategoricalEstimator( Integer k )
  {
	  // k = number of categories
	  dist = new ArrayList<Integer>(k); // new arraylist
	  for(int i = 0; i < k; i++)
	  {
		  // fill table with zeros
		  dist.add(0);
	  }
  } 
  
  
  public void add( Number x ) throws Exception
  {
		n++; 
		int incrementVal = dist.get(x.intValue()) + 1;
		dist.set(x.intValue(), incrementVal);
  }
  
  public Double getProbability( Number x )
  {
	  int xVal = x.intValue(); // have to convert Integer to int
	  
	  // use add-one smoothing when calculating the probability
	  double numerator = dist.get(xVal) + 1;
	  double denominator = this.n + dist.size();
	  return (numerator/denominator);
  }

}


