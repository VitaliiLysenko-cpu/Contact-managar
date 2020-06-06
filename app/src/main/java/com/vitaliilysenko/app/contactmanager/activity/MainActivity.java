package com.vitaliilysenko.app.contactmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vitaliilysenko.app.contactmanager.R;
import com.vitaliilysenko.app.contactmanager.adapter.AdapterContactList;
import com.vitaliilysenko.app.contactmanager.data.Contact;
import com.vitaliilysenko.app.contactmanager.data.ContactViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String DETAIL_INFO = "detailInfo";
    private final static int DETAIL_REQUEST = 123;
    private RecyclerView contactRecyclerView;
    private AdapterContactList adapterContactList;
    private ContactViewModel contactViewModel;
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
                adapterContactList.setItems(contacts);
            }
        });
    }
    
    
    private void initRecyclerView() {
        contactRecyclerView = findViewById(R.id.recycler_contacts);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        AdapterContactList.OnContactListClickListener onContactListClickListener = new AdapterContactList.OnContactListClickListener() {
            @Override
            public void onContactClick(int adapterPosition) {
                adapterContactList.getItemId(adapterPosition);
                
                Intent intent = new Intent(MainActivity.this, ContactDetailsActivity.class);
                intent.putExtra(MainActivity.DETAIL_INFO, adapterContactList.getContacts().get(adapterPosition));
                startActivityForResult(intent, DETAIL_REQUEST);
            }
            
            @Override
            public void onContactDelete(int adapterPosition) {
                Contact contact = adapterContactList.getContacts().get(adapterPosition);
                contactViewModel.delete(contact);
            }
        };
        adapterContactList = new AdapterContactList(onContactListClickListener);
        contactRecyclerView.setAdapter(adapterContactList);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == DETAIL_REQUEST) {
                Contact contact = data.getParcelableExtra(DETAIL_INFO);
                contactViewModel.update(contact);
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.reset) {
            contactViewModel.reset();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}