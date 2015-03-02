package com.meggnify.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.meggnify.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrawerFragment extends Fragment {

    private static final String STATE_SELECTED_POSITION = "selected_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "drawer_learned";

    private DrawerCallback mCallback;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;

    private List<HashMap<String, String>> menus;
    private MenuAdapter menuAdapter;
    private ListView menuList;

    private int mCurrentSelectedPosition;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public static interface DrawerCallback {
        void onDrawerItemSelected(int position);
    }

    public DrawerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        //menus = new String[] {"Home", "Assignment", "Cash Account", "Profile", "Settings"};
        menus = new ArrayList<HashMap<String, String>>();
        menuAdapter = new MenuAdapter(getActivity(), menus);

        menuList = (ListView) view.findViewById(R.id.list_menu);
        //menuList.setAdapter(new ArrayAdapter<String>(getActivity(),
                //android.R.layout.simple_list_item_1, android.R.id.text1, menus));
        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }

        });
        menuList.setItemChecked(mCurrentSelectedPosition, true);

        loadMenu();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (DrawerCallback) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException("Activity must implement DrawerCallback.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openDrawer() {
        mDrawerLayout.openDrawer(containerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(containerView);
    }
    void loadMenu() {
        HashMap<String, String> home = new HashMap<>();
        home.put("icon", String.valueOf(R.drawable.ic_menu_home));
        home.put("label", "Home");
        menus.add(home);

        HashMap<String, String> assignments = new HashMap<>();
        assignments.put("icon", String.valueOf(R.drawable.ic_menu_assignment));
        assignments.put("label", "Assignments");
        menus.add(assignments);

        HashMap<String, String> cash = new HashMap<>();
        cash.put("icon", String.valueOf(R.drawable.ic_menu_cash));
        cash.put("label", "Cash Account");
        menus.add(cash);

        HashMap<String, String> profile = new HashMap<>();
        profile.put("icon", String.valueOf(R.drawable.ic_menu_profile));
        profile.put("label", "Profile");
        menus.add(profile);

        HashMap<String, String> settings = new HashMap<>();
        settings.put("icon", String.valueOf(R.drawable.ic_menu_setting));
        settings.put("label", "Settings");
        menus.add(settings);

        menuAdapter.notifyDataSetChanged();
    }

    void setView(int fragmentId, DrawerLayout drawerLayout) {
        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.drawable.home,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }

                getActivity().supportInvalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (menuList != null) {
            menuList.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(containerView);
        }
        if (mCallback != null) {
            mCallback.onDrawerItemSelected(position);
        }
    }

    boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(containerView);
    }

    ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    static class ViewHolder {
        ImageView iconView;
        TextView labelView;
    }

    private class MenuAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private List<HashMap<String, String>> mMenus;

        public MenuAdapter(Context context, List<HashMap<String, String>> menus) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(mContext);
            mMenus = menus;
        }

        @Override
        public int getCount() {
            return mMenus.size();
        }

        @Override
        public Object getItem(int position) {
            return mMenus.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_menu, parent, false);

                holder = new ViewHolder();
                holder.iconView = (ImageView) convertView.findViewById(R.id.menu_icon);
                holder.labelView = (TextView) convertView.findViewById(R.id.menu_label);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> menu = (HashMap<String, String>) getItem(position);
            holder.iconView.setImageResource(Integer.valueOf(menu.get("icon")));
            holder.labelView.setText(menu.get("label"));

            return convertView;
        }
    }

}
