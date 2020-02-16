package com.mobiquityinc.packer.model;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Class to hold thing data
 * The concept of identity or uniqueness is given by the indexNumber attribute, 
 * if two Things have the same indexNumber then are equal
 * @author ricardo.cortes
 *
 */
@ToString
@RequiredArgsConstructor
public class Thing {
	private final BigDecimal MAX_COST = new BigDecimal("100");
	private final BigDecimal MAX_VALUE = MAX_COST.add(BigDecimal.ONE);
	
	@Getter private final int indexNumber;
	@Getter private final BigDecimal weight;
	@Getter private final BigDecimal cost;
	private static final int SCALE = 3;
	
	
	public BigDecimal getValue() {
		BigDecimal value = MAX_VALUE;
		//Is it positive weight?
		if((weight.signum() == 1)) {
			value = cost.divide(weight, SCALE, ROUND_UP);
		}
		return value;
	}
	
	@Override public boolean equals(Object o) {
	    if (o == this) {
	    	return true;
	    }
	    if (!(o instanceof Thing)) { 
	    	return false;
	    }
	    Thing other = (Thing) o;
	    return other.getIndexNumber() == indexNumber;
	  }
	  
	  @Override public int hashCode() {
		Integer integer = indexNumber;
	    return integer.hashCode();
	  }
}
