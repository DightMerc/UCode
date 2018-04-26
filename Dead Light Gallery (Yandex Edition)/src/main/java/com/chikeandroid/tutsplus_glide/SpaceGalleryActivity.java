package com.chikeandroid.tutsplus_glide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * Created by Chike on 2/12/2017.
 */

public class SpaceGalleryActivity extends AppCompatActivity {






    private static final String TAG = "MyApp";                               //объявления тега для журнала LOG

    String line1[];
    String LineEnd[];



    class LongAndComplicatedTask extends AsyncTask<Void, Void, String[]> {        //Асинхронная загрузка данных с Yandex Disk с помощью REST API

        @Override
        protected String[] doInBackground(Void... noargs) {
            try {

                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();

                String url = "https://cloud-api.yandex.net:443/v1/disk/resources/files?fields=items.file&media_type=image&preview_size=XXXL";        //задание ссылки-запроса

                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestProperty("Authorization", "OAuth AQAAAAAfSkT6AADLWwDkGslHw0kDvqI1tcHt3nk");                                     //задание заголовка с авторизацией

                connection.setRequestMethod("GET");                                                                                                  //установка типа запроса
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();                                                                                                                          //получение ответа от сервера
                bundle.putString("Key", response.toString());
                msg.setData(bundle);

                String date = bundle.getString("Key");                                                                                              
                date = date.replace("{","");                                        //удаление лишних ключевых слов и ответа, оставляя только сам URL адрес
                date = date.replace("[","");
                date = date.replace("}","");
                date = date.replace("]","");
                date = date.replace("\"items\":","");
                date = date.replace("\"preview\":","");
                date = date.replace("\"offset\":0","");
                date = date.replace(","," ");





                String[] line = date.split(" ");
                line1 = line;


                String[] result = line1;
                return result;
            } catch (Exception e) {
                Log.e(TAG, "Получено исключение", e);
                String result[] = null;

                return result;

            }

        }
    }



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private static final int BUFFER_SIZE = 4096;

    public static void downloadFile(String fileURL, String saveDir, int index1)                                             //попытка скачать файл через Yandex Disk Api
            throws IOException {

        try {
            URL connection = new URL(fileURL);
            URLConnection urlconn;
            urlconn = (URLConnection) connection.openConnection();
            //urlconn.setRequestMethod("GET");
            urlconn.setRequestProperty("Authorization", "OAuth AQAAAAAfSkT6AADLWwDkGslHw0kDvqI1tcHt3nk");

            urlconn.connect();
            InputStream in = null;
            in = urlconn.getInputStream();
            OutputStream writer = new FileOutputStream(saveDir);
            byte buffer[] = new byte[BUFFER_SIZE];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        }
        catch(IOException e){
            Log.e(TAG, "EX: ", e);
        }

    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_gallery);

try {

    LongAndComplicatedTask task = new LongAndComplicatedTask();
    task.execute();
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layoutManager);
    while (true){
        if (line1!=null){
            LineEnd = new String[line1.length];
            for (int i=0; i< line1.length;i++){
                String link;
                link = line1[i];
                link = link.replaceAll("file:","");
                link = link.replaceAll("\"","");
                Log.e(TAG,"Lined " + i + ": " + link);

                downloadFile(link,Environment.getExternalStorageDirectory().toString(),i);                          //скачивание файлов(кеширование)
                LineEnd[i] = link;
            }
            ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, SpacePhoto.getSpacePhotos(LineEnd));
            recyclerView.setAdapter(adapter);                                                                       //установка адаптера для прогрузки изображений
            break;
        }
    }


} catch(Exception e){
    Log.e(TAG, "Ошибка: ", e);
}
    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {             //описание адаптера для прогрузки изображений

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the layout
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

            MyViewHolder viewHolder = new MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            SpacePhoto spacePhoto = mSpacePhotos[position];
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)                                                                //прогрузка изображения при помощи GLIDE
                    .load(spacePhoto.getUrl())
                    .placeholder(R.drawable.ic_cloud_off_red)
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return (mSpacePhotos.length);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {                                                            //обработка нажатий на элемент изображения

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    SpacePhoto spacePhoto = mSpacePhotos[position];

                    Intent intent = new Intent(mContext, SpacePhotoActivity.class);
                    intent.putExtra(SpacePhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
                    startActivity(intent);
                }
            }
        }

        private SpacePhoto[] mSpacePhotos;
        private Context mContext;

        public ImageGalleryAdapter(Context context, SpacePhoto[] spacePhotos) {
            mContext = context;
            mSpacePhotos = spacePhotos;
        }
    }
}
