package com.cellfish.mediadb.account.client;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.event.LoginEvent;
import com.cellfish.mediadb.account.client.event.LoginHandler;
import com.cellfish.mediadb.account.client.ui.Hello;
import com.cellfish.mediadb.account.client.ui.LoginPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.jquery.mobile.ui.util.JQueryMobile;
import com.google.gwt.user.client.ui.RootPanel;

public class Account implements EntryPoint {

	private final static EventBus EVENT_BUS = EventBusHelper.getEventBus();

	private LoginPage loginPage = new LoginPage();

	private Hello hello = new Hello();

	@Override
	public void onModuleLoad() {
		JQueryMobile.init();
		RootPanel.get().add(loginPage);
		bind();
	}

	private void bind() {
		EVENT_BUS.addHandler(LoginEvent.getType(), new LoginHandler() {
			@Override
			public void onSearchProductEvent(LoginEvent event) {
				loginPage.removeFromParent();
				RootPanel.get().add(hello);
			}
		});
	}
}
