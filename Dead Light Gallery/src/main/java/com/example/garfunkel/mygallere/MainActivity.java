package com.example.garfunkel.mygallere;


        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import com.bumptech.glide.Glide;



public class MainActivity extends AppCompatActivity {

private String TAG = "MyApp";                                                                                                    //тег для журнала LOG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                                                                                  //установка отображения

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        MainActivity.ImageGalleryAdapter adapter = new MainActivity.ImageGalleryAdapter(this, Photo.getSpacePhotos());
        recyclerView.setAdapter(adapter);                                                                                        //установка адаптера для подкачки и компоновки изображений на форму

    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {                           //реализация адаптера компоновки

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            Photo spacePhoto = mSpacePhotos[position];
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)                                                                                                    //использование библиотеки GLADE для загрузки, кеширования и отображения изображений
                    .load(spacePhoto.getUrl())
                    .placeholder(R.drawable.cloud)
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
            public void onClick(View view) {                                                                         //обработка нажатия на содержащий изображение элемент

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    Photo spacePhoto = mSpacePhotos[position];

                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    intent.putExtra(PhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
                    try {
                        startActivity(intent);
                    }
                    catch(Exception e){                                                                             //обработка ошибок открытия другого окна
                        Log.e(TAG, "EX",e);
                    }
                }
            }
        }

        private Photo[] mSpacePhotos;
        private Context mContext;

        public ImageGalleryAdapter(Context context, Photo[] spacePhotos) {
            mContext = context;
            mSpacePhotos = spacePhotos;
        }
    }
}