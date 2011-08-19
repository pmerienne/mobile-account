package com.cellfish.mediadb.account.client.event;

import com.cellfish.mediadb.account.client.model.Search;
import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.google.gwt.event.shared.GwtEvent;

public class SearchBillEvent extends GwtEvent<SearchBillHandler> {

	private static Type<SearchBillHandler> TYPE;

	public static Type<SearchBillHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<SearchBillHandler>());
	}

	private Search<BillDTO> search;

	private UserDTO user;

	public SearchBillEvent(Search<BillDTO> search, UserDTO user) {
		this.search = search;
		this.user = user;
	}

	@Override
	protected void dispatch(SearchBillHandler handler) {
		handler.onSearchBill(this);
	}

	@Override
	public GwtEvent.Type<SearchBillHandler> getAssociatedType() {
		return getType();
	}

	public UserDTO getUser() {
		return user;
	}

	public Search<BillDTO> getSearch() {
		return search;
	}

}