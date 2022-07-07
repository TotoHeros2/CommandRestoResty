package ch.goldenmango.commandresto.client.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import ch.goldenmango.commandresto.client.bean.Commande;
import ch.goldenmango.commandresto.client.bean.TypePlat;

public interface PlatService extends RestService {
	
	
    @GET
    @Path("plats")
    void getAllPlats(MethodCallback<List<TypePlat>> callback);
 
    @GET
    @Path("times")
    void commandTimes(MethodCallback<List<String>> callback);
    
    @POST
    @Path("panier")
    void panier(final String transactionId, MethodCallback<List<Commande>> callback);
    
// copied from https://github.com/feedm3/spring-boot-gwt/blob/master/src/main/java/com/codecrafters/client/TodoItemService.java    
//    @Path("?text={text}")
//    void getTodos(@PathParam("text") final String text, MethodCallback<List<TypePlat>> callback);

}
