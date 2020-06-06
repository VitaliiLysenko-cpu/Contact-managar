package com.vitaliilysenko.app.contactmanager.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {
    
    public static final List<Contact> DEFAULT_DATA = Arrays.asList(
            new Contact("https://www.w3schools.com/w3css/img_fjords.jpg", "name1 ", "surname1 ", "email1 "),
            new Contact("https://www.w3schools.com/w3images/lights.jpg", "name2 ", "surname2 ", "email2 "),
            new Contact("https://www.w3schools.com/css/img_mountains.jpg", "name3 ", "surname3 ", "email3 "),
            new Contact("https://www.w3schools.com/w3css/img_fjords.jpg", "name4 ", "surname4 ", "email4 "),
            new Contact("https://www.w3schools.com/w3images/lights.jpg", "name5 ", "surname5 ", "email5 "),
            new Contact("https://www.w3schools.com/css/img_mountains.jpg", "name6 ", "surname6 ", "email6 "),
            new Contact("https://www.w3schools.com/w3css/img_fjords.jpg", "name7 ", "surname7 ", "email7 "),
            new Contact("https://www.w3schools.com/w3images/lights.jpg", "name8 ", "surname8 ", "email8 "),
            new Contact("https://www.w3schools.com/css/img_mountains.jpg", "name9 ", "surname9 ", "email9 "),
            new Contact("https://www.w3schools.com/w3css/img_fjords.jpg", "name10", "surname10", "email10"),
            new Contact("https://www.w3schools.com/w3images/lights.jpg", "name11", "surname11", "email11"),
            new Contact("https://www.w3schools.com/css/img_mountains.jpg", "name12", "surname12", "email12"),
            new Contact("https://www.w3schools.com/w3css/img_fjords.jpg", "name13", "surname13", "email13"),
            new Contact("https://www.w3schools.com/w3images/lights.jpg", "name14", "surname14", "email14"),
            new Contact("https://www.w3schools.com/css/img_mountains.jpg", "name14", "surname14", "email14")
    );
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile ContactRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase databas) {
            super.onCreate(databas);
            
            databaseWriteExecutor.execute(() -> {
                ContactDao dao = INSTANCE.contactDao();
                dao.insert(DEFAULT_DATA);
            });
        }
    };
    
    static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    
    public abstract ContactDao contactDao();
}