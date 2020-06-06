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
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcSMNASNhmJgkZZITyEE6eul9bl9hdLSZaxdZ9P5qGriaDb_YcIs&usqp=CAU"
                    , "name1 ", "surname1 ", "email1 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcSRZo9pMbqqGhflREwpqpwBHd1ZLyziYaxeF0UXsG0nqJDmr1uY&usqp=CAU"
                    , "name2 ", "surname2 ", "email2 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcS89mwJlKjQuqJsw-iG3i0fa_Xvh8vd9ZsGkRQ7gcy2zZyQGD8L&usqp=CAU"
                    , "name3 ", "surname3 ", "email3 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcRaiOAZ_Nh1Mx6q6uLRY9eiKPPqE8AFX4YxmZD4g3zzpj6sALyK&usqp=CAU"
                    , "name4 ", "surname4 ", "email4 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcT8GQ-9xCL5VBpQtQT-l6paNdk4R-6z7xirtkXvrDhhffO34ofd&usqp=CAU"
                    , "name5 ", "surname5 ", "email5 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcTGX85HOOE0cANoyed_kkM5xUEhpFUZ1xjkLtaYDFScDwNy6bfh&usqp=CAU"
                    , "name6 ", "surname6 ", "email6 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcTNAbp4chClGMfaX2DW3c9G6Mpr8uAjuUscGS8DKXIMdXk3mANc&usqp=CAU"
                    , "name7 ", "surname7 ", "email7 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcQZ8dB50E2WhmiTEcl9UB6Ikr_TEWElFhhL1VSUa6jodtyvFK55&usqp=CAU"
                    , "name8 ", "surname8 ", "email8 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcQVC-DLRG8vVRRLSlFXiDBX00FO_V9TjM08xz9OlnPL45DRcAhH&usqp=CAU"
                    , "name9 ", "surname9 ", "email9 "),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcSZ5YqWX0ztj6KYISgtUs8SzwafD1SWXUhJqzS7FSFvdFJ9g4gO&usqp=CAU"
                    , "name10", "surname10", "email10"),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcTz31rH3btFgIU_zqyCrx78pOjpRG7mrQtxODYZmmXvxhcVg-a5&usqp=CAU"
                    , "name11", "surname11", "email11"),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcSjUlqifY35PjdkLTC1aPmXEOaQspoh1OE6VMR7ndgtl4bQDptC&usqp=CAU"
                    , "name12", "surname12", "email12"),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcTSWCYZoTQTFIF7xgZVVTsPWiEYIq_O8tLc4beyz1kXrmCmZtuf&usqp=CAU"
                    , "name13", "surname13", "email13"),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcQccTIBn6Tgmpgjf2DXKGEGas7fwUIWXtqF-kxXYdJEPJZZBiBG&usqp=CAU"
                    , "name14", "surname14", "email14"),
            new Contact("https://encrypted-tbn0.gstatic.com/images?q=" +
                    "tbn%3AANd9GcS-zM-HQnR3sYd5eEPAhY5URcWdAWUhs1EFbKRRh8yz2nEDV35w&usqp=CAU"
                    , "name14", "surname14", "email14")
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