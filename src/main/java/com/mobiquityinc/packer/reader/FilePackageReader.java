package com.mobiquityinc.packer.reader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.mobiquityinc.packer.model.PackageInformation;
import com.mobiquityinc.packer.model.Thing;

/**
 * Reads a file that contains the information
 * 
 * @author ricardo.cortes
 *
 */
public class FilePackageReader {
	private static final BigDecimal MAX_PACKAGE_WEIGHT = new BigDecimal("100");
	private static final BigDecimal MAX_WEIGHT_THINGS = new BigDecimal("100");
	private static final BigDecimal MAX_COST_THINGS = new BigDecimal("100");
	
	public static final int MAX_NUMBER_THINGS = 15;

	public List<PackageInformation> readThings(String filePath) throws FilePackageReaderException {
		List<PackageInformation> packagesToPack = new LinkedList<>();

		List<String> inputLines = new LinkedList<>();

		// Use BufferReader for better resource use - UTF8 parameter is the default, but
		// to make the code more explicit
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
			inputLines = br.lines().collect(Collectors.toList());
			int lineNumber = 0;
			for (String ln : inputLines) {
				lineNumber++;
				// Divide exactly by one space
				String tokens[] = ln.split(" ");
				String packageWeighString = tokens[0];

				try {
					BigDecimal packageWeight = new BigDecimal(packageWeighString);
					if (packageWeight.compareTo(MAX_PACKAGE_WEIGHT) > 0) {
						throw new FilePackageReaderException(
								"Error reading the file: " + filePath + " invalid package weight: " + packageWeighString
										+ " > MAXIMUM ALLOWED WEIGHT(" + MAX_PACKAGE_WEIGHT + ") line:" + lineNumber);
					}
					List<Thing> thingsInLine = new ArrayList<>(MAX_NUMBER_THINGS);
					for (int i = 2; i < tokens.length; i++) {
						Thing t = parseThing(tokens[i], filePath, i);
						if(t.getWeight().compareTo(MAX_WEIGHT_THINGS) > 0) {
							throw new FilePackageReaderException(
									"Error reading the file: " + filePath + " invalid thing weight: " + t.getWeight()
											+ " > MAXIMUM ALLOWED WEIGHT(" + MAX_WEIGHT_THINGS + ") line:" + lineNumber);
						}
						if(t.getCost().compareTo(MAX_COST_THINGS) > 0) {
							throw new FilePackageReaderException(
									"Error reading the file: " + filePath + " invalid thing cost: " + t.getCost()
											+ " > MAXIMUM ALLOWED COST(" + MAX_COST_THINGS + ") line:" + lineNumber);
						}
						thingsInLine.add(t);
					}
					packagesToPack.add(new PackageInformation(thingsInLine, packageWeight));

				} catch (NumberFormatException e) {
					throw new FilePackageReaderException("Error reading the file: " + filePath
							+ " invalid package weight: " + packageWeighString + " line:" + lineNumber, e);
				}

			}

		} catch (IOException e) {
			throw new FilePackageReaderException("Error reading the file: " + filePath, e);
		}

		return packagesToPack;
	}

	private Thing parseThing(String rawThingString, String filePath, int lineNumber) throws FilePackageReaderException {
		// For example '(2,88.62,€98)' will be divided in [(2], [88.62], [€98)]
		String[] thingValues = rawThingString.split(",");

		// Check for the format
		if (thingValues[0].charAt(0) != '(') {
			throw new FilePackageReaderException("Error reading the file: " + filePath
					+ " invalid thing format, missing '(' or space at " + rawThingString + " line:" + lineNumber);
		}

		if (thingValues[2].charAt(0) != '€') {
			throw new FilePackageReaderException("Error reading the file: " + filePath
					+ " invalid thing format, missing '€' at " + rawThingString + " line:" + lineNumber);
		}

		if (thingValues[2].charAt(thingValues[2].length() - 1) != ')') {
			throw new FilePackageReaderException("Error reading the file: " + filePath
					+ " invalid thing format, missing ')' or space at " + rawThingString + " line:" + lineNumber);
		}

		// Transform to a thing
		Thing t = null;
		try {
			t = new Thing(Integer.parseInt(thingValues[0].substring(1)), // ID is a integer number
					new BigDecimal(thingValues[1]),
					new BigDecimal(thingValues[2].substring(1, thingValues[2].length() - 1))); // Cost was assumed as
																								// decimal and € char
																								// assumed always
																								// present but OK to
																								// ignore
		} catch (NumberFormatException e) {
			throw new FilePackageReaderException("Error reading the file: " + filePath + " invalid input for thing: "
					+ rawThingString + " line:" + lineNumber, e);
		}
		return t;
	}
}
