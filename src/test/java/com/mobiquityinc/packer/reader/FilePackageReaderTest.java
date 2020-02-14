package com.mobiquityinc.packer.reader;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import com.mobiquityinc.packer.model.Thing;
import com.mobiquityinc.packer.reader.FilePackageReader;

class FilePackageReaderTest {

	@Test
	public void testSortThingListByValue() {
		Thing a = new Thing(1, new BigDecimal("53.38"), new BigDecimal("45.0"));
		Thing b = new Thing(2, new BigDecimal("88.62"), new BigDecimal("98.0"));
		Thing c = new Thing(3, new BigDecimal("78.48"), new BigDecimal("3.0"));

		List<Thing> list = new ArrayList<>(3);
		list.add(a);
		list.add(b);
		list.add(c);
		FilePackageReader.sortDescendingThingByValue(list);
		assertThat(list, isInDescendingOrdering());
		System.out.println(list);
	}

	private Matcher<? super List<Thing>> isInDescendingOrdering() {
		return new TypeSafeMatcher<List<Thing>>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Provided list is not order by value (cost / weight)");
			}

			@Override
			protected boolean matchesSafely(List<Thing> thingsLst) {
				for(int i = 0; i < thingsLst.size() - 1; i++){
					if (thingsLst.get(i).getValue().compareTo(thingsLst.get(i + 1).getValue()) <= 0)
						return false;
				}
				return true;
			}
		};
	}

}
