package com.radiumdigital.meggnify.model;

import com.radiumdigital.meggnify.R;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

public class Task implements Parcelable {

	public String title;
	public String question;
	
	public Task(JSONObject data) {
        try {
            title = "";
            question = data.getString("question");
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	
	public Task(Parcel in) {
		title = in.readString();
		question = in.readString();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(title);
		dest.writeString(question);
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
