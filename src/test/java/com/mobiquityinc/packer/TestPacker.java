package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;

class TestPacker {
	private final String EXPECTED_PROVIDED_RESULTS = 
			"4\n" + 
			"-\n" + 
			"2,7\n" + 
			"8,9";
	
	private final String EXPECTED_EXTRA_RESULTS = 
			"2,4\n" + 
			"2,6\n" + 
			"1,4,6";

	@Test
	void testPackerProjectProvidedData() {
		try {
			String actualResult = Packer.pack("src/test/resources/CompleteInput.txt");
			assertEquals(EXPECTED_PROVIDED_RESULTS, actualResult);
			
		} catch (APIException e) {
			fail();
		}
	}
	
	@Test
	void testPackerProjectAditionalCases() {
		try {
			String actualResult = Packer.pack("src/test/resources/ExtraTestCases.txt");
			assertEquals(EXPECTED_EXTRA_RESULTS, actualResult);
			
		} catch (APIException e) {
			fail();
		}
	}
	
	@Test
	void testPackInvalidPackageWeight() {
		assertThrows(APIException.class, () -> Packer.pack("src/test/resources/InvalidInputPackageWeight.txt"));
	}
	
	@Test
	void testPackInvalidThingWeight() {
		assertThrows(APIException.class, () -> Packer.pack("src/test/resources/InvalidInputThingWeight.txt"));
	}
	
	@Test
	void testPackInvalidThingCost() {
		assertThrows(APIException.class, () -> Packer.pack("src/test/resources/InvalidInputThingCost.txt"));
	}
	
	@Test
	void testPackDuplicatedThings() {
		assertThrows(APIException.class, () -> Packer.pack("src/test/resources/InvalidInputDuplicatedThings.txt"));
	}
	
	

}
