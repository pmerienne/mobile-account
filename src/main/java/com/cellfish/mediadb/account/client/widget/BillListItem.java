package com.cellfish.mediadb.account.client.widget;

import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;

public class BillListItem extends ListItem<BillDTO> {

	public BillListItem(BillDTO bill) {
		super(bill);
	}

	@Override
	protected String getHeading(BillDTO bill) {
		return getShortName(bill.getPaymaster());
	}

	@Override
	protected String getShortDescription(BillDTO bill) {
		return bill.getShortDescription();
	}

	@Override
	protected String getThumbUrl(BillDTO bill) {
		return null;
	}

	@Override
	protected void onItemClicked() {
		// TODO Auto-generated method stub
	}

	private String getShortName(UserDTO user) {
		return user.getFirstName() + " " + user.getName();
	}
}
