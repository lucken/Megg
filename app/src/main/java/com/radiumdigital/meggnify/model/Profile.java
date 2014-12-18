package com.radiumdigital.meggnify.model;

import com.radiumdigital.meggnify.R;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile implements Parcelable {

	public String title;
	public String icon;
    public String name;
    public String email;
    public Integer point;
	
	public Profile(JSONObject data) {
        try {
            title = data.getString("points");
            icon = data.getString("rank_pict");
            name = data.getString("full_name");
            email = data.getString("email");
            point = data.getInt("next_rank_point");
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	
	public Profile(Parcel in) {
		title = in.readString();
		icon = in.readString();
        name = in.readString();
        email = in.readString();
        point = in.readInt();
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
		dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeInt(point);
	}
	
	public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
		public Profile createFromParcel(Parcel in) {
			return new Profile(in);
		}
	
		public Profile[] newArray(int size) {
			return new Profile[size];
		}
	};

}
