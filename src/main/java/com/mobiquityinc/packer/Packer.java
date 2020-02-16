package com.mobiquityinc.packer;

import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.model.PackageInformation;
import com.mobiquityinc.packer.model.Thing;
import com.mobiquityinc.packer.picker.Picker;
import com.mobiquityinc.packer.reader.FilePackageReader;

/**
 * @author ricardo.cortes
 *
 */
public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
	  //A number and a Colon
	  StringBuilder stringBuilder = new StringBuilder(FilePackageReader.MAX_NUMBER_THINGS * 10);
	  FilePackageReader filePackageReader = new FilePackageReader();
	  List<PackageInformation> packagesToPack = filePackageReader.readThings(filePath);
	  
	  for(PackageInformation packageInformation : packagesToPack) {
		  Picker picker = new Picker(packageInformation.getThings(), packageInformation.getPackageWeightLimit());
		  picker.pack();
		  stringBuilder.append(generateStringForPackage(picker.getSelectedThings()));
		  stringBuilder.append("\n");
	  }
	  return stringBuilder.substring(0, stringBuilder.length() -1);
  }
  
  private static String generateStringForPackage(List<Thing> selectedThings) {
	  String result = "";
	  if(selectedThings.isEmpty()) {
		  result =  "-";
	  } else if (selectedThings.size() == 1) {
		  result += selectedThings.get(0).getIndexNumber();
	  } else {
		  StringBuilder stringBuilder = new StringBuilder();
		  for(Thing thing : selectedThings) {
			  stringBuilder.append(thing.getIndexNumber()).append(',');
		  }
		  result = stringBuilder.substring(0, stringBuilder.length() -1);
	  }
	  return result;
  }
   
}
