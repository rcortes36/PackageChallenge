package com.mobiquityinc.packer.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DecisionNode {
	private final Thing thing;
	private int level;
	private BigDecimal profit;
	private BigDecimal bound; 
	private BigDecimal weight; 
}
