package com.atkin_labs.imageencrypt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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

        updateUI();

        return view;
    }

    private void updateUI() {
        PictureModel pictureModel = PictureModel.get(getActivity());
        List<String> pictures = pictureModel.getPictures();

        mAdapter = new PictureAdapter(pictures);
        mPictureRecyclerView.setAdapter(mAdapter);
    }

    private class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String mPicture;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public PictureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindPicture(String picture) {
            Log.d("ImageEncrypt", "Binding");
            mPicture = picture;
            mTitleTextView.setText("Test Title");
            mDateTextView.setText("Test date");
            mSolvedCheckBox.setChecked(false);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainFragmentActivity.class);
            startActivity(intent);
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {
        private List<String> mPictures;

        public PictureAdapter(List<String> crimes) {
            mPictures = crimes;
        }

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_picture, parent, false);
            return new PictureHolder(view);
        }

        @Override
        public void onBindViewHolder(PictureHolder holder, int position) {
            String crime = mPictures.get(position);
            holder.bindPicture(crime);
        }
        @Override
        public int getItemCount() {
            return mPictures.size();
        }
    }
}
