package payment.server;

public class TransactionEntity {
	private Integer buyersId;
	private Integer amount;
	
	public TransactionEntity(Integer buyersId, Integer amount) {
		super();
		this.buyersId = buyersId;
		this.amount = amount;
	}
	
	public Integer getBuyersId() {
		return buyersId;
	}
	public void setBuyersId(Integer buyersId) {
		this.buyersId = buyersId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
