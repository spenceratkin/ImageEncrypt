package com.atkin_labs.imageencrypt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Spencer on 1/30/17.
 */

public class NewContactActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add) {
            EditText contactNameField = (EditText)findViewById(R.id.contact_name_field);
            EditText contactPublicKeyField = (EditText)findViewById(R.id.contact_public_key_field);
            String contactName = contactNameField.getText().toString();
            String contactPublicKey = contactPublicKeyField.getText().toString();
            if (!contactName.equals("") && !contactPublicKey.equals("")) {
                try {
                    ContactsModel.get(getApplicationContext()).addContact(getApplicationContext(), new Contact(contactName, Base64.decode(contactPublicKey, Base64.DEFAULT)));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
