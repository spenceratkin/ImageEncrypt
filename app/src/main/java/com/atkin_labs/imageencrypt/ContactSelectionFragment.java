package com.atkin_labs.imageencrypt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Spencer on 2/1/17.
 */

public class ContactSelectionFragment extends DialogFragment {
    private RecyclerView mContactRecyclerView;
    private ContactAdapter mAdapter;
    // this method create view for your Dialog
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_selection, container, false);

        mContactRecyclerView = (RecyclerView)view;
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mContactRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        updateUI();

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_contact_selection, (ViewGroup)getView());
        mContactRecyclerView = (RecyclerView)view.findViewById(R.id.contacts_recycler_view_dialog);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        updateUI();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Choose contact to encrypt for:")
                .setView(view)
                /*.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do something
                            }
                        }
                )*/.create();
    }

    private void updateUI() {
        ContactsModel contactsModel = ContactsModel.get(getContext());
        List<Contact> contacts = contactsModel.getContacts();
        Log.d("ImageEncrypt", contacts.toString());

        mAdapter = new ContactAdapter(contacts);
        mContactRecyclerView.setAdapter(mAdapter);
    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Contact mContact;
        private TextView mNameTextView;

        public ContactHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = (TextView)itemView.findViewById(R.id.list_item_contact_contact_name);
        }

        public void bindContact(Contact contact) {
            mContact = contact;
            mNameTextView.setText(contact.getContactName());
        }

        @Override
        public void onClick(View v) {
            ImageViewActivity parent = (ImageViewActivity)getActivity();
            dismiss();
            parent.encryptForContact(mContact);
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
        private List<Contact> mContacts;

        public ContactAdapter(List<Contact> contacts) {
            mContacts = contacts;
        }

        public void setContacts(List<Contact> newContacts) { mContacts = newContacts; }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_contact, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bindContact(contact);
        }
        @Override
        public int getItemCount() {
            return mContacts.size();
        }
    }
}
