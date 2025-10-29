package com.example.contactmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contactmanager.R;
import com.example.contactmanager.model.Contact;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;
    private List<Contact> contactListFull;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = new ArrayList<>();
        this.contactListFull = new ArrayList<>();
        updateList(contactList);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        // Hiển thị tên, phone, email
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());
        holder.tvEmail.setText(contact.getEmail());

        // Hiển thị initial (chữ cái đầu)
        String initial = contact.getName().substring(0, 1).toUpperCase();
        holder.tvInitial.setText(initial);

        // Set màu avatar động theo tên
        holder.avatarCircle.setBackgroundColor(
                com.example.contactmanager.utils.AvatarColorHelper.getColorForName(contact.getName())
        );

        // Hiển thị section letter (chữ cái bên trái)
        if (position == 0) {
            // Contact đầu tiên luôn hiển thị letter
            holder.tvSectionLetter.setVisibility(View.VISIBLE);
            holder.tvSectionLetter.setText(initial);
        } else {
            // So sánh với contact trước đó
            Contact previousContact = contactList.get(position - 1);
            String previousInitial = previousContact.getName().substring(0, 1).toUpperCase();

            if (!initial.equals(previousInitial)) {
                // Khác section -> hiển thị letter
                holder.tvSectionLetter.setVisibility(View.VISIBLE);
                holder.tvSectionLetter.setText(initial);
            } else {
                // Cùng section -> ẩn letter
                holder.tvSectionLetter.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // Cập nhật danh sách và sắp xếp theo tên
    public void updateList(List<Contact> newList) {
        contactList.clear();
        contactList.addAll(newList);

        // Sắp xếp theo tên A-Z
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });

        contactListFull = new ArrayList<>(contactList);
        notifyDataSetChanged();
    }

    // Tìm kiếm theo tên
    public void filter(String query) {
        contactList.clear();

        if (query.isEmpty()) {
            contactList.addAll(contactListFull);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Contact contact : contactListFull) {
                String name = contact.getName().toLowerCase();

                // Kiểm tra ký tự đầu tiên của tên
                if (!name.isEmpty() && name.charAt(0) == lowerCaseQuery.charAt(0)) {
                    contactList.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

    // ViewHolder
    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvEmail, tvInitial, tvSectionLetter;
        View avatarCircle;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvInitial = itemView.findViewById(R.id.tvInitial);
            tvSectionLetter = itemView.findViewById(R.id.tvSectionLetter);
            avatarCircle = itemView.findViewById(R.id.avatarCircle);
        }
    }
}