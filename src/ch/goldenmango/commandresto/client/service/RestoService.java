package ch.goldenmango.commandresto.client.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.bean.Plat;
import ch.goldenmango.commandresto.client.bean.Transaction;
import ch.goldenmango.commandresto.client.bean.TransactionId;
import ch.goldenmango.commandresto.client.bean.TypePlat;
import ch.goldenmango.commandresto.client.event.AppEventBus;
import ch.goldenmango.commandresto.client.event.DataReceivedEvent;
import ch.goldenmango.commandresto.client.event.SelectPlatEvent;
import ch.goldenmango.commandresto.client.view.payement.Datatrans;


public class RestoService {
	
    private static final PlatService platService = GWT.create(PlatService.class);

	static private RestoService service;
	
	private ArrayList<Plat> specialites;
	private ArrayList<Plat> poulets;
	private ArrayList<Plat> boeufs,crevettes,poissons;
	
	private ArrayList<Plat> entrees,porcs,canards;
	private ArrayList<Plat> vegetariens,desserts,boissons;

//	private Double prixRemplacemetRiz;
	


	private RestoService() {
		loadPlats();
		// test datatrans
//		initTransService();
	}

	public static RestoService get()
	{
		if (service == null)
		{
			service = new RestoService();
		}
		return service;
	}

	private void loadPlats() {
//		Plat plat = new Plat("Tomyam Kung", "Soupe pimentée de crevettes géantes la citronnelle, servi avec du riz nature", "22.00");
//		listCurrys.add(plat);
//		plat = new Plat("Tomkha Kai", "Soupe de poulet au galanga et au lait de coco, servi avec du riz nature", "18.50");
//		listCurrys.add(plat);
//		plat = new Plat("Tomyam Kung 2", "Soupe pimentée de crevettes géantes la citronnelle, servi avec du riz nature", "22.00");
//		listCurrys.add(plat);
//		plat = new Plat("Tomkha Kai 2", "Soupe de poulet au galanga et au lait de coco, servi avec du riz nature", "18.50");
//		listCurrys.add(plat);
//		plat = new Plat("Tomyam Kung 3", "Soupe pimentée de crevettes géantes la citronnelle, servi avec du riz nature", "22.00");
//		listCurrys.add(plat);
//		plat = new Plat("Tomkha Kai 3", "Soupe de poulet au galanga et au lait de coco, servi avec du riz nature", "18.50");
//		listCurrys.add(plat);
//		plat = new Plat("Tomyam Kung 4", "Soupe pimentée de crevettes géantes la citronnelle, servi avec du riz nature", "22.00");
//		listCurrys.add(plat);
//		plat = new Plat("Tomkha Kai 4", "Soupe de poulet au galanga et au lait de coco, servi avec du riz nature", "18.50");
//		listCurrys.add(plat);
//		plat = new Plat("Tomyam Kung 5", "Soupe pimentée de crevettes géantes la citronnelle, servi avec du riz nature", "22.00");
//		listCurrys.add(plat);
//		Plat plat = new Plat(1000,"Poulet sauté aux noix de cajou",18.50,"Poulet");
//		specialites = new ArrayList<Plat>();
//		specialites.add(plat);

		// remove rpc
//		RPCPlatService.Util.getInstance().allPlats(new AsyncCallback<ArrayList<TypePlat>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Impossible de contacter le serveur. Merci d'essayer plus tard");
//				
//			}
//
//			@Override
//			public void onSuccess(ArrayList<TypePlat> result) {
////				specialites = result;
//				loadAll(result);
//
//				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent());				
//			}
//		});
		
		platService.getAllPlats(new MethodCallback<List<TypePlat>>() {
			
			@Override
			public void onSuccess(Method method, List<TypePlat> response) {
//				MaterialToast.fireToast("Succes can get all plats !","rounded");	
				loadAll(response);

				AppEventBus.EVENT_BUS.fireEvent(new DataReceivedEvent());	
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Impossible de contacter le serveur. Merci d'essayer plus tard");

//				MaterialToast.fireToast("Impossible de contacter le serveur. Merci d'essayer plus tard","rounded");	
				
			}
		});
		


	}

	private void loadAll(List<TypePlat> results) {
		for (TypePlat typePlat : results)
		{
			switch (typePlat.getDescr()) {
			
			case "Spécialité":
				specialites = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Poulet":
				poulets = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Boeuf":
				boeufs = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Crevette":
				crevettes = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Poisson":
				poissons = (ArrayList<Plat>) typePlat.getPlats();
				break;
				
			case "Entrée":
				entrees = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Porc":
				porcs = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Canard":
				canards = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Végétarien":
				vegetariens = (ArrayList<Plat>) typePlat.getPlats();
				break;				
			case "Dessert":
				desserts = (ArrayList<Plat>) typePlat.getPlats();
				break;
			case "Boisson":
				boissons = (ArrayList<Plat>) typePlat.getPlats();
				break;	
//			case "Remplacement":
//				prixRemplacemetRiz = typePlat.plats().get(0).getPrix();
//				break;		
			default:
				break;
			}
		}
		
	}

	public ArrayList<Plat> specialites() {
		return specialites;
	}
	public ArrayList<Plat> poulets() {
		return poulets;
	}	

	public ArrayList<Plat> boeufs() {
		return boeufs;
	}	
	public ArrayList<Plat> crevettes() {
		return crevettes;
	}	
	public ArrayList<Plat> poissons() {
		return poissons;
	}

	public ArrayList<Plat> entrees() {
		return entrees;
	}

	public ArrayList<Plat> porcs() {
		return porcs;
	}

	public ArrayList<Plat> canards() {
		return canards;
	}

	public ArrayList<Plat> vegetariens() {
		return vegetariens;
	}

	public ArrayList<Plat> desserts() {
		return desserts;
	}

	public ArrayList<Plat> boissons() {
		return boissons;
	}

//	public Double prixRemplacemetRiz() {
//		return prixRemplacemetRiz;
//	}

	public void getPanier(String transactionId) {
		platService.panier(transactionId, new MethodCallback<List<Commande>>() {
			
			@Override
			public void onSuccess(Method method, List<Commande> result) {
				if (result != null)// echec de paiment. Reload panier
				{	
					AppEventBus.EVENT_BUS.fireEvent(new SelectPlatEvent((ArrayList<Commande>)result));
				}
				GWT.log("panierForTransaction success" + result); 				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				GWT.log("panierForTransaction" + exception.toString());
				
			}
		});
//		RPCPlatService.Util.getInstance().panierForTransaction(transactionId, new AsyncCallback<ArrayList<Commande>>() {
//			
//			@Override
//			public void onSuccess(ArrayList<Commande> result) {
//				if (result != null)// echec
//				{	
//					AppEventBus.EVENT_BUS.fireEvent(new SelectPlatEvent(result));
//				}
//				GWT.log("panierForTransaction success" + result); 
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				GWT.log("panierForTransaction" + caught.toString());
//				
//			}
//		});		
	}	
	
	// test datatrans
//	private void initTransService() {
//	       Resource datatransRes = new Resource("https://api.sandbox.datatrans.com/v1/transactions");
////	       DatatransServiceAsync datatransService = GWT.create(DatatransServiceAsync.class);
////	       ((RestServiceProxy) datatransService).setResource(datatransRes);	
//	       Method method = datatransRes.post()/*.user("1100027912").password("Datatrans12_")*/;
//	       method.header("Content-Type", "application/json");
//	       String originalInput = "1100027912:r5xnFHfloBc6Qqxc";
////	       String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
//	       method.header("Authorization", "Basic " + "MTEwMDAyNzkxMjpyNXhuRkhmbG9CYzZRcXhj");
//	       
//	       //
////	   	"currency": "CHF",
////		"refno": "Test-1234",
////		"amount": 1000,
////		"paymentMethods": ["VIS","ECA","PAP","TWI"],
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
//				Datatrans.startPayment(tid.getTransactionId());
//			}
//			
//			@Override
//			public void onFailure(Method method, Throwable exception) {
//				GWT.log(exception.toString());
//
//			};
//		});
//	}
}
