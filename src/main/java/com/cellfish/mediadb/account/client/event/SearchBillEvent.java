package com.cellfish.mediadb.account.client.event;

import com.cellfish.mediadb.account.client.model.search.BillSearch;
import com.google.gwt.event.shared.GwtEvent;

public class SearchBillEvent extends GwtEvent<SearchBillHandler> {

	private static Type<SearchBillHandler> TYPE;

	public static Type<SearchBillHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<SearchBillHandler>());
	}

	private BillSearch search;

	public SearchBillEvent(BillSearch search) {
		this.search = search;
	}

	@Override
	protected void dispatch(SearchBillHandler handler) {
		handler.onSearchBill(this);
	}

	@Override
	public GwtEvent.Type<SearchBillHandler> getAssociatedType() {
		return getType();
	}

	public BillSearch getSearch() {
		return search;
	}

}