package ch.goldenmango.commandresto.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.event.AppEventBus;
import ch.goldenmango.commandresto.client.event.SelectPlatEvent;
import ch.goldenmango.commandresto.client.event.SelectPlatHandler;
import gwt.material.design.client.constants.ImageType;
import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;


public class MainContent extends Composite  {
    private boolean toggle = true;
	public interface GlobalImageResources extends ClientBundle {
//		@Source("imageplat.jpg")
		@Source("barreplat.jpg")
		ImageResource banner();
		
		@Source("logo_465x320.png")
		ImageResource logo();

	}
	GlobalImageResources imgRes =  GWT.<GlobalImageResources>create(GlobalImageResources.class);

	@UiField(provided = true)
	MaterialImage banner = new MaterialImage(imgRes.banner(), ImageType.DEFAULT);
	
	@UiField(provided = true)
	Image logo = new Image(imgRes.logo());

    @UiField
    MaterialNavBar navBar;


    @UiField
    MaterialColumn mainPanel, rightPanel;

    @UiField
    MaterialRow mainHeader;
    
    @UiField
    MaterialBadge panierCompteur;
    
    @UiField
    MaterialLink openPanier;
    
    @UiField
    Panier panier;

    private int compteur = 0;
    
	private static MainContentUiBinder uiBinder = GWT.create(MainContentUiBinder.class);

	interface MainContentUiBinder extends UiBinder<Widget, MainContent> {
	}

	public MainContent() {
		initWidget(uiBinder.createAndBindUi(this));
		panierCompteur.setId("shoppincart");
		AppEventBus.EVENT_BUS.addHandler(SelectPlatEvent.TYPE, new SelectPlatHandler() {
			
			@Override
			public void onSelectPlat(SelectPlatEvent event) {
				Commande commande = event.getCommande();
				ArrayList<Commande> commandes = event.getCommandes();// from fail payment
				if (commandes != null)
				{
					compteur += commandes.size();
				}
				else 
				{

					if (commande == null) // only refresh prix total
					{
						return;
					}
					if (event.isRemove())
					{
						compteur--;
					}
					else
						compteur++;
				}

				panierCompteur.setText(String.valueOf(compteur));
		        MaterialAnimation infiniteAnimation = new MaterialAnimation();
		        infiniteAnimation.setDelay(0);
		        infiniteAnimation.setTransition(Transition.SHAKE);
		        infiniteAnimation.setDuration(1000);
		        infiniteAnimation.setInfinite(false);
		        infiniteAnimation.animate(openPanier);
			}	
		});
	}

    @UiHandler("openPanier")
    void onInfo(ClickEvent e) {
    	panier.setToComandePage(true);// show comand page (no pay page)
    	panier.draw();
        if(toggle){

            rightPanel.setVisible(true);
            mainHeader.setVisible(false);
            toggle = false;
        }else{
            hidePanel();
        }
    }

    @UiHandler("closeLink")
    void onClose(ClickEvent e) {
    	panier.setToComandePage(true);
    	panier.draw();
        hidePanel();
    }

    private void hidePanel() {

        rightPanel.setVisible(false);
        mainHeader.setVisible(true);
        toggle = true;
    }


}

