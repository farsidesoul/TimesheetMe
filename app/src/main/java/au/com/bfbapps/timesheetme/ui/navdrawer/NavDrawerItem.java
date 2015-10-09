package au.com.bfbapps.timesheetme.ui.navdrawer;

public class NavDrawerItem {
	private int mItemId;
	private String mTitle;

	public NavDrawerItem(int itemId, String title){
		mItemId = itemId;
		mTitle = title;
	}

	public int getItemId() {
		return mItemId;
	}

	public String getTitle() {
		return mTitle;
	}
}
