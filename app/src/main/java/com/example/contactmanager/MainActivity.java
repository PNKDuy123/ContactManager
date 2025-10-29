package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contactmanager.adapter.ContactAdapter;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.utils.ContactManager;
import com.example.contactmanager.widget.AlphabetIndexer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ContactManager contactManager;
    private EditText etSearch;
    private FloatingActionButton fabAdd;
    private AlphabetIndexer alphabetIndexer;
    private LinearLayoutManager layoutManager;

    private static final int REQUEST_ADD_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo views
        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        fabAdd = findViewById(R.id.fabAdd);
        alphabetIndexer = findViewById(R.id.alphabetIndexer);

        // Khởi tạo ContactManager
        contactManager = new ContactManager(this);

        // Thêm sample data (chỉ lần đầu)
//        SampleDataHelper.addSampleContacts(this);

        // Thiết lập RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadContacts();

        // Xử lý Alphabet Indexer (thanh chữ cái bên phải)
        alphabetIndexer.setOnLetterSelectedListener(new AlphabetIndexer.OnLetterSelectedListener() {
            @Override
            public void onLetterSelected(String letter) {
                scrollToLetter(letter);
            }
        });

        // Xử lý tìm kiếm realtime
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Xử lý nút thêm danh bạ
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, REQUEST_ADD_CONTACT);
        });
    }

    // Load danh sách danh bạ
    private void loadContacts() {
        List<Contact> contacts = contactManager.getContacts();
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);
    }

    // Scroll đến chữ cái được chọn
    private void scrollToLetter(String letter) {
        if (adapter == null) return;

        List<Contact> contacts = contactManager.getContacts();

        for (int i = 0; i < contacts.size(); i++) {
            String firstLetter = contacts.get(i).getName().substring(0, 1).toUpperCase();

            if (firstLetter.equals(letter) ||
                    (letter.equals("#") && !firstLetter.matches("[A-Z]"))) {
                layoutManager.scrollToPositionWithOffset(i, 0);
                break;
            }
        }
    }

    // Nhận kết quả từ AddContactActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK) {
            // Reload danh sách và sắp xếp lại
            List<Contact> contacts = contactManager.getContacts();
            adapter.updateList(contacts);
            etSearch.setText(""); // Clear search
        }
    }
}