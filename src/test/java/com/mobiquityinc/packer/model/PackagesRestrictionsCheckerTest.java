package com.mobiquityinc.packer.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;

public class PackagesRestrictionsCheckerTest {
	private static String DUMMY_FILE_NAME = "DummyFileName";

	@Test
	void testVerifyPackagesRestrictionsMaxPackageWeight() {
		List<PackageInformation> packagesToPack = new ArrayList<>(1);
		List<Thing> thingsList = new ArrayList<>(1);
		PackageInformation packageInformation = new PackageInformation(thingsList, new BigDecimal("101"));
		packagesToPack.add(packageInformation);
		assertThrows(APIException.class,
				() -> PackagesRestrictionsChecker.verifyPackagesRestrictions(DUMMY_FILE_NAME, packagesToPack));

	}

	@Test
	void testVerifyPackagesRestrictionsMinPackageWeight() {
		List<PackageInformation> packagesToPack = new ArrayList<>(1);
		List<Thing> thingsList = new ArrayList<>(1);
		PackageInformation packageInformation = new PackageInformation(thingsList, BigDecimal.ONE.negate());
		packagesToPack.add(packageInformation);
		assertThrows(APIException.class,
				() -> PackagesRestrictionsChecker.verifyPackagesRestrictions(DUMMY_FILE_NAME, packagesToPack));
	}

	@Test
	void testVerifyPackagesRestrictionsMinThingsWeight() {
		List<PackageInformation> packagesToPack = new ArrayList<>(1);
		List<Thing> thingsList = new ArrayList<>(1);
		Thing a = new Thing(1, BigDecimal.ONE.negate(), new BigDecimal("45.0"));
		thingsList.add(a);
		PackageInformation packageInformation = new PackageInformation(thingsList, new BigDecimal("101"));
		packagesToPack.add(packageInformation);
		assertThrows(APIException.class,
				() -> PackagesRestrictionsChecker.verifyPackagesRestrictions(DUMMY_FILE_NAME, packagesToPack));
	}

	@Test
	void testVerifyPackagesRestrictionsMaxThingsWeight() {
		List<PackageInformation> packagesToPack = new ArrayList<>(1);
		List<Thing> thingsList = new ArrayList<>(1);
		Thing a = new Thing(1, new BigDecimal("101"), new BigDecimal("45.0"));
		thingsList.add(a);
		PackageInformation packageInformation = new PackageInformation(thingsList, new BigDecimal("101"));
		packagesToPack.add(packageInformation);
		assertThrows(APIException.class,
				() -> PackagesRestrictionsChecker.verifyPackagesRestrictions(DUMMY_FILE_NAME, packagesToPack));
	}

}
