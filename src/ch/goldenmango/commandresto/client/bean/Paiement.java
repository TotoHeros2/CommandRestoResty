package ch.goldenmango.commandresto.client.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paiement implements  Serializable{
	private static final long serialVersionUID = 1L;

	private int noCommande;
	private double amount;
	private String nom;
	private String telephone;
	private String heure;
	private String email;
	private String trId;
	private String remarques;

	
	private List<Commande> commandes = new ArrayList<Commande>();

	public int getNoCommande() {
		return noCommande;
	}

	public void setNoCommande(int noCommande) {
		this.noCommande = noCommande;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTrId() {
		return trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}
	public void addToCommandes(Commande commande) {
		this.commandes.add(commande);
	}

	public String getRemarques() {
		return remarques;
	}

	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}
}
