package com.radiumdigital.meggnify.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Location implements Parcelable {

    public Integer locationId;
    public Integer missionId;

    public Location(JSONObject data) {
        try {
            locationId = data.getInt("location_id");
            missionId = data.getInt("mission_id");
        } catch (Exception e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
    }

    public Location(Parcel in) {
        locationId = in.readInt();
        missionId = in.readInt();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeInt(locationId);
        dest.writeInt(missionId);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
