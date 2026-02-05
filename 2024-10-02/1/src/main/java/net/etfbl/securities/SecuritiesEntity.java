package net.etfbl.securities;

import java.util.List;

import net.etfbl.enums.SecuritiesType;
import net.etfbl.transactions.TransactionEntity;

public class SecuritiesEntity {
	private String id;
	private String emitentName;
	private SecuritiesType type;
	private List<TransactionEntity> transactions;
	
	public SecuritiesEntity() {
		super();
	}

	public SecuritiesEntity(String id, String emitentName, SecuritiesType type, List<TransactionEntity> transactions) {
		super();
		this.id = id;
		this.emitentName = emitentName;
		this.type = type;
		this.transactions = transactions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmitentName() {
		return emitentName;
	}

	public void setEmitentName(String emitentName) {
		this.emitentName = emitentName;
	}

	public SecuritiesType getType() {
		return type;
	}

	public void setType(SecuritiesType type) {
		this.type = type;
	}

	public List<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "SecuritiesEntity [id=" + id + ", emitentName=" + emitentName + ", type=" + type + ", transactions="
				+ transactions + "]";
	}
}
