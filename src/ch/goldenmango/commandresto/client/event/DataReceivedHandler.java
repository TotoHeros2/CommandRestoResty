package ch.goldenmango.commandresto.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DataReceivedHandler extends EventHandler {
	void onDataReceived(DataReceivedEvent event);

}
