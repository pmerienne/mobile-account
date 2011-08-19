package com.cellfish.mediadb.account.client.ui;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.event.LoginEvent;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.cellfish.mediadb.account.shared.service.UserService;
import com.cellfish.mediadb.account.shared.service.UserServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.jquery.mobile.ui.PasswordTextBox;
import com.google.gwt.jquery.mobile.ui.TextBox;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LoginPage extends Composite {

	private static LoginPageUiBinder uiBinder = GWT.create(LoginPageUiBinder.class);

	interface LoginPageUiBinder extends UiBinder<Widget, LoginPage> {
	}

	private UserServiceAsync userService = GWT.create(UserService.class);

	private final static EventBus EVENT_BUS = EventBusHelper.getEventBus();

	@UiField
	TextBox emailBox;

	@UiField
	PasswordTextBox passwordBox;

	@UiField
	Label errorLabel;

	public LoginPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("loginButton")
	protected void onLoginClicked(ClickEvent event) {
		this.login(emailBox.getText(), passwordBox.getText());
	}

	private void login(String email, String password) {
		errorLabel.setVisible(false);
		this.userService.authenticate(email, password, new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO user) {
				if (user != null) {
					EVENT_BUS.fireEvent(new LoginEvent(user));
				} else {
					errorLabel.setText("Identifiant ou mot de passe invalide(s). Veuillez réessayer.");
					errorLabel.setVisible(true);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				errorLabel.setText("Une erreur est survenue : " + caught.getMessage());
				errorLabel.setVisible(true);
			}
		});
	}
}
