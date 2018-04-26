package com.example.garfunkel.mygallere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.support.v7.graphics.Palette;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
        import com.bumptech.glide.request.RequestListener;
        import com.bumptech.glide.request.target.Target;



public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);                                                                         //установка отображения

        mImageView = (ImageView) findViewById(R.id.image);
        Photo spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)                                                                                                        //загрузка изображения при помощи библиотеки GLIDE
                .load(spacePhoto.getUrl())
                .asBitmap()
                .error(R.drawable.cloud)
                .listener(new RequestListener<String, Bitmap>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {     //после завершения загрузки изображения закрасить фон черным
                                                                                                                                                                   //изначально подразумевалось использование палитры для определения
                        mImageView.setImageBitmap(resource);                                                                                                       //частого цвета картинки и установки его как фона
                        onPalette(Palette.from(resource).generate());

                        return false;
                    }

                    public void onPalette(Palette palette) {

                            ViewGroup parent = (ViewGroup) mImageView.getParent().getParent();
                            parent.setBackgroundColor((Color.BLACK));
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

    }
}
