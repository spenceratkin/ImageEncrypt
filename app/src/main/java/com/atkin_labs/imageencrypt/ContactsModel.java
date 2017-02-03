package com.atkin_labs.imageencrypt;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Spencer on 1/27/17.
 */

public class ContactsModel {
    private static ContactsModel sContactsModel;
    private List<Contact> mContacts;
    private String CONTACTS_FILENAME = "com.atkin_labs.imageencrypt.contacts";

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
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File contactsFile = new File(dir, CONTACTS_FILENAME);
        if (contactsFile.exists()) {
            try {
                ObjectInputStream fis = new ObjectInputStream(new FileInputStream(contactsFile));
                mContacts = (List<Contact>)fis.readObject();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveContacts(Context context) {
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File contactsFile = new File(dir, CONTACTS_FILENAME);
        try {
            contactsFile.createNewFile();
            ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(contactsFile));
            fos.writeObject(mContacts);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContact(Context context, Contact newContact) {
        mContacts.add(newContact);
        saveContacts(context);
    }

    public List<Contact> getContacts() {
        return mContacts;
    }
}
