package com.vitaliilysenko.app.contactmanager.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import static com.vitaliilysenko.app.contactmanager.data.ContactRoomDatabase.DEFAULT_DATA;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContact;
    
    ContactRepository(Application application) {
        ContactRoomDatabase database = ContactRoomDatabase.getDatabase(application);
        contactDao = database.contactDao();
        allContact = contactDao.getAlphabetizedContact();
    }
    
    LiveData<List<Contact>> getAllContact() {
        return allContact;
    }
    
    void update(final Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.update(contact);
            }
        });
    }
    
    void delete(final Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.delete(contact);
            }
        });
    }
    
    void reset() {
        ContactRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.deleteAll();
                contactDao.insert(DEFAULT_DATA);
            }
        });
    }
}
