package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.models.DailyEntry;

public class DailyEntryRecyclerViewAdapter
		extends RecyclerView.Adapter<DailyEntryRecyclerViewAdapter.DailyEntryItemViewHolder> {

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
		View view = mInflater.inflate(R.layout.fragment_daily_entry_item, parent, false);
		DailyEntryItemViewHolder holder = new DailyEntryItemViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(DailyEntryItemViewHolder holder, int position) {
		DailyEntry currentItem = mDailyEntryItemList.get(position);
		holder.title.setText(currentItem.getTitle());
		holder.subTitle.setText(currentItem.getSubTitle());
		holder.start.setText(currentItem.getStart());
		holder.finish.setText(currentItem.getFinish());
		holder.totalHours.setText(currentItem.getTotalHours());
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
		TextView subTitle;
		TextView start;
		TextView finish;
		TextView totalHours;

		public DailyEntryItemViewHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			title = (TextView)itemView.findViewById(R.id.daily_entry_item_title);
			subTitle = (TextView)itemView.findViewById(R.id.daily_entry_item_sub_title);
			start = (TextView)itemView.findViewById(R.id.daily_entry_item_start_time);
			finish = (TextView)itemView.findViewById(R.id.daily_entry_item_finish_time);
			totalHours = (TextView)itemView.findViewById(R.id.daily_entry_item_total_hours);
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
