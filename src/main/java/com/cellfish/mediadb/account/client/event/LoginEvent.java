package com.cellfish.mediadb.account.client.event;

import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginHandler> {

	private static Type<LoginHandler> TYPE;

	private UserDTO user;

	public static Type<LoginHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<LoginHandler>());
	}

	public LoginEvent(UserDTO user) {
		this.user = user;
	}

	@Override
	protected void dispatch(LoginHandler handler) {
		handler.onSearchProductEvent(this);
	}

	@Override
	public GwtEvent.Type<LoginHandler> getAssociatedType() {
		return getType();
	}

	public UserDTO getUser() {
		return user;
	}

}