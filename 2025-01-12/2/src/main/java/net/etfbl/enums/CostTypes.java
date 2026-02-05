package net.etfbl.enums;

import java.math.BigDecimal;

public enum CostTypes {
	FIXED(new BigDecimal("0.15")),
	BOOKS_REVIEW(new BigDecimal("0.07")),
	ORDER(new BigDecimal("1.50"));
	
	private final BigDecimal cost;
	
	CostTypes(BigDecimal cost) {
		this.cost = cost;
	}
	
	public BigDecimal getCost() {
        return cost;
    }
}
