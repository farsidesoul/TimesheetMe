package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.uis.navdrawer.NavDrawerItem;

public class NavViewAdapter extends RecyclerView.Adapter<NavViewAdapter.NavItemViewHolder> {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<NavDrawerItem> mData = Collections.emptyList();
	private ClickListener mClickListener;

	public NavViewAdapter(Context context, List<NavDrawerItem> data){
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mData = data;
	}

	@Override
	public NavItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.nav_row_item, parent, false);
		NavItemViewHolder holder = new NavItemViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(NavItemViewHolder holder, final int position) {
		NavDrawerItem currentItem = mData.get(position);
		holder.title.setText(currentItem.getTitle());
		holder.icon.setImageResource(currentItem.getItemId());
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public void delete(int position){
		mData.remove(position);
		notifyItemRemoved(position);
	}

	public void setClickListener(ClickListener clickListener){
		mClickListener = clickListener;
	}

	class NavItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView title;
		ImageView icon;
		public NavItemViewHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			title = (TextView)itemView.findViewById(R.id.nav_drawer_list_item_text);
			icon = (ImageView)itemView.findViewById(R.id.nav_drawer_list_item_icon);
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
