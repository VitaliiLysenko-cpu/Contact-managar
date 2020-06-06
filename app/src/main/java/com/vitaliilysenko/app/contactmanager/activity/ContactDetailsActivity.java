package com.vitaliilysenko.app.contactmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.vitaliilysenko.app.contactmanager.R;
import com.vitaliilysenko.app.contactmanager.data.Contact;

import static com.vitaliilysenko.app.contactmanager.activity.MainActivity.DETAIL_INFO;

public class ContactDetailsActivity extends AppCompatActivity {
    private ImageView photo;
    private EditText lastName;
    private EditText surName;
    private EditText email;
    private Contact contact;
    private Toolbar toolbar;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        
        photo = findViewById(R.id.photo_detail);
        lastName = findViewById(R.id.last_name_detail);
        surName = findViewById(R.id.sur_name_detail);
        email = findViewById(R.id.email_detail);
        toolbar = findViewById(R.id.toolbar_contact_detail);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        contact = getIntent().getParcelableExtra(DETAIL_INFO);
        Picasso.get()
                .load(contact.getImageUrl())
                .into(photo);
        lastName.setText(contact.getName());
        surName.setText(contact.getSurname());
        email.setText(contact.getEmail());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_detail, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Contact result = new Contact(
                    contact.getImageUrl(),
                    lastName.getText().toString(),
                    surName.getText().toString(),
                    email.getText().toString()
            );
            Intent intent = new Intent();
            intent.putExtra(DETAIL_INFO, result);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
