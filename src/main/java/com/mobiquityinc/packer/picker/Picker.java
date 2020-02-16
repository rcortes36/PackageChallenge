package com.mobiquityinc.packer.picker;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.Getter;

import com.mobiquityinc.packer.model.DecisionNode;
import com.mobiquityinc.packer.model.Thing;

public class Picker {
	private final Thing thingsArray[];
	@Getter
	private List<Thing> selectedThings;
	private final BigDecimal packageWeightLimit;
	@Getter
	private BigDecimal calculatedCost;
	public static Comparator<Thing> COMPARE_THING_BY_VALUE_DESC = ((Thing a, Thing b) -> b.getValue()
			.compareTo(a.getValue()));

	public Picker(List<Thing> thingsList, BigDecimal boxWeightLimit) {
		// Remove things that weights more than the box capacity, then sort it according
		// with the value
		thingsArray = thingsList.stream().filter(thing -> thing.getWeight().compareTo(boxWeightLimit) <= 0)
				.sorted(COMPARE_THING_BY_VALUE_DESC).toArray(Thing[]::new);
		this.selectedThings = new LinkedList<>();
		this.packageWeightLimit = boxWeightLimit;
	}

	public void pack() {
		this.calculatedCost = BigDecimal.ZERO;
		switch (thingsArray.length) {
		case 0:
			// selectedThings already is an empty list and calculatedCost is Zero
			break;
		case 1:
			// If there is just one thing, put it it the box, weight restriction already
			// checked during the reading
			this.calculatedCost = thingsArray[0].getCost();
			selectedThings.add(thingsArray[0]);
			break;
		default:
			this.calculatedCost = packNonTrivial();
			selectedThings.sort((Thing a, Thing b) -> new Integer(a.getIndexNumber())
					.compareTo(new Integer(b.getIndexNumber())));
			break;
		}
	}

	private BigDecimal packNonTrivial() {
		BigDecimal profit = BigDecimal.ZERO;
		int totalThings = this.thingsArray.length;
		Queue<DecisionNode> queue = new LinkedList<DecisionNode>();
		DecisionNode headNode = new DecisionNode();

		// Values for the first level
		headNode.setLevel(-1);
		headNode.setProfit(BigDecimal.ZERO);
		headNode.setWeight(BigDecimal.ZERO);
		headNode.setBound(BigDecimal.ZERO);
		// Head or most valuable thing is the first that will be evaluated
		queue.add(headNode);

		while (!queue.isEmpty()) {
			DecisionNode node = queue.remove();
			if (node.getLevel() < totalThings - 1) {
				Thing nextThing = this.thingsArray[node.getLevel() + 1];
				DecisionNode includeNode = new DecisionNode();
				includeNode.addThings(node.getSelectedThings());
				includeNode.addThing(nextThing);
				includeNode.setLevel(node.getLevel() + 1);
				includeNode.setWeight(node.getWeight().add(nextThing.getWeight()));
				includeNode.setProfit(node.getProfit().add(nextThing.getCost()));
				includeNode.setBound(calculateMaxNodeProfit(includeNode));

				// Are room for the new group of things and new thing has value?
				if ((includeNode.getWeight().compareTo(this.packageWeightLimit) <= 0)
						&& includeNode.getBound().compareTo(profit) >= 0) {
					if (profit.compareTo(includeNode.getProfit()) < 0) {
						profit = includeNode.getProfit();
						selectedThings = includeNode.getSelectedThings();
					}
					// This node is valuable to keep for evaluate
					queue.offer(includeNode);
				}

				DecisionNode excludeNode = new DecisionNode();
				excludeNode.setLevel(node.getLevel() + 1);
				excludeNode.setWeight(node.getWeight());
				excludeNode.setProfit(node.getProfit());
				excludeNode.setSelectedThings(node.getSelectedThings());
				BigDecimal possibleNewProfitIfOut = calculateMaxNodeProfit(excludeNode);
				excludeNode.setBound(possibleNewProfitIfOut);
				if (possibleNewProfitIfOut.compareTo(profit) > 0) {
					// This node is valuable to keep for evaluate
					queue.offer(excludeNode);
				}
			}
		}
		return profit;
	}

	private boolean fits(BigDecimal currentWeight, BigDecimal thingWeight) {
		return this.packageWeightLimit.compareTo(currentWeight.add(thingWeight)) >= 0;

	}

	private BigDecimal calculateMaxNodeProfit(DecisionNode dNode) {
		BigDecimal currentProfit = dNode.getProfit();
		// Accumulated weight until now
		BigDecimal currentWeight = dNode.getWeight();

		// start index to including following things
		for (int i = dNode.getLevel() + 1; i < this.thingsArray.length; i++) {
			Thing t = this.thingsArray[i];
			if (fits(currentWeight, t.getWeight())) {
				currentWeight = currentWeight.add(t.getWeight());
				currentProfit = currentProfit.add(t.getCost());
			} else {
				// How much room are
				BigDecimal remainder = this.packageWeightLimit.subtract(currentWeight);
				// Fill the theoretical cost than can be added if the thing were divisible
				currentProfit = currentProfit.add(remainder.multiply(t.getValue()));
				// end the loop
				break;
			}
		}
		return currentProfit;
	}
}
