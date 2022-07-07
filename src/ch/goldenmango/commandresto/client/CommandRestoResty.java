package ch.goldenmango.commandresto.client;

import java.util.List;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import ch.goldenmango.commandresto.client.bean.TypePlat;
import ch.goldenmango.commandresto.client.event.AppEventBus;
import ch.goldenmango.commandresto.client.event.DataReceivedEvent;
import ch.goldenmango.commandresto.client.event.DataReceivedHandler;
import ch.goldenmango.commandresto.client.service.PlatService;
import ch.goldenmango.commandresto.client.service.RestoService;
import ch.goldenmango.commandresto.client.view.Footer;
import ch.goldenmango.commandresto.client.view.MainContent;
import ch.goldenmango.commandresto.client.view.Panier;
import gwt.material.design.client.ui.MaterialToast;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CommandRestoResty implements EntryPoint {
	
	
    private static final PlatService platService = GWT.create(PlatService.class);
	private String transactionId;



	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DOM.getElementById("wait").getStyle().setDisplay(Display.NONE);

		useCorrectRequestBaseUrl();
		
// etst only		getAllPlats();

		RestoService.get().specialites();// will load everything + get specialites for first page + event when loaded
		
		AppEventBus.EVENT_BUS.addHandler(DataReceivedEvent.TYPE,new DataReceivedHandler() {
			
			@Override
			public void onDataReceived(DataReceivedEvent event) {
//				RootPanel.get().add(new MainSplitPanel());
//				RootPanel.get().add(new SideNavContentView());
//				RootPanel.get().add(new TestTab());
//				RootPanel.get().clear();
				transactionId = getTransactionId();

				if (transactionId != null)// // after payment error from server page
				{
					RestoService.get().getPanier(transactionId);
				}
				RootPanel.get().add(new MainContent()); // start app!

			}
		});

	}
	
    private void getAllPlats() {
		platService.getAllPlats(new MethodCallback<List<TypePlat>>() {
			
			@Override
			public void onSuccess(Method method, List<TypePlat> response) {
//				MaterialToast.fireToast("Succes can get all plats !","rounded");	
				onDataReceived();
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Impossible de contacter le serveur. Merci d'essayer plus tard");

//				MaterialToast.fireToast("Impossible de contacter le serveur. Merci d'essayer plus tard","rounded");	
				
			}
		});
	}

	protected void onDataReceived() {
		// TODO Auto-generated method stub
		transactionId = getTransactionId();
		if (transactionId == null) // client
		{
			
		}
		else // after payment error from server page
		{
			
		}

		
	}

	private void useCorrectRequestBaseUrl() {
        if (isDevelopmentMode()) {
            // our spring boot server is running at port 80. If we don't change the url
            // resty gwt would use the gwt servlet port
            Defaults.setServiceRoot("http://localhost:8080");
        } else {
            // in production our compiled javascript code gets copied into
            // a 'springbootgwt' folder into the static resources. So if we
            // dont change the default url resty gwt would put the folders name
            // to the base url
            Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        }
    }
	
	protected String getTransactionId()
	{
		Dictionary id = Dictionary.getDictionary("ids");
		if (id.values().isEmpty())
			return null;
		return id.get("transactionId");
	}
    
	/**
     * Detect if the app is in development mode.
     *
     * @return true if in development mode
     */
    private static native boolean isDevelopmentMode()/*-{
        return typeof $wnd.__gwt_sdm !== 'undefined';
    }-*/;
    
    
    
}
