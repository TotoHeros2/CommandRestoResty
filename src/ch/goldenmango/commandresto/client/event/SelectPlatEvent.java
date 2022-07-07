package ch.goldenmango.commandresto.client.event;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.view.LigneCommande;

public class SelectPlatEvent extends GwtEvent<SelectPlatHandler> {

	public static final Type<SelectPlatHandler> TYPE = new Type<SelectPlatHandler>();
	
	
//	private Plat plat;
	private Commande commande;
	
	private ArrayList<Commande> commandes;
	
	private LigneCommande panel;
	
	private boolean remove;
	
	public SelectPlatEvent(Commande commande) {
		this.commande = commande;
	}
	
	public SelectPlatEvent(ArrayList<Commande> commandes) {
		this.setCommandes(commandes);
	}
	public SelectPlatEvent(Commande commande,LigneCommande panel,boolean remove) {
		this.commande = commande;
		this.remove = remove;
		this.panel = panel;
	}
	
	
	@Override
	protected void dispatch(SelectPlatHandler handler) {
		handler.onSelectPlat(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SelectPlatHandler> getAssociatedType() {
		return TYPE;
	}
public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	//	public Plat getPlat() {
//		return plat;
//	}
//	public void setPlat(Plat plat) {
//		this.plat = plat;
//	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public LigneCommande getPanel() {
		return panel;
	}
	public void setPanel(LigneCommande panel) {
		this.panel = panel;
	}

	public ArrayList<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(ArrayList<Commande> commandes) {
		this.commandes = commandes;
	}




}
