package com.atkin_labs.imageencrypt;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Spencer on 1/27/17.
 */

public class ContactsModel {
    private static ContactsModel sContactsModel;
    private List<Contact> mContacts;

    public static ContactsModel get(Context context) {
        if (sContactsModel == null) {
            sContactsModel = new ContactsModel(context);
        }
        return sContactsModel;
    }

    private ContactsModel(Context context) {
        scanDir(context);
    }

    public void scanDir(Context context) {
        mContacts = new ArrayList<>();
        /*File picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (picturesDir != null) {
            String[] files = picturesDir.list();
            Collections.addAll(mPictures, files);
        }*/
        // TODO: Load contacts from file
    }

    public List<Contact> getContacts() {
        return mContacts;
    }
}
