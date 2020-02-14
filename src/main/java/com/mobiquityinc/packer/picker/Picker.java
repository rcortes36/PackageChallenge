package com.mobiquityinc.packer.picker;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mobiquityinc.packer.model.DecisionNode;
import com.mobiquityinc.packer.model.Thing;

public class Picker {
	private final Thing thingsArray[];
	private final List<Thing> selectedThings;
	private final BigDecimal maxWeight;

	public Picker(List<Thing> thingsList, BigDecimal maxWeight) {
		// Remove things that weights more than the box capacity
		thingsArray = thingsList.stream().filter(thing -> thing.getWeight().compareTo(maxWeight) > 0)
				.toArray(Thing[]::new);
		this.selectedThings = new LinkedList<>();
		this.maxWeight = maxWeight;
	}

	public BigDecimal calculateMaxNodeProfit(DecisionNode dNode, int n, int W) {
		BigDecimal currentProfit = dNode.getProfit();

		return currentProfit;

	}

	public BigDecimal pack() {
		BigDecimal profit = BigDecimal.ZERO;
		switch (thingsArray.length) {
		case 0:
			// selectedThings already is an empty list and profit is Zero
			break;
		case 1:
			profit = thingsArray[0].getCost();
			selectedThings.add(thingsArray[0]);
			break;
		default:
			profit = packNonTrivial();
			break;
		}
		return profit;
	}

	public BigDecimal packNonTrivial() {
		BigDecimal profit = BigDecimal.ZERO;
		int totalThings = thingsArray.length;
		Queue<DecisionNode> queue = new ArrayDeque<>(thingsArray.length);
		// Head node with the most valuable thing
		Thing firstThing = thingsArray[0];
		DecisionNode headNode = new DecisionNode(firstThing);
		int currentLevel = -1;

		// Values for the first level
		headNode.setLevel(currentLevel);
		headNode.setProfit(BigDecimal.ZERO);
		headNode.setWeight(BigDecimal.ZERO);
		// Head or most valuable thing is the first that will be evaluated
		queue.add(headNode);

		for (DecisionNode node : queue) {
			if (currentLevel < totalThings - 1) {
				Thing currentThing = node.getThing();
				currentLevel++;
				Thing nextThing = thingsArray[currentLevel];
				DecisionNode nextNode = new DecisionNode(nextThing);
				nextNode.setLevel(currentLevel);
				nextNode.setWeight(node.getWeight().add(currentThing.getWeight()));
				nextNode.setProfit(node.getProfit().add(currentThing.getCost()));

				//Are room for the next thing and has value? 
				if ((nextNode.getWeight().compareTo(maxWeight) < 0) && nextNode.getProfit().compareTo(profit) > 0) {
					profit = nextNode.getProfit();
				}
				
			}
		}
		return profit;
	}
}
