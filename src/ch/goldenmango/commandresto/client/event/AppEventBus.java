package ch.goldenmango.commandresto.client.event;

import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppEventBus {

    public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

}
