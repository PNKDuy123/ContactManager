package com.example.contactmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.utils.ContactManager;

public class AddContactActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail;
    private Button btnSave;
    private ContactManager contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Khởi tạo views
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);

        // Khởi tạo ContactManager
        contactManager = new ContactManager(this);

        // Xử lý nút Lưu
        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validate
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }

        // Tạo contact mới và lưu
        Contact newContact = new Contact(name, phone, email);
        contactManager.addContact(newContact);

        Toast.makeText(this, "Đã lưu danh bạ", Toast.LENGTH_SHORT).show();

        // Trả kết quả về MainActivity
        setResult(RESULT_OK);
        finish();
    }
}