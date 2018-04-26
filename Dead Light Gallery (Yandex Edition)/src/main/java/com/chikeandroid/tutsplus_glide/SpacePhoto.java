package com.chikeandroid.tutsplus_glide;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;



import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chike on 2/11/2017.
 */

public class SpacePhoto implements Parcelable {





    private String mUrl;
    private String mTitle;

    public SpacePhoto(String url) {
        mUrl = url;
    }

    protected SpacePhoto(Parcel in) {
        mUrl = in.readString();
    }

    public static final Creator<SpacePhoto> CREATOR = new Creator<SpacePhoto>() {
        @Override
        public SpacePhoto createFromParcel(Parcel in) {
            return new SpacePhoto(in);
        }

        @Override
        public SpacePhoto[] newArray(int size) {
            return new SpacePhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }



    public static SpacePhoto[] getSpacePhotos(String[] s) {                         //получение ссылок на изображения в массив
        SpacePhoto[] array;
        array = new SpacePhoto[s.length];
try {
    for (int i = 0; i < s.length; i++) {
        array[i] = new SpacePhoto(s[i]);
    }
    return array;

} catch(Exception e) {
    Log.e(TAG, "Ошибка: ", e);
}

    return array;

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
