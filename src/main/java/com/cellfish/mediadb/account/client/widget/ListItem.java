package com.cellfish.mediadb.account.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.jquery.mobile.ui.Description;
import com.google.gwt.jquery.mobile.ui.Heading;
import com.google.gwt.jquery.mobile.ui.ListButton;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class ListItem<T> extends ListButton {

	private Heading heading;

	private Description description;

	public ListItem(String text) {
		super(text, "arrow-r", null);
	}

	public ListItem() {
		this("");
	}

	public ListItem(T bean) {
		this("");
		FlowPanel mainContent = new FlowPanel();
		heading = new Heading(this.getHeading(bean));
		description = new Description(this.getShortDescription(bean));
		mainContent.add(heading);
		mainContent.add(description);
		this.add(mainContent);

		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onItemClicked();
			}
		});
	}

	protected abstract String getHeading(T bean);

	protected abstract String getShortDescription(T bean);

	protected abstract String getThumbUrl(T bean);

	protected abstract void onItemClicked();
}
