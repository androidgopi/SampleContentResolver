package com.sreeyainfotech.samplecontentresolver;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sreeyainfotech.samplecontentresolver.adapter.ContactAdapter;
import com.sreeyainfotech.samplecontentresolver.model.ContactsModel;

import java.util.AbstractList;
import java.util.ArrayList;

public class ShowContactsActivity extends AppCompatActivity {

    private static final int PERMISSION_ALL = 1;
    private RecyclerView recycler_view;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE};
    private AbstractList<ContactsModel> contctList= new ArrayList<>();
    private ContactAdapter mAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contacts);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);

//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }
        fetchContacts();
    }

    public boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                } else {
                }
                return false;
            }
        }
        return true;
    }

    private void fetchContacts() {
       // Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uri = Uri.parse("content://com.sample.providersdb");

    //    String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String[] projection = null;

        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        while (cursor.moveToNext()) {
            ContactsModel contactsModel = new ContactsModel();
            String name = cursor.getString(cursor.getColumnIndex("Name"));
            String number = cursor.getString(cursor.getColumnIndex("Number"));
            String email = cursor.getString(cursor.getColumnIndex("Email"));
            String Address = cursor.getString(cursor.getColumnIndex("Address"));

            contactsModel.setName(name);
            contactsModel.setNumber(number);
            contactsModel.setAddress(Address);
            contactsModel.setEmail(email);
            contctList.add(contactsModel);
        }

        if (contctList.size() > 0) {
            showAdapter();
        }

    }

    private void showAdapter() {
        mAdapter = new ContactAdapter(contctList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        contctList.clear();
        fetchContacts();
    }

}
