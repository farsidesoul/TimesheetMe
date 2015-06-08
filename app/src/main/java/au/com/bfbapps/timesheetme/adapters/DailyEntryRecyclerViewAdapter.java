package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

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

	private boolean isOpen = false;

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
	public void onBindViewHolder(final DailyEntryItemViewHolder holder, final int position) {
		mDb = new DatabaseHelper(mContext.getApplicationContext());

		Entry currentItem = mEntryItemList.get(position);
		Job currentItemJob = mDb.getJobById(currentItem.getJobId());
		Task currentItemTask = mDb.getTaskById(currentItem.getTaskId());

		holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
		holder.swipeLayout.close();
		holder.swipeLayout.setSwipeEnabled(false);
		Log.d("Swipe", "Disabled");
		holder.swipeLayout.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				if (isOpen) {
					holder.swipeLayout.close();
					isOpen = false;
				} else {
					holder.swipeLayout.open();
					isOpen = true;
				}

				return true;
			}
		});

		holder.swipeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isOpen) {
					holder.swipeLayout.close();
					isOpen = false;
				}
			}
		});


		holder.deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				delete(position);
			}
		});

		holder.title.setText(currentItemJob.getJobName());
		holder.subTitle.setText(currentItemTask.getTaskName());
		holder.start.setText(currentItem.getStart());
		holder.finish.setText(currentItem.getFinish());
		holder.totalHours.setText(String.format("%.2g%n", currentItem.getTotalHoursWorked()));
		Log.d("Date", currentItem.getDate().toString());
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

		SwipeLayout swipeLayout;
		TextView title;
		TextView subTitle;
		TextView start;
		TextView finish;
		TextView totalHours;
		LinearLayout bottomWrapper;
		TextView deleteButton;

		public DailyEntryItemViewHolder(View itemView) {
			super(itemView);
			itemView.setOnLongClickListener(this);
			title = (TextView)itemView.findViewById(R.id.daily_entry_item_title);
			subTitle = (TextView)itemView.findViewById(R.id.daily_entry_item_sub_title);
			start = (TextView)itemView.findViewById(R.id.daily_entry_item_start_time);
			finish = (TextView)itemView.findViewById(R.id.daily_entry_item_finish_time);
			totalHours = (TextView)itemView.findViewById(R.id.daily_entry_item_total_hours);
			swipeLayout = (SwipeLayout)itemView.findViewById(R.id.swipeLayout);
			bottomWrapper = (LinearLayout)itemView.findViewById(R.id.bottom_wrapper);
			deleteButton = (TextView)itemView.findViewById(R.id.daily_entry_delete_button);
		}

		@Override
		public boolean onLongClick(View view) {
			if (mClickListener != null){
				mClickListener.itemClicked(view, getPosition());
			}
			return false;
		}


	}

	public interface ClickListener {
		void itemClicked(View view, int position);
	}
}
