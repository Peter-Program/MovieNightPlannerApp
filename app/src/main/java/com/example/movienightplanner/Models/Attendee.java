package com.example.movienightplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Attendee implements Parcelable {
    private String name;
    private String email;
    private String id;

    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    private Attendee(Parcel in) {
        name = in.readString();
        email = in.readString();
        id = in.readString();
    }

    public static final Creator<Attendee> CREATOR = new Creator<Attendee>() {
        @Override
        public Attendee createFromParcel(Parcel in) {
            return new Attendee(in);
        }

        @Override
        public Attendee[] newArray(int size) {
            return new Attendee[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(id);
    }
}
