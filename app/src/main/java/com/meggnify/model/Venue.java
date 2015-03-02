package com.meggnify.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Venue implements Parcelable {

    public Integer locationId;
    public Integer missionId;
    public String missionName;
    public String name;
    public String address;
    public Double latitude;
    public Double longitude;

    public Venue(JSONObject data) {
        try {
            locationId = data.getInt("location_id");
            name = data.getString("location_name");
            address = data.getString("location_address");
            latitude = data.getDouble("latitude");
            longitude = data.getDouble("longitude");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        try {
            missionId = data.getInt("assignment_id");
            missionName = data.getString("assignment_name");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
    }

    public Venue(Parcel in) {
        locationId = in.readInt();
        //missionId = in.readInt();
        missionName = in.readString();
        name = in.readString();
        address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
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
        //dest.writeInt(missionId);
        dest.writeString(missionName);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}
