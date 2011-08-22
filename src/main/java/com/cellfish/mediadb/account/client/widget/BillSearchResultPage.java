package com.cellfish.mediadb.account.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.event.SearchBillEvent;
import com.cellfish.mediadb.account.client.event.SearchBillHandler;
import com.cellfish.mediadb.account.client.event.SearchBillResultEvent;
import com.cellfish.mediadb.account.client.event.SearchBillResultHandler;
import com.cellfish.mediadb.account.client.model.search.BillSearch;
import com.cellfish.mediadb.account.client.model.search.SearchResult;
import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.jquery.mobile.ui.Button;
import com.google.gwt.jquery.mobile.ui.ListView;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class BillSearchResultPage extends Composite {

	private static BillSearchResultPageUiBinder uiBinder = GWT.create(BillSearchResultPageUiBinder.class);

	interface BillSearchResultPageUiBinder extends UiBinder<Widget, BillSearchResultPage> {
	}

	protected final static EventBus EVENT_BUS = EventBusHelper.getEventBus();

	@UiField
	Label searchMessage;

	@UiField
	ListView resultsList;

	@UiField
	FlowPanel pagination;

	@UiField
	Button previousResultsButton;

	@UiField
	Label currentPage;

	@UiField
	Button nextResultsButton;

	protected BillSearch currentSearch;

	public BillSearchResultPage() {
		initWidget(uiBinder.createAndBindUi(this));
		bind();
	}

	@UiHandler("previousResultsButton")
	protected void onPreviousClicked(ClickEvent event) {
		if (this.isActionAllowed(event)) {
			if (this.currentSearch != null) {
				this.currentSearch.setPage(this.currentSearch.getPage() - 1);
				EVENT_BUS.fireEvent(new SearchBillEvent(this.currentSearch));
			}
		}
	}

	@UiHandler("nextResultsButton")
	protected void onNextClicked(ClickEvent event) {
		if (this.isActionAllowed(event)) {
			if (this.currentSearch != null) {
				this.currentSearch.setPage(this.currentSearch.getPage() + 1);
				EVENT_BUS.fireEvent(new SearchBillEvent(this.currentSearch));
			}
		}
	}

	private void setResults(SearchResult<BillDTO> searchResult) {
		clearResultsList();
		List<BillDTO> results = searchResult.getResults();
		for (BillDTO result : results) {
			ListItem<BillDTO> listItem = getListItem(result);
			resultsList.add(listItem);
		}
		searchMessage.setText("Recherche de " + searchResult.getSearch().getName() + " : " + searchResult.getTotalRecords()
				+ " résultat(s).");
		setPendingResult(false);
		previousResultsButton.setActive(searchResult.getCurrentPage() > 1);
		nextResultsButton.setActive(searchResult.getCurrentPage() * 10 < searchResult.getTotalRecords());
		int pageCount = Math.abs(searchResult.getTotalRecords() / 10) + 1;
		currentPage.setText("Page " + searchResult.getCurrentPage() + " sur " + pageCount);
	}

	private boolean isActionAllowed(ClickEvent event) {
		return event.getSource() instanceof Button && ((Button) event.getSource()).isActive();
	}

	protected void setPendingResult(boolean pending) {
		this.resultsList.setVisible(!pending);
		this.pagination.setVisible(!pending);
		if (pending) {
			this.searchMessage.setText("Recherche en cours");
		}
	}

	private void clearResultsList() {
		List<Widget> children = new ArrayList<Widget>();
		Iterator<Widget> iterator = this.resultsList.iterator();
		while (iterator.hasNext()) {
			children.add(iterator.next());
		}
		for (Widget child : children) {
			this.resultsList.remove(child);
		}
	}

	private ListItem<BillDTO> getListItem(BillDTO bean) {
		return new BillListItem(bean);
	}

	protected void bind() {
		EVENT_BUS.addHandler(SearchBillEvent.getType(), new SearchBillHandler() {
			@Override
			public void onSearchBill(SearchBillEvent event) {
				currentSearch = event.getSearch();
			}
		});

		EVENT_BUS.addHandler(SearchBillResultEvent.getType(), new SearchBillResultHandler() {
			@Override
			public void onSearchResult(SearchBillResultEvent event) {
				setResults(event.getSearch());
			}
		});
	}
}
