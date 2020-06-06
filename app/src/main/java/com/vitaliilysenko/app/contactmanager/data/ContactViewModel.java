package com.vitaliilysenko.app.contactmanager.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    
    private ContactRepository repository;
    
    private LiveData<List<Contact>> allContact;
    
    public ContactViewModel(Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContact = repository.getAllContact();
    }
    
    public LiveData<List<Contact>> getAllContact() {
        return allContact;
    }
    public  void delete(Contact contact){
       repository.delete(contact);
    }
    
    public void update(Contact contact) {
        repository.update(contact);
    }
    public void reset(){
        repository.reset();
    }
}

