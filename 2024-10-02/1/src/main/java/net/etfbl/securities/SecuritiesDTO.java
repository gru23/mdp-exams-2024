package net.etfbl.securities;

public class SecuritiesDTO {
	private String id;
	private String lastTransactionDate;
	
	public SecuritiesDTO(String id, String date) {
		this.id = id;
		this.lastTransactionDate = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(String lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	@Override
	public String toString() {
		return "SecuritiesDTO [id=" + id + ", lastTransactionDate=" + lastTransactionDate + "]";
	}
}
