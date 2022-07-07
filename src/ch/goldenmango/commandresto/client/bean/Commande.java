package ch.goldenmango.commandresto.client.bean;

import java.io.Serializable;


public class Commande implements  Serializable{
	
	private Plat plat;
//	private int quantite;
	private double prix;
	private int numero;
	private boolean rizRemplacement;
	private Piment piment;

	public Commande() {
		// TODO Auto-generated constructor stub
	}

	public Commande(Plat plat,int numero) {
		super();
		this.plat = plat;
//		this.quantite = 1;
		this.prix = plat.getPrix();// init with prix du plat
		this.numero = numero;
		this.piment = Piment.MOYEN;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Plat getPlat() {
		return plat;
	}

	public void setPlat(Plat plat) {
		this.plat = plat;
	}

	public boolean isRizRemplacement() {
		return rizRemplacement;
	}

	public Piment getPiment() {
		return piment;
	}

	public void setRizRemplacement(boolean rizRemplacement) {
		this.rizRemplacement = rizRemplacement;
	}

	public void setPiment(Piment piment) {
		this.piment = piment;
	}

//	public int getQuantite() {
//		return quantite;
//	}
//
//	public void setQuantite(int quantite) {
//		this.quantite = quantite;
//	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

}
