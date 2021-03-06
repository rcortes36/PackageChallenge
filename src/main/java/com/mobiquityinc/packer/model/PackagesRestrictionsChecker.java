package com.mobiquityinc.packer.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mobiquityinc.exception.APIException;

public class PackagesRestrictionsChecker {
	private static final BigDecimal MAX_PACKAGE_WEIGHT = new BigDecimal("100");
	private static final BigDecimal MAX_WEIGHT_THINGS = new BigDecimal("100");
	private static final BigDecimal MAX_COST_THINGS = new BigDecimal("100");
	private static final int MAX_NUMBER_THINGS = 15;
	
	private PackagesRestrictionsChecker() {
		//For now, this class just holds an method to check restrictions
	}
	/**
	 * @param origin Used to create meaningful error exceptions descriptions 
	 * @param packagesToPack List of packages (capacity and things) to verify 
	 * @throws APIException - If any of the weights are Zero or below or if maximum limit are surpass
	 */
	public static void verifyPackagesRestrictions(String origin, List<PackageInformation> packagesToPack)
			throws APIException {
		
		for (PackageInformation packageInformation : packagesToPack) {
			//Check for weight restriction
			if (packageInformation.getPackageWeightLimit().compareTo(MAX_PACKAGE_WEIGHT) > 0) {
				throw new APIException(
						"File: " + origin + " invalid package weight: " + packageInformation.getPackageWeightLimit()
								+ " > MAXIMUM ALLOWED WEIGHT(" + MAX_PACKAGE_WEIGHT);
			}
			
			if (packageInformation.getPackageWeightLimit().compareTo(BigDecimal.ZERO) <= 0) {
				throw new APIException(
						"File: " + origin + " invalid package weight: " + packageInformation.getPackageWeightLimit());
			}
			
			//check for max number of thing 
			if (packageInformation.getThings().size() > MAX_NUMBER_THINGS) {
				throw new APIException("File: " + origin + " to many things to choose from "
						+ packageInformation.getThings().size() + " > MAXIMUM ALLOWED THINGS(" + MAX_NUMBER_THINGS
						+ " for package with limit weight: " + packageInformation.getPackageWeightLimit());
			}
			
			//check for duplicates things
			Set<Thing> set = new HashSet<>(packageInformation.getThings());
			if(set.size() < packageInformation.getThings().size()){
				throw new APIException("File: " + origin + " REPETED things to choose from "
						+ "for package with limit weight: " + packageInformation.getPackageWeightLimit());
			}
			
			//Check for cost and weight restrictions on Things
			for (Thing t : packageInformation.getThings()) {
				if (t.getWeight().compareTo(MAX_WEIGHT_THINGS) > 0) {
					throw new APIException("File: " + origin + " invalid thing weight: " + t.getWeight()
							+ " > MAXIMUM ALLOWED WEIGHT(" + MAX_WEIGHT_THINGS
							+ " for package with limit weight: " + packageInformation.getPackageWeightLimit());
				}
				if (t.getCost().compareTo(MAX_COST_THINGS) > 0) {
					throw new APIException("File: " + origin + " invalid thing cost: " + t.getCost()
							+ " > MAXIMUM ALLOWED COST(" + MAX_COST_THINGS
							+ " for package with limit weight: " + packageInformation.getPackageWeightLimit());
				}
				
				if (t.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
					throw new APIException("File: " + origin + " invalid NON POSITIVE thing weight: " + t.getWeight()
							+ " for package with limit weight: " + packageInformation.getPackageWeightLimit());
				}
				if (t.getCost().compareTo(BigDecimal.ZERO) <= 0) {
					throw new APIException("File: " + origin + " invalid NON POSITIVE thing cost: " + t.getCost()
							+ " for package with limit weight: " + packageInformation.getPackageWeightLimit());
				}
			}
		}
	}

}
