package com.radiumdigital.meggnify.model;

import com.radiumdigital.meggnify.R;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import org.json.JSONObject;

public class Mission implements Parcelable {

    public Integer missionId;
	public String name;
	public String objective;
	public String description;
	public String endDate;
	public String endTime;
	public String type;
	public String location;
	public Float cost;
	
	public String icon;
	
	public Mission(JSONObject data) {
        try {
            missionId = data.getInt("id");
            name = data.getString("name");
            objective = data.getString("objective");
            cost = Float.valueOf(data.getString("price"));
        } catch (Exception e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
        }
        description = "";
        endDate = "";
        endTime = "";
        type = "";
        location = "";
	}
	
	public Mission(Parcel in) {
        missionId = in.readInt();
		name = in.readString();
		objective = in.readString();
		description = in.readString();
		endDate = in.readString();
		endTime = in.readString();
		type = in.readString();
		location = in.readString();
		cost = in.readFloat();
		
		icon = in.readString();
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
		dest.writeString(description);
		dest.writeString(endDate);
		dest.writeString(endTime);
		dest.writeString(type);
		dest.writeString(location);
		dest.writeFloat(cost);
		
		dest.writeString(icon);
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
