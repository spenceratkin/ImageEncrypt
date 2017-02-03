package com.atkin_labs.imageencrypt;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Spencer on 1/27/17.
 */

public class ImageViewActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    public static String EXTRA_IMAGE_PATH = "com.atkin_labs.imageencrypt.image_path";
    ImageView mImageView;
    DialogFragment mAlertFragment;

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

        mImageView = (ImageView)findViewById(R.id.main_image_view);
        new LoadImage(mImageView, getIntent().getStringExtra(EXTRA_IMAGE_PATH)).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_image_view, menu);

        // Locate MenuItem with ShareActionProvider
        //MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        //mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_encrypt) {
            /*Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_TEXT, ));
            setShareIntent(shareIntent);*/
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            mAlertFragment = new ContactSelectionFragment();
            mAlertFragment.show(ft, "dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void encryptForContact(Contact contact) {
        // Hide dialog
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }

        Drawable drawable = mImageView.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        CryptoInterface crypto = new CryptoInterface(getFilesDir(), "keystorepass", "privkeypass");
        PublicKey publicKey = crypto.getPublicKey("privkeypass");//crypto.publicKeyFromBytes(contact.getPublicKey());
        String cipherText = crypto.encrypt(imageInByte, publicKey);
        Log.d("ImageEncrypt", cipherText);
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
