package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.models.DailyEntry;

public class DailyEntryRecyclerViewAdapter extends RecyclerView.Adapter<DailyEntryRecyclerViewAdapter.DailyEntryItemViewHolder> {

	private Context mContext;
	private List<DailyEntry> mDailyEntryItemList;
	private LayoutInflater mInflater;
	private ClickListener mClickListener;

	public DailyEntryRecyclerViewAdapter(Context context, List<DailyEntry> data){
		mContext = context;
		mDailyEntryItemList = data;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public DailyEntryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//TODO: Create custom view for daily entry
		View view = mInflater.inflate(R.layout.nav_row_item, parent, false);
		DailyEntryItemViewHolder holder = new DailyEntryItemViewHolder(view);
		return null;
	}

	@Override
	public void onBindViewHolder(DailyEntryItemViewHolder holder, int position) {
		//TODO: Add elements to Daily Entry
		DailyEntry currentItem = mDailyEntryItemList.get(position);
		holder.title.setText("");
	}

	@Override
	public int getItemCount() {
		return mDailyEntryItemList.size();
	}

	public void delete(int position){
		//TODO: Create call to DB to remove item from DB
		mDailyEntryItemList.remove(position);
		notifyItemRemoved(position);
	}

	public void setClickListener(ClickListener clickListener){
		mClickListener = clickListener;
	}

	class DailyEntryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView title;
		public DailyEntryItemViewHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			title = (TextView)itemView.findViewById(R.id.nav_drawer_list_item_text);
		}

		@Override
		public void onClick(View view) {
			if (mClickListener != null){
				mClickListener.itemClicked(view, getPosition());
			}
		}
	}

	public interface ClickListener {
		void itemClicked(View view, int position);
	}
}
