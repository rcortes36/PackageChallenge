package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.io.FilePackageReader;
import com.mobiquityinc.packer.io.PackagesSolutionWriter;
import com.mobiquityinc.packer.model.PackageInformation;
import com.mobiquityinc.packer.model.PackagesRestrictionsChecker;
import com.mobiquityinc.packer.picker.Picker;

/**
 * This class Coordinates all the other Classes 
 * @author ricardo.cortes
 *
 */
public class Packer {
	private Packer() {
		//To prevent creation of object of this Class
	}

	public static String pack(String filePath) throws APIException {
		// Read the file, without validations beyond the format
		FilePackageReader filePackageReader = new FilePackageReader();
		List<PackageInformation> packagesToPack = filePackageReader.readThings(filePath);

		// Check the "world" restrictions
		PackagesRestrictionsChecker.verifyPackagesRestrictions(filePath, packagesToPack);

		// Now, pick things and pack
		List<Picker> pickersList = new ArrayList<>(packagesToPack.size());
		for (PackageInformation packageInformation : packagesToPack) {
			Picker picker = new Picker(packageInformation.getThings(), packageInformation.getPackageWeightLimit());
			picker.pack();
			pickersList.add(picker);
		}

		//Last step, create the String with the solution
		PackagesSolutionWriter packagesSolutionWriter = new PackagesSolutionWriter(pickersList);
		return packagesSolutionWriter.getSolution();
	}
}
