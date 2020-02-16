package com.mobiquityinc.packer.reader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.packer.io.FilePackageReader;
import com.mobiquityinc.packer.io.exception.FilePackageReaderException;

class FilePackageReaderTest {
	@Test
	void testPackEmptyList() {
		FilePackageReader reader = new FilePackageReader();
		assertDoesNotThrow(() -> reader.readThings("src/test/resources/Input.txt"));
	}
	
	@Test
	void testlackCurrencySimbol() {
		FilePackageReader reader = new FilePackageReader();
		assertThrows(FilePackageReaderException.class,() -> reader.readThings("src/test/resources/InvalidInputThingNoEuro.txt"));
	}
	
	@Test
	void testIncorrectSpace() {
		FilePackageReader reader = new FilePackageReader();
		assertThrows(FilePackageReaderException.class,() -> reader.readThings("src/test/resources/InvalidInputThingSpaces.txt"));
	}
}

