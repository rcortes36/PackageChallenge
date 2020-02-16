package com.mobiquityinc.packer.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PackageInformation {
	@Getter
	private final List<Thing> things;
	@Getter
	private final BigDecimal packageWeightLimit;

}
