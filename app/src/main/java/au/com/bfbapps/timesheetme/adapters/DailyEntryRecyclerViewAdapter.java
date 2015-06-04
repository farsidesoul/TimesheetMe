package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;

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
		DailyEntryItemViewHolder holder = new DailyEntryItemViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(DailyEntryItemViewHolder holder, int position) {
		mDb = new DatabaseHelper(mContext.getApplicationContext());

		Entry currentItem = mEntryItemList.get(position);
		Job currentItemJob = mDb.getJobById(currentItem.getJobId());
		Task currentItemTask = mDb.getTaskById(currentItem.getTaskId());

		holder.title.setText(currentItemJob.getJobName());
		holder.subTitle.setText(currentItemTask.getTaskName());
		holder.start.setText(currentItem.getStart());
		holder.finish.setText(currentItem.getFinish());
		holder.totalHours.setText(currentItem.getTotalHoursWorked() + "");
	}

	@Override
	public int getItemCount() {
		return mEntryItemList.size();
	}

	public void delete(int position){
		//TODO: Create call to DB to remove item from DB
		mEntryItemList.remove(position);
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
