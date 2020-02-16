package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.exception.APIException;

class TestPacker {
	private final String EXPECTED_RESULTS = "4\n" + 
			"-\n" + 
			"2,7\n" + 
			"8,9"; 
	@Test
	void testPackerProject() {
		try {
			String actualResult = Packer.pack("src/test/resources/CompleteInput.txt");
			assertEquals(EXPECTED_RESULTS, actualResult);
			
		} catch (APIException e) {
			fail();
		}
	}

}
