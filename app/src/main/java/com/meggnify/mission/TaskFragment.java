package com.meggnify.mission;

import com.meggnify.R;
import com.meggnify.model.Task;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskFragment extends Fragment {

    private ISurveyListener mListener;

    private Task task;

    private String mUserToken;

    private TextView questionView;
    private LinearLayout answerContainer;

    public TaskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");
        task = extras.getParcelable("task");

        questionView = (TextView) view.findViewById(R.id.task_question);
        questionView.setText(task.question);

        answerContainer = (LinearLayout) view.findViewById(R.id.answer_container);

        for (int i = 0; i < task.answers.size(); i++) {
            TextView answerView = new TextView(getActivity());
            answerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            answerView.setText(task.answers.get(i).get("answer"));

            answerContainer.addView(answerView);
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (ISurveyListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException("Activity must implement DrawerCallback.");
        }
    }

}
