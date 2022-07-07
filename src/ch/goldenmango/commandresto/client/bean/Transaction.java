package ch.goldenmango.commandresto.client.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Transaction implements Serializable{
//	"currency": "CHF",
//	"refno": "Test-1234",
//	"amount": 1000,
//	"paymentMethods": ["VIS","ECA","PAP","TWI"],
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currency;
	private String refno;
	private int amount;
	
//	@Expose(serialize = false, deserialize = false) 
//	private List<Commande> commandes = new ArrayList<Commande>();
	

	public Transaction() {
		// TODO Auto-generated constructor stub
	}


	public String getCurrency() {
		return currency;
	}


	public String getRefno() {
		return refno;
	}


	public int getAmount() {
		return amount;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public void setRefno(String refno) {
		this.refno = refno;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


//	public List<Commande> getCommandes() {
//		return commandes;
//	}
//
//
//	public void setCommandes(List<Commande> commandes) {
//		this.commandes = commandes;
//	}
//	public void addToCommandes(Commande commande) {
//		this.commandes.add(commande);
//	}
}
