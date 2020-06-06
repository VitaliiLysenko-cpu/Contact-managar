package com.vitaliilysenko.app.contactmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitaliilysenko.app.contactmanager.R;
import com.vitaliilysenko.app.contactmanager.data.Contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterContactList extends RecyclerView.Adapter<ViewHolderContactList> {
    private OnContactListClickListener onContactListClickListener;
    private List<Contact> contacts = new ArrayList<>();
    
    public AdapterContactList(OnContactListClickListener onContacListClickListener) {
        this.onContactListClickListener = onContacListClickListener;
    }
    
    public ViewHolderContactList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolderContactList(view, onContactListClickListener);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolderContactList holder, int position) {
        holder.bind(contacts.get(position));
    }
    
    @Override
    public int getItemCount() {
        return contacts.size();
    }
    
    
    public List<Contact> getContacts() {
        return contacts;
    }
    
    public void setItems(Collection<Contact> listContacts) {
        if(listContacts == null || listContacts.equals(contacts)) {
            return;
        }
        contacts.clear();
        contacts.addAll(listContacts);
        notifyDataSetChanged();
    }
    
    public interface OnContactListClickListener {
        void onContactClick(int adapterPosition);
        
        void onContactDelete(int adapterPosition);
    }
}
