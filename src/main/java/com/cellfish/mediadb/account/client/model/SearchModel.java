package com.cellfish.mediadb.account.client.model;

import java.util.Arrays;
import java.util.List;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.event.SearchBillEvent;
import com.cellfish.mediadb.account.client.event.SearchBillHandler;
import com.cellfish.mediadb.account.client.event.SearchBillResultEvent;
import com.cellfish.mediadb.account.client.model.search.BillSearch;
import com.cellfish.mediadb.account.client.model.search.SearchResult;
import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
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
				SearchResult<BillDTO> searchResult = mockBillSearch(event.getSearch());
				EVENT_BUS.fireEvent(new SearchBillResultEvent(searchResult));
			}
		});
	}

	private SearchResult<BillDTO> mockBillSearch(BillSearch search) {
		SearchResult<BillDTO> searchResult = new SearchResult<BillDTO>();
		searchResult.setSearch(search);

		UserDTO smith = new UserDTO("smith", "john");
		UserDTO dupont = new UserDTO("dupont", "jean");
		UserDTO martin = new UserDTO("martin", "paul");

		BillDTO pub = new BillDTO(smith, 200);
		List<UserDTO> pubParticipants = Arrays.asList(martin, smith, dupont);
		pub.setParticipants(pubParticipants);

		BillDTO shoping = new BillDTO(smith, 800);
		List<UserDTO> shopingParticipants = Arrays.asList(dupont, smith);
		shoping.setParticipants(shopingParticipants);

		BillDTO rent = new BillDTO(martin, 2010);
		List<UserDTO> rentParticipants = Arrays.asList(smith, dupont, martin);
		rent.setParticipants(rentParticipants);

		searchResult.setResults(Arrays.asList(pub, shoping, rent));
		searchResult.setCurrentPage(search.getPage());
		searchResult.setTotalRecords(5);
		return searchResult;
	}
}
