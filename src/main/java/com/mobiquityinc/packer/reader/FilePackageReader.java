package com.mobiquityinc.packer.reader;

import java.util.LinkedList;
import java.util.List;

import com.mobiquityinc.packer.model.Thing;

public class FilePackageReader {

	public List<Thing> readThings(String filePath){
		List<Thing> result = new LinkedList<>();
		return sortDescendingThingByValue(result);
	}
	
	public static List<Thing> sortDescendingThingByValue(List<Thing> list) {
		list.sort((Thing a, Thing b) -> b.getValue().compareTo(a.getValue()));
		return list;
	}
	
	
	
}
