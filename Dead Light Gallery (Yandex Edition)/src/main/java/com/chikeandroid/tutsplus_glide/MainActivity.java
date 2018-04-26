package com.chikeandroid.tutsplus_glide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent galleryIntent = new Intent(MainActivity.this, SpaceGalleryActivity.class);
        startActivity(galleryIntent);
        finish();
    }
}
