package com.atkin_labs.imageencrypt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Spencer on 1/26/17.
 */

public class ContactsFragment extends Fragment {
    private RecyclerView mContactRecyclerView;
    private ContactAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mContactRecyclerView = (RecyclerView)view;
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mContactRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        updateUI();

        return view;
    }

    public void notifyNewData() {
        ContactsModel contactsModel = ContactsModel.get(getActivity());
        contactsModel.scanDir(getActivity());
        mAdapter.setContacts(contactsModel.getContacts());
        mAdapter.notifyDataSetChanged();
    }

    private void updateUI() {
        ContactsModel contactsModel = ContactsModel.get(getActivity());
        List<Contact> contacts = contactsModel.getContacts();

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
            //Intent intent = ImageViewActivity.newIntent(getContext(), mFullPicturePath);//new Intent(getActivity(), MainFragmentActivity.class);
            //startActivity(intent);
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

            View view = layoutInflater.inflate(R.layout.list_item_picture, parent, false);
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
