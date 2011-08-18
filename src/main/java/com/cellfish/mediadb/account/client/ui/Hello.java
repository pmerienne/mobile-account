package com.cellfish.mediadb.account.client.ui;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.event.LoginEvent;
import com.cellfish.mediadb.account.client.event.LoginHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Hello extends Composite {

	private static HelloUiBinder uiBinder = GWT.create(HelloUiBinder.class);

	interface HelloUiBinder extends UiBinder<Widget, Hello> {
	}

	private final static EventBus EVENT_BUS = EventBusHelper.getEventBus();

	@UiField
	Label helloLabel;

	public Hello() {
		initWidget(uiBinder.createAndBindUi(this));
		bind();
	}

	private void bind() {
		EVENT_BUS.addHandler(LoginEvent.getType(), new LoginHandler() {
			@Override
			public void onSearchProductEvent(LoginEvent event) {
				helloLabel.setText("Hello " + event.getUser().getName());
			}
		});
	}

}
