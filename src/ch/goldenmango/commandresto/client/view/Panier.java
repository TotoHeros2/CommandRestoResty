package ch.goldenmango.commandresto.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.bean.Paiement;
import ch.goldenmango.commandresto.client.bean.Plat;
import ch.goldenmango.commandresto.client.bean.Transaction;
import ch.goldenmango.commandresto.client.bean.TransactionId;
import ch.goldenmango.commandresto.client.event.AppEventBus;
import ch.goldenmango.commandresto.client.event.SelectPlatEvent;
import ch.goldenmango.commandresto.client.event.SelectPlatHandler;
import ch.goldenmango.commandresto.client.service.DatatransInterface;
import ch.goldenmango.commandresto.client.service.PlatService;
import ch.goldenmango.commandresto.client.service.RestoService;

import ch.goldenmango.commandresto.client.view.payement.Datatrans;
import ch.goldenmango.commandresto.client.view.payement.DatatransOption;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class Panier extends Composite  {
	
    private static final PlatService platService = GWT.create(PlatService.class);
    
    private static final DatatransInterface datatransService = GWT.create(DatatransInterface.class);


//	private static NumberFormat currFmt =  NumberFormat.getCurrencyFormat();
	private static NumberFormat myFmt =  NumberFormat.getFormat("0.00 CHF");


	private static PanierUiBinder uiBinder = GWT.create(PanierUiBinder.class);

	interface PanierUiBinder extends UiBinder<Widget, Panier> {
	}
	private int numero = 0;
	private double totalPrix = 0;
	
	private String transactionId = "";
	private Integer noCommande = null;
	
	private Date lastTimeToPay = null;


	@UiField
	MaterialPanel contentPanel,formPanel;
	
	@UiField
	MaterialLabel total;
	
	@UiField
	MaterialLabel votrePanier;
	
	@UiField
	MaterialButton commandButton,payButton;
	
	@UiField
	MaterialTextArea remarquesArea;

	@UiField
	MaterialTextBox nom,email,tel;
	
	@UiField
	MaterialComboBox<String> hour;
	

//	private ArrayList<Commande> commandes = new ArrayList<Commande>();
	private ArrayList<Plat> plats = new ArrayList<Plat>();// plat individuel car piment + "sans" par plat
		
	private boolean toCommandePage = true; 
	public Panier() {
		initWidget(uiBinder.createAndBindUi(this));
		// test 1 ligne
//		Plat plat = new Plat(1000,"Poulet sauté aux noix de cajou",18.50,"Poulet");
//
//		Commande commande = new Commande(plat/*RestoService.get().getListCurrys().get(0)*/);
//		contentPanel.add( new LigneCommande(commande));
		
		AppEventBus.EVENT_BUS.addHandler(SelectPlatEvent.TYPE, new SelectPlatHandler() {
			
			@Override
			public void onSelectPlat(SelectPlatEvent event) {
				Commande commande = event.getCommande();
				ArrayList<Commande> commandes = event.getCommandes();
				if (commandes != null) // from failed payment
				{
					for (Commande newcommande :commandes)
					{
						numero++;
						LigneCommande ligneCommande = new LigneCommande(newcommande);
						contentPanel.add(ligneCommande);
						totalPrix += newcommande.getPrix();
					}
					reorderPanels();
				}
				else // from user choice
				{
					if (commande == null)// only update prix
					{
						redrawTotalPrice();
//						return;
					}
					else if (event.isRemove())
					{
						numero--;
						contentPanel.remove(event.getPanel());
						totalPrix -= commande.getPrix();
						reorderPanels();
					}
					// added from selection
					else
					{
						numero++;
						Commande newcommande = new Commande(commande.getPlat(),numero);
						LigneCommande ligneCommande = new LigneCommande(newcommande);
						contentPanel.add(ligneCommande);
						totalPrix += commande.getPrix();
					}
				}
				if (totalPrix > 0)
				{
					commandButton.setEnabled(true);
				}
				else
				{
					commandButton.setEnabled(false);
				}
				String sTotal = Panier.formatNumer(totalPrix);
				total.setText(sTotal);
				if (numero == 0)
				{
					votrePanier.setText("Votre panier est vide");
					
				}
				else
					votrePanier.setText("Votre panier de " + sTotal + " :");
			}
		});

	}

	private void getOpenTimes() {
		platService.commandTimes(new MethodCallback<List<String>>() {
			
			@Override
			public void onSuccess(Method method, List<String> response) {
				if (response.size() == 0)
				{
					Window.alert("le restaurant est maintenant fermé. Merci de revenir pour la prochaine ouverture");
					lastTimeToPay = null;
					return;
				}
				lastTimeToPay = new Date();

				for (String choix : response)
				{
					hour.addItem(choix);
				}
				String lastChoice = response.get(response.size() - 1);
				String[] choice = lastChoice.split(":");
				lastTimeToPay.setHours(Integer.valueOf(choice[0]));
				lastTimeToPay.setMinutes(Integer.valueOf(choice[1]));
				lastTimeToPay.setSeconds(0);
				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				
			}
		});
//		RPCPlatService.Util.getInstance().commandTimes(new AsyncCallback<ArrayList<String>>() {
//			
//			@Override
//			public void onSuccess(ArrayList<String> result) {
//				if (result.size() == 0)
//				{
//					Window.alert("le restaurant est maintenant fermé. Merci de revenir pour la prochaine ouverture");
//					lastTimeToPay = null;
//					return;
//				}
//				lastTimeToPay = new Date();
//
//				for (String choix : result)
//				{
//					hour.addItem(choix);
//				}
//				String lastChoice = result.get(result.size() - 1);
//				String[] choice = lastChoice.split(":");
//				lastTimeToPay.setHours(Integer.valueOf(choice[0]));
//				lastTimeToPay.setMinutes(Integer.valueOf(choice[1]));
//				lastTimeToPay.setSeconds(0);
//
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}

	protected void redrawTotalPrice() {
		List<Widget> ligneCommandes = contentPanel.getChildrenList();
		totalPrix = 0;
		for (Widget widget : ligneCommandes)
		{
			LigneCommande ligneCommande = (LigneCommande)widget;
			totalPrix += ligneCommande.getCommande().getPrix();
		}
		total.setText(Panier.formatNumer(totalPrix));

	}

	private void reorderPanels()
	{
		List<Widget> ligneCommandes = contentPanel.getChildrenList();
		int index = 1;

		for (Widget widget : ligneCommandes)
		{
			LigneCommande ligneCommande = (LigneCommande)widget;
			ligneCommande.getCommande().setNumero(index);
			ligneCommande.redraw();
			index++;
		}
	}
	public static String formatNumer(double prix)
	{
		return myFmt.format(prix);
//		return currFmt.format(prix).replaceFirst("US\\$", "CHF ");
	}
	
	@UiHandler("commandButton")
	void onCommande(ClickEvent e) {
//		initTransService();
		// ask server
		initTransServiceFromServerV2();
		//
		getOpenTimes();
		setToComandePage(false);
		draw();
	}
	


	@UiHandler("payButton")
	void onPay(ClickEvent e) {
		Date now = new Date();
		if (now.after(lastTimeToPay))
		{
			Window.alert("le restaurant est maintenant fermé. Merci de faire votre commande pour la prochaine ouverture");
			Window.Location.assign("https://golden-mango.ch");
		}
		
		// create a complete Paiement
		Paiement paiement = new Paiement();
		List<Widget> ligneCommandes = contentPanel.getChildrenList();
		totalPrix = 0;
		for (Widget widget : ligneCommandes)
		{
			LigneCommande ligneCommande = (LigneCommande)widget;
			Commande commande = ligneCommande.getCommande();
			totalPrix += commande.getPrix();
			paiement.addToCommandes(commande);
		}
		paiement.setAmount(totalPrix);
		paiement.setNoCommande(noCommande);
		paiement.setNom(nom.getText());
		paiement.setTrId(transactionId);
		paiement.setEmail(email.getText());
		paiement.setHeure(hour.getValue().get(0));
		paiement.setTelephone(tel.getText());
		paiement.setRemarques(remarquesArea.getText());
//		RPCPlatService.Util.getInstance().payer(paiement, new AsyncCallback<Void>() {
//			
//			@Override
//			public void onSuccess(Void result) {
////						Datatrans.startPayment(DatatransOption.create(transactionId)); // light box
//
//				Window.Location.assign("https://pay.sandbox.datatrans.com/v1/start/" + transactionId); // redirect				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		datatransService.payer(paiement, new MethodCallback<Void>() {
			
			@Override
			public void onSuccess(Method method, Void response) {
				Window.Location.assign("https://pay.sandbox.datatrans.com/v1/start/" + transactionId); // redirect				
				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	private void initTransServiceFromServerV2() {
		// create a empty Paiement
		Paiement paiement = new Paiement();
		List<Widget> ligneCommandes = contentPanel.getChildrenList();
		totalPrix = 0;
		for (Widget widget : ligneCommandes)
		{
			LigneCommande ligneCommande = (LigneCommande)widget;
			Commande commande = ligneCommande.getCommande();
			totalPrix += commande.getPrix();
			paiement.addToCommandes(commande);
		}
		paiement.setAmount((int)totalPrix*100);
		datatransService.commande(paiement, new MethodCallback<Paiement>() {
			
			@Override
			public void onSuccess(Method method, Paiement result) {
				if (result.getTrId() == null)
				{
					Window.alert("Le système de paiement ne répond pas. Merci de réessayer plus tard");
					return;
				}
				transactionId = result.getTrId();	
				noCommande = result.getNoCommande();
				GWT.log(transactionId + " from server");
				payButton.setVisible(true);				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				GWT.log(exception.toString());		
				Window.alert("Le système de paiement ne répond pas. Merci de réessayer plus tard");
				
			}
		});
		
		
//		RPCPlatService.Util.getInstance().commander(paiement, new AsyncCallback<Paiement>() {
//			
//			@Override
//			public void onSuccess(Paiement result) {
//				if (result.getTrId() == null)
//				{
//					Window.alert("Le système de paiement ne répond pas. Merci de réessayer plus tard");
//					return;
//				}
//				transactionId = result.getTrId();	
//				noCommande = result.getNoCommande();
//				GWT.log(transactionId + " from server");
//				payButton.setVisible(true);
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				GWT.log(caught.toString());				
//			}
//		});
	}
	// abandon
	private void initTransServiceFromServer() {
       	Transaction transaction = new Transaction();
		List<Widget> ligneCommandes = contentPanel.getChildrenList();
		totalPrix = 0;
		for (Widget widget : ligneCommandes)
		{
			LigneCommande ligneCommande = (LigneCommande)widget;
			Commande commande = ligneCommande.getCommande();
			totalPrix += commande.getPrix();
//			transaction.addToCommandes(commande);
		}
		total.setText(Panier.formatNumer(totalPrix));
       	transaction.setCurrency("CHF");
       	transaction.setRefno(String.valueOf(transaction.hashCode()));
       	transaction.setAmount((int)totalPrix*100);
//		RPCPlatService.Util.getInstance().initTransaction(transaction, new AsyncCallback<String>() {
//			
//			@Override
//			public void onSuccess(String result) {
//				transactionId = result;
//				if (!"error".equalsIgnoreCase(transactionId))
//					payButton.setVisible(true);
//				GWT.log(transactionId + " from server");
//
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				GWT.log(caught.toString());
//				
//			}
//		});		
	}	
	
//	private void initTransService() {
//	       Resource datatransRes = new Resource("https://api.sandbox.datatrans.com/v1/transactions");
////	       DatatransServiceAsync datatransService = GWT.create(DatatransServiceAsync.class);
////	       ((RestServiceProxy) datatransService).setResource(datatransRes);	
//	       Method method = datatransRes.post()/*.user("1100027912").password("Datatrans12_")*/;
//	       method.header("Content-Type", "application/json");
////	       String originalInput = "1100027912:r5xnFHfloBc6Qqxc";
////	       String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
//	       method.header("Authorization", "Basic " + "MTEwMDAyNzkxMjpyNXhuRkhmbG9CYzZRcXhj");
//	       
//	       
//	       	Transaction transaction = new Transaction();
//	       	transaction.setCurrency("CHF");
//	       	transaction.setRefno("TestGMJavaV1");
//	       	transaction.setAmount(1350);
//			TransactionCodec codec = GWT.create(TransactionCodec.class);
//			JSONValue jsonValue = codec.encode(transaction);
//			GWT.log(jsonValue.toString());
//	       method.json(jsonValue);
//	       method.send(new JsonCallback() {
//			
//			@Override
//			public void onSuccess(Method method, JSONValue response) {
//				TransactionIdCodec codec = GWT.create(TransactionIdCodec.class);
//				TransactionId tid= codec.decode(response);
//				GWT.log(tid.getTransactionId());
//				transactionId = tid.getTransactionId();
//				payButton.setVisible(true);
//			}
//			
//			@Override
//			public void onFailure(Method method, Throwable exception) {
//				GWT.log(exception.toString());
//
//			};
//		});
//	}

	public void setToComandePage(boolean b) {
		toCommandePage = b;		
	}

	public boolean isToCommandePage() {
		return toCommandePage;
	}
	public void draw()
	{
		if (isToCommandePage())
		{
			commandButton.setVisible(true);
			payButton.setVisible(false);
			contentPanel.setVisible(true);
			formPanel.setVisible(false);
			votrePanier.setVisible(true);

		}
		else
		{
			payButton.setVisible(true);
			commandButton.setVisible(false);
			contentPanel.setVisible(false);
			formPanel.setVisible(true);
			votrePanier.setVisible(false);
			payButton.setEnabled(false);

			if (nom.getText().length() > 0 && hour.getValue().size() > 0)
			{
				payButton.setEnabled(true);
			}
			
		}			
	}
	@UiHandler("nom")
	public void onChange(ChangeEvent event) {
		draw();
	}


}
