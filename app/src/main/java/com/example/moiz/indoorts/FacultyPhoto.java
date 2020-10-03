package com.example.moiz.indoorts;

import android.os.Parcel;
import android.os.Parcelable;

public class FacultyPhoto implements Parcelable {

    private String date_created;
    private String image_path;
    private String photo_id;
    private String user_id;

    public FacultyPhoto() {

    }

    public FacultyPhoto(String date_created, String image_path, String photo_id,
                        String user_id) {
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
    }

    protected FacultyPhoto(Parcel in) {
        date_created = in.readString();
        image_path = in.readString();
        photo_id = in.readString();
        user_id = in.readString();
    }

    public static Creator<FacultyPhoto> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<FacultyPhoto> CREATOR = new Creator<FacultyPhoto>() {
        @Override
        public FacultyPhoto createFromParcel(Parcel in) {
            return new FacultyPhoto(in);
        }

        @Override
        public FacultyPhoto[] newArray(int size) {
            return new FacultyPhoto[size];
        }
    };

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "FacultyPhoto{" +
                ", date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_created);
        dest.writeString(image_path);
        dest.writeString(photo_id);
        dest.writeString(user_id);
    }
}

