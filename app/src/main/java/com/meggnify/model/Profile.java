package com.meggnify.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tricipta on 12/23/14.
 */
public class Profile implements Parcelable {

    public String name;
    public String email;
    public String rank;
    public String rankImage;
    public Integer point;
    public Integer totalPoint;

    public String harvest;
    public String consumed;

    public Profile(JSONObject data) {
        try {
            name = data.getString("full_name");
            email = data.getString("email");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        try {
            rank = data.getString("rank_name");
            rankImage = data.getString("rank_pict");
            point = data.getInt("point");
            totalPoint = data.getInt("next_rank_point");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        try {
            harvest = data.getString("harvest_value");
            consumed = data.getString("consumed_value");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
    }

    public Profile(Parcel in) {
        name = in.readString();
        email = in.readString();

        rank = in.readString();
        rankImage = in.readString();
        point = in.readInt();
        totalPoint = in.readInt();

        harvest = in.readString();
        consumed = in.readString();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(name);
        dest.writeString(email);

        dest.writeString(rank);
        dest.writeString(rankImage);
        dest.writeInt(point);
        dest.writeInt(totalPoint);

        dest.writeString(harvest);
        dest.writeString(consumed);
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
