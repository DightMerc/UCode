package com.chikeandroid.tutsplus_glide;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import javax.net.ssl.SSLSocketFactory;


/**
 * Created by Chike on 2/12/2017.
 */





public class SpacePhotoActivity extends AppCompatActivity {



    public String MyLine="";

    private static final String TAG = "MyApp";
    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";

    



    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_detail);



        mImageView = (ImageView) findViewById(R.id.image);
        SpacePhoto spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)                                                                            //прогрузка изображения при помощи GLIDE
                .load(spacePhoto.getUrl())
                .asBitmap()
                .error(R.drawable.ic_cloud_off_red)
                .listener(new RequestListener<String, Bitmap>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        onPalette(Palette.from(resource).generate());
                        mImageView.setImageBitmap(resource);

                        return false;
                    }

                    public void onPalette(Palette palette) {
                            ViewGroup parent = (ViewGroup) mImageView.getParent().getParent();
                            parent.setBackgroundColor(Color.BLACK);
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

    }

   /* private SimpleTarget target = new SimpleTarget<Bitmap>() {

        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

           onPalette(Palette.from(bitmap).generate());
           mImageView.setImageBitmap(bitmap);
        }

        public void onPalette(Palette palette) {
            if (null != palette) {
                ViewGroup parent = (ViewGroup) mImageView.getParent().getParent();
                parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
            }
        }
    };*/
}
