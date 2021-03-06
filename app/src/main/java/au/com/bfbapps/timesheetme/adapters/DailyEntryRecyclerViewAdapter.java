package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.utils.DateUtil;

public class DailyEntryRecyclerViewAdapter
		extends RecyclerView.Adapter<DailyEntryRecyclerViewAdapter.DailyEntryItemViewHolder> {

	private Context mContext;
	private List<Entry> mEntryItemList;
	private LayoutInflater mInflater;
	private ClickListener mClickListener;

	private DatabaseHelper mDb;

	public DailyEntryRecyclerViewAdapter(Context context, List<Entry> data){
		mContext = context;
		mEntryItemList = data;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public DailyEntryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.fragment_daily_entry_item, parent, false);

		return new DailyEntryItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final DailyEntryItemViewHolder holder, final int position) {
		mDb = new DatabaseHelper(mContext.getApplicationContext());

		Entry currentItem = mEntryItemList.get(position);

		holder.title.setText(currentItem.getJob().getJobName());
		holder.subTitle.setText(currentItem.getTask().getTaskName());
		holder.start.setText(DateUtil.convertLongToTimeString(currentItem.getStart()));
		holder.finish.setText(DateUtil.convertLongToTimeString(currentItem.getFinish()));
		holder.totalHours.setText(String.format("%.2g%n", currentItem.getTotalTimeWorkedInMinutes()));
	}

	@Override
	public int getItemCount() {
		return mEntryItemList.size();
	}

	public void delete(int position){
		mDb.deleteEntry(mEntryItemList.get(position).getEntryId());
		mEntryItemList.remove(position);
		notifyItemRemoved(position);
	}

	public void setClickListener(ClickListener clickListener){
		mClickListener = clickListener;
	}

	class DailyEntryItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

		TextView title;
		TextView subTitle;
		TextView start;
		TextView finish;
		TextView totalHours;

		public DailyEntryItemViewHolder(View itemView) {
			super(itemView);
			itemView.setOnLongClickListener(this);
			title = (TextView)itemView.findViewById(R.id.daily_entry_item_title);
			subTitle = (TextView)itemView.findViewById(R.id.daily_entry_item_sub_title);
			start = (TextView)itemView.findViewById(R.id.daily_entry_item_start_time);
			finish = (TextView)itemView.findViewById(R.id.daily_entry_item_finish_time);
			totalHours = (TextView)itemView.findViewById(R.id.daily_entry_item_total_hours);
		}

		@Override
		public boolean onLongClick(View view) {
			if (mClickListener != null){
				mClickListener.itemClicked(view, getAdapterPosition());
			}
			return false;
		}


	}

	public interface ClickListener {
		void itemClicked(View view, int position);
	}
}
