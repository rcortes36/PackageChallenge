package com.mobiquityinc.packer.model;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;

import lombok.Data;
/**
 * @author ricardo.cortes
 *
 */
@Data
public class Thing {
	private final int indexNumber;
	private final BigDecimal weight;
	private final BigDecimal cost;
	private static final int SCALE = 3;
	private final BigDecimal MAX_COST = new BigDecimal("100");
	private final BigDecimal MAX_VALUE = MAX_COST.add(BigDecimal.ONE);
	
	public BigDecimal getValue() {
		BigDecimal value = MAX_VALUE;
		//Is it positive weight?
		if((weight.signum() == 1)) {
			value = cost.divide(weight, SCALE, ROUND_UP);
		}
		return value;
	}
}
