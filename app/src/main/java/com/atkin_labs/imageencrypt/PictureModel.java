package com.atkin_labs.imageencrypt;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Spencer on 1/25/17.
 */

public class PictureModel {
    private static PictureModel sPictureModel;
    private List<String> mPictures;

    public static PictureModel get(Context context) {
        if (sPictureModel == null) {
            sPictureModel = new PictureModel(context);
        }
        return sPictureModel;
    }

    private PictureModel(Context context) {
        mPictures = new ArrayList<>();
        mPictures.add("Test");
        /*File picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (picturesDir != null) {
            String[] files = picturesDir.list();
            Collections.addAll(mPictures, files);
        }*/
    }

    public List<String> getPictures() {
        return mPictures;
    }
/*
    public String getPicture(UUID id) {
        for (String crime : mPictures) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
*/
}
