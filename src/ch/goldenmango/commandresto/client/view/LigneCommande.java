package ch.goldenmango.commandresto.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.bean.Piment;
import ch.goldenmango.commandresto.client.bean.Plat;
import ch.goldenmango.commandresto.client.event.AppEventBus;
import ch.goldenmango.commandresto.client.event.SelectPlatEvent;
import ch.goldenmango.commandresto.client.service.RestoService;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTooltip;

public class LigneCommande extends Composite  {

	private static LigneCommandeUiBinder uiBinder = GWT.create(LigneCommandeUiBinder.class);

	interface LigneCommandeUiBinder extends UiBinder<Widget, LigneCommande> {
	}



	@UiField
	MaterialLabel plat;
	
//	@UiField
////	MaterialLabel quantite;
//	Label quantite;

	
	@UiField
//	MaterialLabel prix;
	Label prix;
	
	
	@UiField	
	MaterialLink add,remove;
	
	@UiField	
	MaterialTooltip tooltip;
	
	
	@UiField	
	MaterialRadioButton doux,moyen,fort;
	
	@UiField	
	MaterialRow pimentLigne;
	
//	MaterialRow remplacementLigne;
	
	
//	@UiField	
//	MaterialCheckBox remplacement;
	
	private Commande commande;

	public LigneCommande(Commande commande) {
		this.commande = commande;
		initWidget(uiBinder.createAndBindUi(this));
		plat.setText(commande.getNumero() +  ") "+commande.getPlat().getDescr());
		tooltip.setText(commande.getPlat().getDescr());

//		quantite.setText("" +commande.getQuantite());
		
		prix.setText(Panier.formatNumer(commande.getPrix()));
		doux.setName("piment" + commande.toString());
		moyen.setName("piment" + commande.toString());
		fort.setName("piment" + commande.toString());
		switch (commande.getPlat().getSousFamille()) {
		case "Dessert":
		case "Boisson":
		case "Entrée":

			break;

		default:
			pimentLigne.setVisible(true);
			Plat platObj = commande.getPlat();
//			if (!platObj.getDescr().toLowerCase().contains("riz") &&  !platObj.getDescr().toLowerCase().contains("nouille"))
//			{
//				remplacementLigne.setVisible(true);
//			}
			break;
		}
		// set if not default after fail payment
//		if (commande.isRizRemplacement())
//		{
//			remplacement.setValue(true);
//		}
		if (commande.getPiment() == Piment.DOUX)
		{
			doux.setValue(true);
		}
		else if (commande.getPiment() == Piment.FORT)
		{
			fort.setValue(true);
		}

	}
	@UiHandler("add")
	void onAdd(ClickEvent e) {
//		commande.setQuantite(commande.getQuantite() + 1);
//		quantite.setText("" +commande.getQuantite());
//		prix.setText(Panier.formatNumer(commande.getPrix()));
		
		AppEventBus.EVENT_BUS.fireEvent(new SelectPlatEvent(commande,this,false));	


	}
	@UiHandler("remove")
	void onRemove(ClickEvent e) {
//		int qte = commande.getQuantite();
//		if (qte == 0)
//		{
//			return;
//		}
//		commande.setQuantite(commande.getQuantite() - 1);
////		quantite.setText("" +commande.getQuantite());
//		prix.setText(Panier.formatNumer(commande.getQuantite()*commande.getPrix()));
		AppEventBus.EVENT_BUS.fireEvent(new SelectPlatEvent(commande,this, true));	


	}
	// remove remlacement
//	<m:MaterialRow ui:field="remplacementLigne" visible="false">
//	<m:MaterialColumn grid="s12">
//			<m:MaterialLabel text="Riz cantonais (+2 CHF) : &#160;" addStyleNames="{style.inline}"></m:MaterialLabel> 	        
//            <m:MaterialCheckBox text="&#160;" ui:field="remplacement" addStyleNames="{style.inline}"/>
//						
//	</m:MaterialColumn>
//</m:MaterialRow>
//    @UiHandler("remplacement")
//    void onCheckBox(ValueChangeEvent<Boolean> e) {
//        if(e.getValue()) {
//        	commande.setRizRemplacement(true);
//        	commande.setPrix(commande.getPlat().getPrix() + RestoService.get().prixRemplacemetRiz());
//        } else {
//        	commande.setRizRemplacement(false);
//        	commande.setPrix(commande.getPlat().getPrix());
//        }
//		prix.setText(Panier.formatNumer(commande.getPrix()));
//		AppEventBus.EVENT_BUS.fireEvent(new SelectPlatEvent((Commande)null));	// recalcul prix dans panier
//
//    }
	
	public Commande getCommande() {
		updateFromUI();
		return commande;
	}
	
	private void updateFromUI() {
		if (doux.getValue())
		{
			commande.setPiment(Piment.DOUX);
		}
		else if (fort.getValue())
		{
			commande.setPiment(Piment.FORT);			
		}
//		if (remplacement.getValue())
//		{
//			commande.setRizRemplacement(true);
//		}
	}
	public void redraw()
	{
		plat.setText(commande.getNumero() +  ") "+commande.getPlat().getDescr());
	}


}
