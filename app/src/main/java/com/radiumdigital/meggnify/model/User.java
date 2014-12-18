package com.radiumdigital.meggnify.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class User implements Parcelable {

	public User(SparseArray<String> data) {
		
	}
	
	public User(Parcel in) {
		
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}
	
		public User[] newArray(int size) {
			return new User[size];
		}
	};

}
