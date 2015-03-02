package com.meggnify.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.meggnify.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task implements Parcelable {

    public String question;
    public String question_type;
    public String answer_type;

    public List<HashMap<String, String>> answers;

    public int icon;

    public Task(JSONObject data) {
        try {
            question = data.getString("question");
            question_type = data.getString("question_type");
            answer_type = data.getString("answer_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        answers = new ArrayList<HashMap<String, String>>();
        try {
            JSONArray answersData = data.getJSONArray("answers");
            for (int i = 0; i < answersData.length(); i++) {
                JSONObject answerData = answersData.getJSONObject(i);
                HashMap<String, String> answer = new HashMap<>();
                answer.put("answer", answerData.getString("answer"));
                answers.add(answer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (question_type.equals("action")) {
            icon = R.drawable.ic_general_survey;
        } else {
            icon = R.drawable.ic_general_survey;
        }
    }

    public Task(Parcel in) {
        question = in.readString();
        question_type = in.readString();
        answer_type = in.readString();
        
        in.readList(answers, List.class.getClassLoader());

        icon = in.readInt();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(question);
        dest.writeString(question_type);
        dest.writeString(answer_type);

        dest.writeList(answers);

        dest.writeInt(icon);
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}
