package com.example.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ConstantConditions")
public class ContactFragment extends Fragment {

    EditText editText;

    RecyclerView contactRV;
    ArrayList<ContactsModal> contactModelArrayList;
    ContactRVAdapter contactRVAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, null);
        contactRV = view.findViewById(R.id.recyer_view_contacts);
        editText = view.findViewById(R.id.editTextTextPersonName);
        contactModelArrayList = new ArrayList<ContactsModal>();
        contactRVAdapter = new ContactRVAdapter(getContext(), contactModelArrayList);
        contactRV.setLayoutManager(new LinearLayoutManager(getContext()));
        contactRV.setAdapter(contactRVAdapter);
        FloatingActionButton addNewContactFAB = view.findViewById(R.id.idFABadd);

        prepareContactRV();

        getContacts();

        addNewContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication(), CreateNewContactActivity.class);
                startActivity(i);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());

            }
        });

        return view;
    }

//    void readContacts(){
//        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
//        while (phones.moveToNext())
//        {
//            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//            contactModelArrayList.add(new Model(name, phoneNumber));
//        }
//        contactRVAdapter.notifyDataSetChanged();
//        phones.close();
//    }
private void getContacts() {
    // this method is use to read contact from users device.
    // on below line we are creating a string variables for
    // our contact id and display name.
    String contactId = "";
    String displayName = "";
    // on below line we are calling our content resolver for getting contacts
    Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
    // on blow line we are checking the count for our cursor.
    if (cursor.getCount() > 0) {
        // if the count is greater than 0 then we are running a loop to move our cursor to next.
        while (cursor.moveToNext()) {
            // on below line we are getting the phone number.
             int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
            if (hasPhoneNumber > 0) {
                // we are checking if the has phone number is > 0
                // on below line we are getting our contact id and user name for that contact
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // on below line we are calling a content resolver and making a query
                Cursor phoneCursor = getActivity().getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{contactId},
                        null);
                // on below line we are moving our cursor to next position.
                if (phoneCursor.moveToNext()) {
                    // on below line we are getting the phone number for our users and then adding the name along with phone number in array list.
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactModelArrayList.add(new ContactsModal(displayName, phoneNumber));
                }
                // on below line we are closing our phone cursor.
                phoneCursor.close();
            }
        }
    }

    cursor.close();

    contactRVAdapter.notifyDataSetChanged();
}


    private void  filter(String tostring){
        List<ContactsModal> filterlist = new ArrayList<>();
        for(ContactsModal item : contactModelArrayList){

            if(item.getName().toLowerCase().contains(tostring.toLowerCase())){
                filterlist.add(item);
            }
        }
        if(filterlist.isEmpty()){
            Toast.makeText(getActivity(), "No Contact Found", Toast.LENGTH_SHORT).show();
        }
        else {
            contactRVAdapter.filterlist(filterlist);
        }
    }

    private void prepareContactRV(){
        contactRVAdapter = new ContactRVAdapter(getActivity(), contactModelArrayList);
        contactRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
        contactRV.setAdapter(contactRVAdapter);
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplication());


        builder.setTitle("Need Permissions");


        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName() , null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();
    }



}
