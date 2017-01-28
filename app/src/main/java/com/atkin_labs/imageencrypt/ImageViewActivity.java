package com.atkin_labs.imageencrypt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Spencer on 1/27/17.
 */

public class ImageViewActivity extends AppCompatActivity {

    public static String EXTRA_IMAGE_PATH = "com.atkin_labs.imageencrypt.image_path";

    public static Intent newIntent(Context packageContext, String imagePath) {
        android.content.Intent i = new Intent(packageContext, ImageViewActivity.class);
        i.putExtra(EXTRA_IMAGE_PATH, imagePath);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        //Bitmap imgBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(EXTRA_IMAGE_PATH));

        ImageView imageView = (ImageView)findViewById(R.id.main_image_view);
        new LoadImage(imageView, getIntent().getStringExtra(EXTRA_IMAGE_PATH)).execute();
        //imageView.setImageBitmap(imgBitmap);
    }

    private class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private ImageView imv;
        private String path;

        public LoadImage(ImageView imv, String p) {
            this.imv = imv;
            this.path = p;//imv.getTag().toString();
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            Bitmap bitmap = null;
            File file = new File(path);

            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }

            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null && imv != null) {
                imv.setVisibility(View.VISIBLE);
                imv.setImageBitmap(result);
            } else if (imv != null) {
                imv.setVisibility(View.GONE);
            }
        }

    }
}
