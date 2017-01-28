package com.atkin_labs.imageencrypt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Spencer on 1/25/17.
 */

public class GalleryFragment extends Fragment {
    private RecyclerView mPictureRecyclerView;
    private PictureAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mPictureRecyclerView = (RecyclerView)view;
        mPictureRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPictureRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        updateUI();

        return view;
    }

    public void notifyNewData() {
        PictureModel pictureModel = PictureModel.get(getActivity());
        pictureModel.scanDir(getActivity());
        mAdapter.setPictures(pictureModel.getPictures());
        mAdapter.notifyDataSetChanged();
    }

    private void updateUI() {
        PictureModel pictureModel = PictureModel.get(getActivity());
        List<String> pictures = pictureModel.getPictures();

        mAdapter = new PictureAdapter(pictures);
        mPictureRecyclerView.setAdapter(mAdapter);
    }

    private class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String mPicture;
        private ImageView mPictureThumbnail;
        private TextView mDateTextView;

        private String mFullPicturePath;

        public PictureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
            mPictureThumbnail = (ImageView)itemView.findViewById(R.id.list_item_picture_thumbnail);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_picture_date_textview);
            //mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindPicture(String picture) {
            Log.d("ImageEncrypt", picture);
            mPicture = picture;
            mFullPicturePath = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), picture).getAbsolutePath();
            //Bitmap imgBitmap = BitmapFactory.decodeFile(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), picture).getAbsolutePath());
            // Get the dimensions of the View
            /*int targetW = mPictureThumbnail.getWidth();
            int targetH = mPictureThumbnail.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
            mPictureThumbnail.setImageBitmap(bitmap);*/
            mDateTextView.setText(picture);
        }

        @Override
        public void onClick(View v) {
            Intent intent = ImageViewActivity.newIntent(getContext(), mFullPicturePath);//new Intent(getActivity(), MainFragmentActivity.class);
            startActivity(intent);
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {
        private List<String> mPictures;

        public PictureAdapter(List<String> pictures) {
            mPictures = pictures;
        }

        public void setPictures(List<String> newPictures) { mPictures = newPictures; }

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_picture, parent, false);
            return new PictureHolder(view);
        }

        @Override
        public void onBindViewHolder(PictureHolder holder, int position) {
            String picture = mPictures.get(position);
            holder.bindPicture(picture);
        }
        @Override
        public int getItemCount() {
            return mPictures.size();
        }
    }
}
