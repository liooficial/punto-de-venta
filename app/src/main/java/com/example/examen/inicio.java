package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class inicio extends AppCompatActivity {
    private ImageView mGifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        mGifImageView = findViewById(R.id.gif_imageview);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.inicio);
        mediaPlayer.start();
        Glide.with(this)
                .asGif()
                .load(R.drawable.boosg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mGifImageView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                Intent i =new Intent(inicio.this, login.class);
                startActivity(i);
            }
        }, 3500);

    }
}