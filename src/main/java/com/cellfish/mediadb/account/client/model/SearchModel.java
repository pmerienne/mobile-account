package com.cellfish.mediadb.account.client.model;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.event.SearchBillEvent;
import com.cellfish.mediadb.account.client.event.SearchBillHandler;
import com.google.gwt.event.shared.EventBus;

public class SearchModel {

	private final static EventBus EVENT_BUS = EventBusHelper.getEventBus();

	public SearchModel() {
		bind();
	}

	private void bind() {
		EVENT_BUS.addHandler(SearchBillEvent.getType(), new SearchBillHandler() {
			@Override
			public void onSearchBill(SearchBillEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
}
