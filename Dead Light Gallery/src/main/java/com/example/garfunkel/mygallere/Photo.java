package com.example.garfunkel.mygallere;

import android.os.Parcel;
import android.os.Parcelable;


public class Photo implements Parcelable {

    private String mUrl;
    private String mTitle;

    public Photo(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected Photo(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static  Photo[] getSpacePhotos() {                                         //создание массива ссылок на изображения

        return new Photo[]{
                new Photo("https://i.imgur.com/isy8JVw.jpg", "Heart"),
                new Photo("https://i.imgur.com/2tQoKAI.jpg", "Wood"),
                new Photo("https://i.imgur.com/iXkW0pq.jpg", "Ring"),
                new Photo("https://i.imgur.com/NbKcpR9.jpg", "Elf"),
                new Photo("https://i.imgur.com/yNmERmB.jpg", "Shuttle"),
                new Photo("https://i.imgur.com/h10VLU5.jpg", "Astro"),
                new Photo("https://i.imgur.com/b4PGYsP.jpg", "Cobain"),

        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}
