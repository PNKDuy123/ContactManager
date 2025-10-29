 Ứng Dụng Quản Lý Danh Bạ (Contact Manager)

 Mô tả
Ứng dụng Android quản lý danh bạ với các tính năng:
-  Thêm danh bạ mới
-  Hiển thị danh sách danh bạ
-  **Sắp xếp tự động theo alphabet A-Z**
-  **Hiển thị section header (J, K, L, M...)**
-  **Fast scroll với thanh alphabet bên phải**
-  **Avatar màu động theo tên**
-  Tìm kiếm danh bạ theo tên (realtime)
-  Lưu trữ dữ liệu bằng SharedPreferences (JSON format)
- Sample data tự động khi chạy lần đầu

 Cấu trúc Project

```
app/
├── src/main/java/com/example/contactmanager/
│   ├── MainActivity.java              # Màn hình chính
│   ├── AddContactActivity.java        # Màn hình thêm danh bạ
│   ├── model/
│   │   └── Contact.java               # Model class
│   ├── adapter/
│   │   └── ContactAdapter.java        # RecyclerView Adapter với alphabet sorting
│   ├── utils/
│   │   ├── ContactManager.java        # Quản lý SharedPreferences
│   │   └── AvatarColorHelper.java     # Màu avatar động
│   └── widget/
│       └── AlphabetIndexer.java       # Custom view thanh alphabet
│
├── src/main/res/
│   ├── layout/
│   │   ├── activity_main.xml          # Layout màn hình chính
│   │   ├── activity_add_contact.xml   # Layout thêm danh bạ
│   │   └── item_contact.xml           # Layout item với section header
│   └── drawable/
│       └── circle_background.xml      # Background cho avatar
│
└── src/main/AndroidManifest.xml       # Manifest file
```

## 🛠️ Các Bước Cài Đặt

### 1. Tạo Project Mới trong Android Studio
- Mở Android Studio → New Project
- Chọn "Empty Activity"
- Name: `ContactManager`
- Package name: `com.example.contactmanager`
- Language: **Java**
- Minimum SDK: API 21 (Android 5.0)

### 2. Cấu hình Dependencies
Mở file `build.gradle (Module: app)` và thêm dependencies (xem file build.gradle đã cung cấp)

### 3. Tạo Cấu trúc Thư mục
Tạo các package sau trong `src/main/java/com/example/contactmanager/`:
- `model` - chứa Contact.java
- `adapter` - chứa ContactAdapter.java
- `utils` - chứa ContactManager.java, SampleDataHelper.java, AvatarColorHelper.java
- `widget` - chứa AlphabetIndexer.java

### 4. Copy Code vào Từng File
Sao chép code từ các artifact đã tạo vào đúng vị trí:

1. **Contact.java** → `model/Contact.java`
2. **ContactManager.java** → `utils/ContactManager.java`
3. **SampleDataHelper.java** → `utils/SampleDataHelper.java`
4. **AvatarColorHelper.java** → `utils/AvatarColorHelper.java`
5. **ContactAdapter.java** → `adapter/ContactAdapter.java`
6. **AlphabetIndexer.java** → `widget/AlphabetIndexer.java`
7. **MainActivity.java** → `MainActivity.java`
8. **AddContactActivity.java** → `AddContactActivity.java`

### 5. Tạo Layout Files
Tạo các file XML trong `res/layout/`:
1. **activity_main.xml**
2. **activity_add_contact.xml**
3. **item_contact.xml**

### 6. Tạo Drawable Resource
Tạo file `circle_background.xml` trong `res/drawable/`

### 7. Cập nhật AndroidManifest.xml
Copy nội dung từ file AndroidManifest.xml đã cung cấp

### 8. Sync và Build Project
- Click **File** → **Sync Project with Gradle Files**
- Đợi sync hoàn tất
- Click **Build** → **Make Project**

##  Chạy Ứng Dụng
1. Kết nối thiết bị Android hoặc khởi động Emulator
2. Click nút **Run** (Shift + F10)
3. Chọn thiết bị và chạy

##  Các Chức Năng Chính

### 1. SharedPreferences với JSON
```java
// Lưu danh sách
JSONArray jsonArray = new JSONArray();
for (Contact contact : contacts) {
    JSONObject json = new JSONObject();
    json.put("name", contact.getName());
    json.put("phone", contact.getPhone());
    json.put("email", contact.getEmail());
    jsonArray.put(json);
}
prefs.edit().putString("contacts", jsonArray.toString()).apply();
```

### 2. RecyclerView với Adapter
```java
recyclerView.setLayoutManager(new LinearLayoutManager(this));
adapter = new ContactAdapter(contacts);
recyclerView.setAdapter(adapter);
```

### 3. Tìm Kiếm Realtime
```java
etSearch.addTextChangedListener(new TextWatcher() {
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.filter(s.toString());
    }
});
```

## Cách Sử Dụng

1. **Thêm danh bạ mới:**
   - Nhấn nút + (FAB) ở góc dưới bên phải
   - Nhập Tên, Số điện thoại, Email
   - Nhấn "Lưu"

2. **Tìm kiếm danh bạ:**
   - Gõ tên vào ô "Search for people"
   - Kết quả được lọc tự động

3. **Xem danh sách:**
   - Tất cả danh bạ hiển thị trong RecyclerView
   - Mỗi item hiển thị avatar, tên, số điện thoại, email

## Kiểm Tra Dữ liệu SharedPreferences

Để xem dữ liệu đã lưu trong SharedPreferences:
```bash
adb shell
run-as com.example.contactmanager
cat shared_prefs/ContactPrefs.xml
```

Hoặc dùng Device File Explorer trong Android Studio:
```
/data/data/com.example.contactmanager/shared_prefs/ContactPrefs.xml
```

##  Troubleshooting

### Lỗi: Cannot resolve symbol 'R'
- File → Invalidate Caches → Invalidate and Restart
- Build → Clean Project → Rebuild Project

### Lỗi: Không hiển thị danh sách
- Kiểm tra LogCat xem có lỗi parse JSON không
- Đảm bảo ContactManager được khởi tạo đúng

### Lỗi: App crash khi thêm danh bạ
- Kiểm tra validation trong AddContactActivity
- Xem LogCat để xác định lỗi cụ thể

