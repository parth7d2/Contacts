package com.example.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.TextDrawableHelper;


import java.util.ArrayList;
import java.util.List;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ViewHolder> {

    Context context;
    ArrayList<Model> modelArrayList;
    public ContactRVAdapter(Context context, ArrayList<Model> modelArrayList){
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contacts_rv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        Model model = modelArrayList.get(position);
        holder.contactTV.setText(modelArrayList.get(position).getName());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();

        TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                        .width(100)
                                .height(100)
                                        .endConfig()
                                                .buildRound(model.getName().substring(0, 1), color);
        holder.contactIV.setImageDrawable(drawable2);


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(context, ContactFragment.class);
                in.putExtra("name", model.getName());
                in.putExtra("contact", model.getNumber());
                context.startActivity(in);
            }
        });

//        holder.sms.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                final EditText message = new EditText(view.getContext());
//                message.setHint("Type your Message...");
//                message.setText("hello");
//
//                final AlertDialog.Builder sending_sms = new AlertDialog.Builder(view.getContext());
//
//                sending_sms.setIcon(R.drawable.ic_baseline_textsms_24)
//                        .setTitle("SENT TO : " + modelArrayList.get(position).getName())
//                        .setView(message)
//                        .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                sendSMS(modelArrayList.get(position).getNumber(),message.getText().toString());
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).create();
//
//                sending_sms.show();
//            }
//        });
    }

    public void filterlist(List<Model> filterlist){
        modelArrayList = (ArrayList<Model>) filterlist;
        notifyDataSetChanged();
    }

//    private void sendSMS(String phnumber, String message){
//        try{
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phnumber, null, message, null, null);
//            Toast.makeText(context, "Message sent to :" + phnumber, Toast.LENGTH_SHORT).show();
//        }
//        catch (Exception exception){
//            Toast.makeText(context, "Something went Wrong...", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView contactTV;

        ImageView contactIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactIV = itemView.findViewById(R.id.idIVContact);
            contactTV = itemView.findViewById(R.id.idTVContactName);
        }
    }

}
