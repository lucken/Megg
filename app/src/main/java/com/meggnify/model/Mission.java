package com.meggnify.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.meggnify.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Mission implements Parcelable {

    public Integer missionId;
    public String name;
    public String objective;
    public String instruction;
    public String endDate;
    public String endTime;
    public String type;
    public Float cost;

    public int icon;
    public int iconDark;

    public Mission(JSONObject data) {
        try {
            missionId = data.getInt("id");
            name = data.getString("name");
            objective = data.getString("objective");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        instruction = "";
        try {
            instruction = data.getString("instruction");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        cost = Float.valueOf("0.0");
        try {
            String price = data.getString("price");
            if (price != null) {

            } else {

            }
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        try {
            endDate = data.getString("end_date");
            endTime = data.getString("end_time");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        type = "General Survey";
        try {
            type = data.getString("assignment_type");
        } catch (JSONException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }

        if (type.equals("Mystery Audit")) {
            icon = R.drawable.ic_mystery_audit;
            iconDark = R.drawable.ic_mystery_audit_dark;
        } else {
            icon = R.drawable.ic_general_survey;
            iconDark = R.drawable.ic_mystery_audit_dark;
        }
    }

    public Mission(Parcel in) {
        missionId = in.readInt();
        name = in.readString();
        objective = in.readString();
        instruction = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        type = in.readString();
        cost = in.readFloat();

        icon = in.readInt();
        iconDark = in.readInt();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeInt(missionId);
        dest.writeString(name);
        dest.writeString(objective);
        dest.writeString(instruction);
        dest.writeString(endDate);
        dest.writeString(endTime);
        dest.writeString(type);
        dest.writeFloat(cost);

        dest.writeInt(icon);
        dest.writeInt(iconDark);
    }

    public static final Parcelable.Creator<Mission> CREATOR = new Parcelable.Creator<Mission>() {
        public Mission createFromParcel(Parcel in) {
            return new Mission(in);
        }

        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };

}
