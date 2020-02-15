package com.mobiquityinc.packer.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DecisionNode {
	private List<Thing> selectedThings = new ArrayList<>();
	private int level;
	private BigDecimal profit;
	private BigDecimal bound; 
	private BigDecimal weight; 
	
	public void addThing(Thing t) {
		selectedThings.add(t);
	}
	
	public void addThings(List<Thing> selectedThings) {
		this.selectedThings.addAll(selectedThings);
	}
}
