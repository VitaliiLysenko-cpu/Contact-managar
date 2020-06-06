package com.vitaliilysenko.app.contactmanager.data;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "contact_table")
public class Contact implements Parcelable {
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }
        
        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    private String imageUrl;
    private String name;
    private String surname;
    @PrimaryKey
    @NonNull
    private String email;
    
    public Contact(String imageUrl, String name, String surname, String email) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    
    protected Contact(Parcel in) {
        this.imageUrl = in.readString();
        this.name = in.readString();
        this.surname = in.readString();
        this.email = in.readString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getImageUrl(), contact.getImageUrl()) &&
                Objects.equals(getName(), contact.getName()) &&
                Objects.equals(getSurname(), contact.getSurname()) &&
                Objects.equals(getEmail(), contact.getEmail());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getImageUrl(), getName(), getSurname(), getEmail());
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.email);
    }
}
