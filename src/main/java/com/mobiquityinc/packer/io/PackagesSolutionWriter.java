package com.mobiquityinc.packer.io;

import java.util.List;

import com.mobiquityinc.packer.model.Thing;
import com.mobiquityinc.packer.picker.Picker;

import lombok.Getter;

public class PackagesSolutionWriter {
	@Getter
	private final List<Picker> packedPackages;

	public PackagesSolutionWriter(List<Picker> packedPackages) {
		this.packedPackages = packedPackages;
	}

	public String getSolution() {
		StringBuilder stringBuilder = new StringBuilder();
		boolean extraLastNewLineInserted = false;
		for (Picker picker : packedPackages) {
			generateStringForPackage(picker.getSelectedThings(), stringBuilder);
			stringBuilder.append("\n");
			extraLastNewLineInserted = true;
		}
		if (extraLastNewLineInserted) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		return stringBuilder.toString();
	}

	private void generateStringForPackage(List<Thing> selectedThings, StringBuilder stringBuilder) {
		if (selectedThings.isEmpty()) {
			stringBuilder.append("-");
		} else if (selectedThings.size() == 1) {
			stringBuilder.append(selectedThings.get(0).getIndexNumber());
		} else {
			for (Thing thing : selectedThings) {
				stringBuilder.append(thing.getIndexNumber()).append(',');
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
	}

}
