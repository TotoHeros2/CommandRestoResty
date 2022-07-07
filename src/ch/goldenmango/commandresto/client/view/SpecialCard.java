package ch.goldenmango.commandresto.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.bean.Plat;
import ch.goldenmango.commandresto.client.event.AppEventBus;
import ch.goldenmango.commandresto.client.event.SelectPlatEvent;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialToast;

public class SpecialCard extends Composite {

	private static SpecialCardUiBinder uiBinder = GWT.create(SpecialCardUiBinder.class);

	interface SpecialCardUiBinder extends UiBinder<Widget, SpecialCard> {
	}


	private Plat plat;
	
	@UiField
	MaterialLabel phonetique;
	
	@UiField
	MaterialLabel desc;
	
	@UiField
	MaterialLabel prix;

	@UiField
	MaterialImage image;
	
	public SpecialCard(Plat plat,String noByType) {
		this.plat = plat;
		initWidget(uiBinder.createAndBindUi(this));
//		titre.setText(plat.getTitre());
		desc.setText(plat.getDescr());
		prix.setText(Panier.formatNumer(plat.getPrix()));
		phonetique.setText(noByType + " " + plat.getPhonetique());
		if (plat.getImageUrl() != null)
		{
			image.setUrl(plat.getImageUrl());
			image.setVisible(true);
		}
	}



	@UiHandler("titre")
	void onClick(ClickEvent e) {
		AppEventBus.EVENT_BUS.fireEvent(new SelectPlatEvent(new Commande(plat,0)));	
		MaterialToast.fireToast(plat.getDescr() + " a été envoyé à votre panier !","rounded");				
	}	

}
