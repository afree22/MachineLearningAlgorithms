import java.util.ArrayList;

/*
 * NominalAttribute.java
 * Copyright (c) 2018 Georgetown University.  All Rights Reserved.
 */

public class NominalAttribute extends Attribute
{
	  protected ArrayList<String> domain; 
	  
	  public NominalAttribute()
	  {
		  domain = new ArrayList<String>();
	  }
	  
	  public NominalAttribute( String n1 )
	  {
		  super(n1);
		  domain = new ArrayList<String>();
	  }
	  
	  public void addValue( String value )
	  {
		  domain.add(value);
	  }
	  
	  public int size()
	  {
		  return(domain.size());
		  
	  }
	  
	  public String getValue( int index )
	  {
		  return domain.get(index);
	  }
	  
	  public int getIndex( String value ) throws Exception
	  {
		  int domainIndex = -1;
		  try
		  {
			for(int i = 0; i< domain.size() ; i++){
				if(domain.get(i).equals(value))
				{
					domainIndex = i;
				}
			}
			if(domainIndex == -1)
			{
				Exception e = new Exception("Nominal Attribute Domain not found");
				throw e;
			}
			return domainIndex;
		  }
			catch(Exception e) {
				System.out.println(e);
			}
		  return 0;
	  }
	  
	  public String toString()
	  {
		  String nomAttrName = "@attribute " + name;
		  for(int i = 0; i < domain.size(); i++)
		  {
			  nomAttrName += " "+ domain.get(i);
			  
		  }
		  nomAttrName += "\n";
		  return nomAttrName;
	  }
	  
	  public boolean validValue( String value )
	  {
//		  Returns whether the value is valid for a nominal attribute
		  boolean inDomain = false;
			for(int i = 0; i< domain.size() ; i++){
				if(domain.get(i).equals(value))
				{
					inDomain = true;
				}
			}
			return inDomain;
	  }
	  
} // End of Class





