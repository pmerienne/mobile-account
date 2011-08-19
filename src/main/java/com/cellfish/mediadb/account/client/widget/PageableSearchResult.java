package com.cellfish.mediadb.account.client.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cellfish.mediadb.account.client.event.EventBusHelper;
import com.cellfish.mediadb.account.client.model.SearchResult;
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

public abstract class PageableSearchResult<T> extends Composite {

	private static PageableSearchResultUiBinder uiBinder = GWT.create(PageableSearchResultUiBinder.class);

	@SuppressWarnings("rawtypes")
	interface PageableSearchResultUiBinder extends UiBinder<Widget, PageableSearchResult> {
	}

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

	public PageableSearchResult() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("previousResultsButton")
	protected void onPreviousClicked(ClickEvent event) {
		if (this.isActionAllowed(event)) {
			this.onPrevious();
		}
	}

	@UiHandler("nextResultsButton")
	protected void onNextClicked(ClickEvent event) {
		if (this.isActionAllowed(event)) {
			this.onNext();
		}
	}

	protected void setResults(SearchResult<T> searchResult) {
		clearResultsList();
		List<T> results = searchResult.getResults();
		for (T result : results) {
			ListItem<T> listItem = getListItem(result);
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

	protected abstract ListItem<T> getListItem(T bean);

	protected abstract void onPrevious();

	protected abstract void onNext();

}
