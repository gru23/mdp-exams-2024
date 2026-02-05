package net.etfbl.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionEntity {
	private String date;
	private int unitsCount;
	private BigDecimal unitPrice;
	private String comment;
	
	public TransactionEntity() {
		super();
	}

	public TransactionEntity(int unitsCount, BigDecimal unitPrice, String comment) {
		super();
		this.date = LocalDate.now().toString();
//		this.date = LocalDate.of(2025, 1, 6).toString();
		this.unitsCount = unitsCount;
		this.unitPrice = unitPrice;
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUnitsCount() {
		return unitsCount;
	}

	public void setUnitsCount(int unitsCount) {
		this.unitsCount = unitsCount;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "TransactionEntity [date=" + date + ", unitsCount=" + unitsCount + ", unitPrice=" + unitPrice
				+ ", comment=" + comment + "]";
	}
}
