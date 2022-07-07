package ch.goldenmango.commandresto.client.bean;

import java.io.Serializable;



public class TransactionId implements  Serializable{

	private String transactionId;
	
	private Erreur error;

	public Erreur getError() {
		return error;
	}

	public void setError(Erreur error) {
		this.error = error;
	}	

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "TransactionId [transactionId=" + transactionId + ", error=" + error + "]";
	}
}
