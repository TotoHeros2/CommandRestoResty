package ch.goldenmango.commandresto.client.bean;


import java.io.Serializable;


public class Plat implements Serializable{
	
	private Integer id;
	private String descr;
	private Double prix;
	private String sousFamille;
	private String phonetique;
	private String imageUrl;



	public Plat() {
		super();
	}

	public Plat(Integer id, String descr, Double prix,String sousFamille, String imageUrl) {
		this.id = id;
		this.descr = descr;
		this.prix = prix;
		this.sousFamille = sousFamille;
		this.setImageUrl(imageUrl);
	}




	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public Double getPrix() {
		return prix;
	}




	public void setPrix(Double prix) {
		this.prix = prix;
	}




	public String getSousFamille() {
		return sousFamille;
	}




	public void setSousFamille(String sousFamille) {
		this.sousFamille = sousFamille;
	}




	public String getPhonetique() {
		return phonetique;
	}

	public void setPhonetique(String phonetique) {
		this.phonetique = phonetique;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getDescr();
	}
	

}
