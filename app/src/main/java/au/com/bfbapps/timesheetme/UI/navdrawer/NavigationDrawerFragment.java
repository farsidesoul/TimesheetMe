package au.com.bfbapps.timesheetme.UI.navdrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.adapters.NavViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements NavViewAdapter.ClickListener {

	public static final String PREF_FILE_NAME = "pref";
	public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

	private RecyclerView mRecyclerView;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private NavViewAdapter mNavViewAdapter;

	private boolean mUserLearnedDrawer;
	private boolean mFromSavedInstanceState;
	private View containerView;


	public NavigationDrawerFragment() {
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
		if(savedInstanceState != null){
			mFromSavedInstanceState = true;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

		mRecyclerView = (RecyclerView)layout.findViewById(R.id.nav_drawer_list);
		mNavViewAdapter = new NavViewAdapter(getActivity(), getNavItems());
		mNavViewAdapter.setClickListener(this);
		mRecyclerView.setAdapter(mNavViewAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		// Inflate the layout for this fragment
		return layout;
	}

	public static List<NavDrawerItem> getNavItems(){
		List<NavDrawerItem> data = new ArrayList<>();
		int[] icons = {R.mipmap.ic_home_black_48dp,
				R.mipmap.ic_assessment_black_48dp,
				R.mipmap.ic_get_app_black_48dp,
				R.mipmap.ic_input_black_48dp};

		String[] titles = {"Home", "Review", "Download", "Logout"};

		for (int i = 0; i< titles.length && i < icons.length; i++){
			NavDrawerItem currentItem = new NavDrawerItem(icons[i], titles[i]);
			data.add(currentItem);
		}

		return data;
	}
	
	
	public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
		containerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
				drawerLayout,
				toolbar,
				R.string.drawer_open,
				R.string.drawer_closed){
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if(!mUserLearnedDrawer){
					mUserLearnedDrawer = true;
					saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
				}
				// Makes the activity draw the actionbar again
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);

				// Makes the activity draw the actionbar again
				getActivity().invalidateOptionsMenu();
			}
		};

		if(!mUserLearnedDrawer && !mFromSavedInstanceState){
			mDrawerLayout.openDrawer(containerView);
		}

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

	}

	public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(preferenceName, preferenceValue);
		editor.apply();
	}

	public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(preferenceName, defaultValue);
	}

	@Override
	public void itemClicked(View view, int position) {
		// depending on position clicked, start relevant activity

		Toast.makeText(getActivity(), "Clicked item at position: " + position, Toast.LENGTH_SHORT).show();
	}
}
