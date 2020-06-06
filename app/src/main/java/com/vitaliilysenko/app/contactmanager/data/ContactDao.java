package com.vitaliilysenko.app.contactmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Update
    void update(Contact contact);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Contact> contacts);
    
    @Delete
    void delete(Contact contact);
    
    @Query("DELETE from contact_table")
    void deleteAll();
    
    @Query("SELECT *from contact_table ORDER BY surname ASC")
    LiveData<List<Contact>> getAlphabetizedContact();
}
