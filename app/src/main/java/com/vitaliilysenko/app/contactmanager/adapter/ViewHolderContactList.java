package com.vitaliilysenko.app.contactmanager.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.vitaliilysenko.app.contactmanager.R;
import com.vitaliilysenko.app.contactmanager.data.Contact;

public class ViewHolderContactList extends RecyclerView.ViewHolder {
    
    private ImageView photoImageView;
    private TextView lastName;
    private TextView surName;
    private TextView numberPhone;
    private FloatingActionButton deleteContact;
    private AdapterContactList.OnContactListClickListener onContactClickListener;
    
    public ViewHolderContactList(@NonNull View itemView, AdapterContactList
            .OnContactListClickListener onContactClickListener) {
        super(itemView);
        
        photoImageView = itemView.findViewById(R.id.photo_imageView);
        lastName = itemView.findViewById(R.id.last_name_textView);
        surName = itemView.findViewById(R.id.sur_name_textView);
        numberPhone = itemView.findViewById(R.id.num_phone_textView);
        deleteContact = itemView.findViewById(R.id.delete_contact_Button);
        this.onContactClickListener = onContactClickListener;
    }
    
    public void bind(final Contact contact) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactClickListener
                        .onContactClick(getAdapterPosition());
            }
        });
        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onContactClickListener
                            .onContactDelete(getAdapterPosition());
        
            }
        });
        
        lastName.setText(contact.getName());
        surName.setText(contact.getSurname());
        numberPhone.setText(contact.getEmail());
    
        Picasso.get()
                .load(contact.getImageUrl())
                .into(photoImageView);
        
        
    }
}
