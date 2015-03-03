package com.meggnify.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.meggnify.R;
import com.meggnify.helper.BaseFragment;
import com.meggnify.helper.so;
import com.meggnify.model.Question;
import com.raaf.rDialog;

import org.w3c.dom.Text;

/**
 * Created by Luqman Hakim on 3/3/2015.
 */
public class MissionQuestionFragment extends BaseFragment {
    Button BtDoneNext;
    TextView TvQuestion,TvQuestionPersen;
    FrameLayout FramePb;
    int pos;
    Boolean isDone = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_question, container, false);

        FramePb = (FrameLayout) view.findViewById(R.id.FramePb);
        TvQuestion = (TextView) view.findViewById(R.id.TvQuestion);
        TvQuestionPersen = (TextView) view.findViewById(R.id.TvQuestionPersen);
        BtDoneNext= (Button) view.findViewById(R.id.BtDoneNext);
        BtDoneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDone) {
                    so.currentAssignment.setCurrent_question(so.currentAssignment.getCurrent_question()+1);
                    ((MeggnifyActivity)getActivity()).MissionSelectItem(1);
                }
                else{
                    BtDoneNext.setText("Next");
                    FramePb.setVisibility(View.VISIBLE);
                    TvQuestionPersen.setVisibility(View.VISIBLE);
                    isDone=true;
                }

            }
        });
        return view;
    }

    Question q;
    @Override
    public void onResume() {
        super.onResume();
        q = so.currentAssignment.getQuestions().get(so.currentAssignment.getCurrent_question());
        setQuestion();
    }

    private void setQuestion(){
        TvQuestion.setText(q.getQuestion());
    }
}
