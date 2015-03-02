package com.meggnify.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.meggnify.R;
import com.meggnify.helper.API;
import com.meggnify.helper.BaseFragment;
import com.meggnify.helper.Util;
import com.meggnify.helper.so;
import com.meggnify.model.Assignment;
import com.meggnify.model.Question;
import com.raaf.rDialog;

import java.util.ArrayList;

/**
 * Created by Luqman Hakim on 3/2/2015.
 */
public class MissionChecklistFragment extends BaseFragment {
    private QuestionAdapter missionAdapter;
    private ListView questionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_checklist, container, false);
        questionList = (ListView) view.findViewById(R.id.ListMissionQuestion);
        missionAdapter = new QuestionAdapter(getActivity(), so.currentAssignment.getQuestions());
        questionList.setAdapter(missionAdapter);
        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
               // selectItem(position);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (so.currentAssignment.getQuestions().size() == 0)
            API.AssQuest(Util.getToken(getActivity()), so.currentAssignment.getId(), handler);
    }

    @Override
    protected void onHandlerResponse(Message msg) {
        super.onHandlerResponse(msg);
        rDialog.SetToast(getActivity(), so.result.getInfo());
        missionAdapter.notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView TvQuestion;
        ImageView ImgIcon;
    }

    private class QuestionAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;
        private ArrayList<Question> questions;

        public QuestionAdapter(Context context, ArrayList<Question> questions) {
            mContext = context;
            this.questions = questions;
            mInflater = ((Activity) mContext).getLayoutInflater();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return questions.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return questions.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_question, parent, false);

                holder = new ViewHolder();
                holder.TvQuestion = (TextView) convertView.findViewById(R.id.TvQuestion);
                holder.ImgIcon = (ImageView) convertView.findViewById(R.id.ImgQuestion);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Question q = (Question) getItem(position);
            if (q != null) {
                holder.TvQuestion.setText(q.getQuestion());
                holder.ImgIcon.setImageResource(R.drawable.ic_mystery_audit);


            }

            return convertView;
        }
    }
}
