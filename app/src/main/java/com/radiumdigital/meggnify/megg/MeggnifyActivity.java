package com.radiumdigital.meggnify.megg;

import com.radiumdigital.meggnify.Constants;
import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.lib.LocalContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MeggnifyActivity extends ActionBarActivity implements DrawerFragment.DrawerCallback {

    private Context context;

    private String mUserToken;

	private DrawerFragment drawerFragment;
	
	private CharSequence mTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meggnify);

        context = LocalContext.getContext();

        mUserToken = getToken(context);

		mTitle = getTitle();

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Home");

		drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
		drawerFragment.setView(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!drawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = null;
		switch(position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new AssignmentFragment();
			break;
		case 2:
			fragment = new CashFragment();
			break;
		case 3:
			fragment = new ProfileFragment();
			break;
		case 4:
			fragment = new SettingFragment();
			break;
		default:
			fragment = new HomeFragment();
			break;
		}

        Bundle extras = new Bundle();
        extras.putString("token", mUserToken);
        fragment.setArguments(extras);

		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
	}

    private String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token  = prefs.getString(Constants.PREF_USER_TOKEN, "");
        if (token.isEmpty()) {
            return null;
        }
        return token;
    }

	public void onFragmentAttached(int number) {
		switch (number) {
		case 0:
			mTitle = "Home";
			break;
		case 1:
			mTitle = "Assignment";
			break;
		case 2:
			mTitle = "Cash Account";
			break;
		case 3:
			mTitle = "Profile";
			break;
		case 4:
			mTitle = "Settings";
			break;
		default:
			mTitle = "Home";
			break;
		}
	}

	void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

}
