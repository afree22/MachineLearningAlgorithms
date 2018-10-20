/*
 * Attribute.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class Attribute extends Object
{
	  protected String name;
	  
	  public Attribute()
	  {
		  name = "unknown";
		  
	  }
	  
	  public Attribute( String name1 )
	  {
		  // Explicit constructor that sets the name of this attribute.
		  name = name1;
	  }
	  
	  public String getName() {return name;}
	  public void setName( String name1) {name = name1;}
	  
	  public int size()
	  {
		  // Returns 0 in the superclass
		  // in the subcloss - nominalAttribute it will return the size of the domain
		  return 0;
	  }
	 
	  public String toString()
	  {
//		   Returns a string representation of this attribute.
		  return "@attribute " + name;
	  }
	  
	  public static void main(java.lang.String[] args)
	  {
		  try
		  {
		 // This main is for testing Attribute class and attribute functions
		  Attribute att = new Attribute("test");
		  System.out.println(att.getName());
		  System.out.println(att.toString());
		  System.out.println("");

		  
		  NominalAttribute nomAtt = new NominalAttribute("tires");
		  nomAtt.addValue("knobby");
		  nomAtt.addValue("treads");
		  System.out.println(nomAtt.getName());
		  System.out.println(nomAtt.size());
		  System.out.println(nomAtt.getValue(0));
		  System.out.println(nomAtt.getIndex("treads"));
		  System.out.println(nomAtt.toString());

		  
		  NumericAttribute numAtt = new NumericAttribute("weight");
		  System.out.println(numAtt.getName());
		  System.out.println(numAtt.toString());
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  
	  }
}
  

 

