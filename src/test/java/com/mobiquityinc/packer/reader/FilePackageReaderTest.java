package com.mobiquityinc.packer.reader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FilePackageReaderTest {
	@Test
	void testPackEmptyList() {
		FilePackageReader reader = new FilePackageReader();
		assertDoesNotThrow(() -> reader.readThings("src/test/resources/Input.txt"));
		
	}
	
	@Test
	void testPackInvalidPackageWeight() {
		FilePackageReader reader = new FilePackageReader();
		assertThrows(FilePackageReaderException.class, () -> reader.readThings("src/test/resources/InvalidInputPackageWeight.txt"));
	}
	
	@Test
	void testPackInvalidThingWeight() {
		FilePackageReader reader = new FilePackageReader();
		assertThrows(FilePackageReaderException.class, () -> reader.readThings("src/test/resources/InvalidInputThingWeight.txt"));
	}
	
	@Test
	void testPackInvalidThingCost() {
		FilePackageReader reader = new FilePackageReader();
		assertThrows(FilePackageReaderException.class, () -> reader.readThings("src/test/resources/InvalidInputThingCost.txt"));
	}

	
	
}

