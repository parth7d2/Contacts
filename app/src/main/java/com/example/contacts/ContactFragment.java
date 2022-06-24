package com.example.contacts;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ContactFragment extends Fragment {

    EditText editText;

    RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, null);
        recyclerView = view.findViewById(R.id.recyer_view_contacts);
        editText = view.findViewById(R.id.editTextTextPersonName);
        arrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        readContacts();

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

    void readContacts(){
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(new Model(name, phoneNumber));
        }
        adapter.notifyDataSetChanged();
        phones.close();
    }

    private void  filter(String tostring){
        List<Model> filterlist = new ArrayList<>();
        for(Model item : arrayList){

            if(item.getName().toLowerCase().contains(tostring.toLowerCase())){
                filterlist.add(item);
            }
        }

        adapter.filterlist(filterlist);
    }

}