package ch.goldenmango.commandresto.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Notification extends Composite  {

	private static NotificationUiBinder uiBinder = GWT.create(NotificationUiBinder.class);

	interface NotificationUiBinder extends UiBinder<Widget, Notification> {
	}

	public Notification() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
