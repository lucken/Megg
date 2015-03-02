package com.meggnify.mission;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.Constants;
import com.meggnify.lib.LocalContext;
import com.meggnify.model.Mission;
import com.meggnify.model.Task;

import java.util.ArrayList;
import java.util.List;

public class GeneralSurveyActivity extends ActionBarActivity implements ISurveyListener {

    private Context mContext;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private Mission mission;

    private int totalTask;
    private int currentTask;

    private List<Task> tasks;
    private TaskPagerAdapter taskPagerAdapter;
    private ViewPager mViewPager;

    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_survey);

        mContext = LocalContext.getContext();

        meggManager = MeggnifyManager.sharedInstance();

        mUserToken = getToken(mContext);

        Bundle extras = getIntent().getExtras();
        mission = extras.getParcelable("mission");

        setTitle(mission.name);

        currentTask = 0;
        totalTask = 0;

        tasks = new ArrayList<Task>();
        taskPagerAdapter = new TaskPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.task_pager);
        mViewPager.setHorizontalScrollBarEnabled(false);
        mViewPager.setAdapter(taskPagerAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentTask = position;
                if (currentTask == totalTask) {
                    nextButton.setText("Done");
                } else {
                    nextButton.setText("Next");
                }

                if (currentTask == 0) {
                    prevButton.setVisibility(View.INVISIBLE);
                } else {
                    prevButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        prevButton = (Button) findViewById(R.id.prev_button);
        prevButton.setVisibility(View.INVISIBLE);
        prevButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                int task = currentTask - 1;
                mViewPager.setCurrentItem(task, true);
            }

        });

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (currentTask == totalTask) {
                    postAnswers();

                    finish();
                } else {
                    saveAnswer();

                    int task = currentTask + 1;
                    mViewPager.setCurrentItem(task, true);
                }
            }

        });

        loadMission(mission.missionId, false);
    }

    @Override
    public void onBackPressed() {

    }

    private String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token  = prefs.getString(Constants.PREF_USER_TOKEN, "");
        if (token.isEmpty()) {
            return null;
        }
        return token;
    }

    void loadMission(int missionId, final boolean cache) {
        new AsyncTask<Integer, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Integer... params) {
                // TODO Auto-generated method stub
                int missionId = params[0];

                return meggManager.refreshMission(missionId);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                if (status) {
                    List<Task> tmpTasks = meggManager.tasks;
                    tasks.clear();
                    for (int i = 0; i < tmpTasks.size(); i++) {
                        tasks.add(tmpTasks.get(i));
                    }

                    taskPagerAdapter.notifyDataSetChanged();

                    totalTask = tasks.size() - 1;
                    currentTask = 0;
                    mViewPager.setCurrentItem(currentTask);

                    prevButton.setVisibility(View.INVISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    nextButton.setText("Next");
                } else {
                    if (!cache) {
                        //Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }.execute(missionId);
    }

    void saveAnswer() {

    }

    void postAnswers() {

    }

    private class TaskPagerAdapter extends FragmentPagerAdapter {

        public TaskPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            Fragment fragment = new TaskFragment();

            Task task = (Task) tasks.get(position);
            if (task != null) {
                Bundle extras = new Bundle();
                extras.putString("token", mUserToken);
                extras.putParcelable("task", task);
                fragment.setArguments(extras);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return tasks.size();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
