package ch.goldenmango.commandresto.client.service;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import ch.goldenmango.commandresto.client.bean.Paiement;

public interface DatatransInterface extends RestService {
	
    @POST
    @Path("paiement")
    void commande(final Paiement paiement, final MethodCallback<Paiement> callback);
	
    @PUT
    @Path("paiement")
    void payer(final Paiement paiement, final MethodCallback<Void> callback);


}
