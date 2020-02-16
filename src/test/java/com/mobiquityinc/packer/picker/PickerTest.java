package com.mobiquityinc.packer.picker;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import com.mobiquityinc.packer.model.Thing;

class PickerTest {
	@Test
	void testPackEmptyList() {
		List<Thing> emptyThingsList = new ArrayList<>();

		Picker picker = new Picker(emptyThingsList, new BigDecimal("1"));
		picker.pack();

		// Should be no things
		assertThat(picker.getSelectedThings(), empty());
	}

	@Test
	void testPackHeavierThing() {
		List<Thing> thingsList = new ArrayList<>();
		Thing thing = new Thing(1, new BigDecimal("53.38"), new BigDecimal("45.0"));
		thingsList.add(thing);
		BigDecimal boxCapacity = thing.getWeight().subtract(BigDecimal.ONE);

		Picker picker = new Picker(thingsList, boxCapacity);
		picker.pack();

		// Should be no things
		assertThat(picker.getSelectedThings(), empty());
	}
	
	@Test
	public void testSortThingListByValueDesc() {
		List<Thing> list = new ArrayList<>(3);
		Thing a = new Thing(1, new BigDecimal("53.38"), new BigDecimal("45.0"));
		Thing b = new Thing(2, new BigDecimal("88.62"), new BigDecimal("98.0"));
		Thing c = new Thing(3, new BigDecimal("78.48"), new BigDecimal("3.0"));
		list.add(a);
		list.add(b);
		list.add(c);
		list.sort(Picker.COMPARE_THING_BY_VALUE_DESC);
		assertThat(list, isInDescendingOrdering());
	}

	@Test
	void testPackSoloList() {
		List<Thing> oneThingList = new ArrayList<>(1);
		Thing thing = new Thing(1, new BigDecimal("53.38"), new BigDecimal("45.0"));
		oneThingList.add(thing);

		Picker picker = new Picker(oneThingList, thing.getWeight());
		picker.pack();

		assertThat("Thing not found and should be there", picker.getSelectedThings(), hasItem(thing));

	}

	@Test
	void testPackSimplestList() {
		// Just create few things for simple test
		List<Thing> thingsList = new ArrayList<>();
		Thing a = new Thing(1, new BigDecimal("53.38"), new BigDecimal("45.0"));
		Thing b = new Thing(2, new BigDecimal("88.62"), new BigDecimal("98"));
		Thing c = new Thing(3, new BigDecimal("78.48"), new BigDecimal("3.0"));
		Thing expectedThing = new Thing(4, new BigDecimal("72.30"), new BigDecimal("76"));
		Thing e = new Thing(5, new BigDecimal("30.18"), new BigDecimal("9"));
		Thing f = new Thing(6, new BigDecimal("46.34"), new BigDecimal("48"));

		BigDecimal boxCapacity = new BigDecimal("81");

		thingsList.add(a);
		thingsList.add(b);
		thingsList.add(c);
		thingsList.add(expectedThing);
		thingsList.add(e);
		thingsList.add(f);

		Picker picker = new Picker(thingsList, boxCapacity);
		picker.pack();

		assertThat("Expected thing not found and should be there", picker.getSelectedThings(), hasItem(expectedThing));
	}
	
	@Test
	void testPackNoMostValuableList() {
		// Just create few things for simple test
		List<Thing> thingsList = new ArrayList<>();
		Thing mostValuable = new Thing(1, new BigDecimal("3.00"), new BigDecimal("3.5"));
		Thing a = new Thing(2, new BigDecimal("2.00"), new BigDecimal("2.0"));
	    Thing b = new Thing(3, new BigDecimal("2.00"), new BigDecimal("2.0"));
	    thingsList.add(a);
		thingsList.add(b);
		thingsList.add(mostValuable);
	
		BigDecimal boxCapacity = new BigDecimal("4");

		Picker picker = new Picker(thingsList, boxCapacity);
		picker.pack();

		assertThat("Not expected thing found ", picker.getSelectedThings(), not(hasItem(mostValuable)));
		assertThat("Expected thing not found ", picker.getSelectedThings(), hasItem(a));
		assertThat("Expected thing not found ", picker.getSelectedThings(), hasItem(b));
	}
	
	@Test
	void testPackNoMostValuablesList() {
		// Just create few things for simple test
		List<Thing> thingsList = new ArrayList<>();
		Thing mostValuable = new Thing(1, new BigDecimal("3.00"), new BigDecimal("3.5"));
		Thing mostValuableOther = new Thing(2, new BigDecimal("3.00"), new BigDecimal("3.5"));
		for(int i = 3; i < 5; i++) {
			thingsList.add(new Thing(i, new BigDecimal("2.00"), new BigDecimal("2.0")));
		}
		thingsList.add(mostValuable);
		thingsList.add(mostValuableOther);
	
		BigDecimal boxCapacity = new BigDecimal("4");

		Picker picker = new Picker(thingsList, boxCapacity);
		picker.pack();
		
		assertEquals(2, picker.getSelectedThings().size());
		assertThat("Not expected thing found ", picker.getSelectedThings(), not(hasItem(mostValuable)));
		assertThat("Not expected thing found ", picker.getSelectedThings(), not(hasItem(mostValuableOther)));
	}
	
	@Test
	void testPackProvidedList() {
		// Just create few things for simple test
		List<Thing> thingsList = new ArrayList<>();
		Thing a = new Thing(1, new BigDecimal("85.31"), new BigDecimal("29"));
		Thing b = new Thing(2, new BigDecimal("14.55"), new BigDecimal("74"));
		Thing c = new Thing(3, new BigDecimal("3.98"), new BigDecimal("16"));
		Thing d = new Thing(4, new BigDecimal("26.24"), new BigDecimal("55"));
		Thing e = new Thing(5, new BigDecimal("63.69"), new BigDecimal("52"));
		Thing f = new Thing(6, new BigDecimal("76.25"), new BigDecimal("75"));
		Thing g = new Thing(7, new BigDecimal("60.02"), new BigDecimal("74"));
		Thing h = new Thing(8, new BigDecimal("93.18"), new BigDecimal("35"));
		Thing i = new Thing(9, new BigDecimal("89.95"), new BigDecimal("78"));

		BigDecimal boxCapacity = new BigDecimal("75");

		thingsList.add(a);
		thingsList.add(b);
		thingsList.add(c);
		thingsList.add(d);
		thingsList.add(e);
		thingsList.add(f);
		thingsList.add(g);
		thingsList.add(h);
		thingsList.add(i);

		Picker picker = new Picker(thingsList, boxCapacity);
		picker.pack();

		assertThat("Expected thing not found ", picker.getSelectedThings(), hasItem(b));
		assertThat("Expected thing not found ", picker.getSelectedThings(), hasItem(g));
	}
	
	private Matcher<? super List<Thing>> isInDescendingOrdering() {
		return new TypeSafeMatcher<List<Thing>>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Provided list is not order by value (cost / weight)");
			}

			@Override
			protected boolean matchesSafely(List<Thing> thingsLst) {
				for (int i = 0; i < thingsLst.size() - 1; i++) {
					if (thingsLst.get(i).getValue().compareTo(thingsLst.get(i + 1).getValue()) <= 0)
						return false;
				}
				return true;
			}
		};
	}

}
