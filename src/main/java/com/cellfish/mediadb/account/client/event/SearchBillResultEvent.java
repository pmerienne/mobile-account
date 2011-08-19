package com.cellfish.mediadb.account.client.event;

import com.cellfish.mediadb.account.client.model.SearchResult;
import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.google.gwt.event.shared.GwtEvent;

public class SearchBillResultEvent extends GwtEvent<SearchBillResultHandler> {

	private static Type<SearchBillResultHandler> TYPE;

	public static Type<SearchBillResultHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<SearchBillResultHandler>());
	}

	private SearchResult<BillDTO> searchResult;

	public SearchBillResultEvent(SearchResult<BillDTO> searchResult) {
		this.searchResult = searchResult;
	}

	@Override
	protected void dispatch(SearchBillResultHandler handler) {
		handler.onSearchBillResult(this);
	}

	@Override
	public GwtEvent.Type<SearchBillResultHandler> getAssociatedType() {
		return getType();
	}

	public SearchResult<BillDTO> getSearch() {
		return searchResult;
	}

}