package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;



/**
 * @author ricardo.cortes
 *
 */
public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
	StringBuilder result = new StringBuilder();
    result.append("A").append('\n').append("B");
    System.out.println(result); 
    return null;
  }
  
   public static void main(String args[]) {
	   try{
		   pack("");
	   }catch(APIException e) {
		   
	   }
   }
   
}
