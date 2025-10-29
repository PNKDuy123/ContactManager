package com.example.contactmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.contactmanager.model.Contact;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private static final String PREFS_NAME = "ContactPrefs";
    private static final String KEY_CONTACTS = "contacts";
    private SharedPreferences prefs;

    public ContactManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Lưu danh sách danh bạ vào SharedPreferences
    public void saveContacts(List<Contact> contacts) {
        JSONArray jsonArray = new JSONArray();

        for (Contact contact : contacts) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", contact.getName());
                jsonObject.put("phone", contact.getPhone());
                jsonObject.put("email", contact.getEmail());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CONTACTS, jsonArray.toString());
        editor.apply();
    }

    // Lấy danh sách danh bạ từ SharedPreferences
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        String jsonString = prefs.getString(KEY_CONTACTS, "[]");

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String phone = jsonObject.getString("phone");
                String email = jsonObject.getString("email");

                contacts.add(new Contact(name, phone, email));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    // Thêm danh bạ mới
    public void addContact(Contact contact) {
        List<Contact> contacts = getContacts();
        contacts.add(contact);
        saveContacts(contacts);
    }
}