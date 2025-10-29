package com.example.contactmanager.utils;

import android.graphics.Color;

public class AvatarColorHelper {

    private static final int[] COLORS = {
            Color.parseColor("#2196F3"), // Blue
            Color.parseColor("#4CAF50"), // Green
            Color.parseColor("#FF9800"), // Orange
            Color.parseColor("#E91E63"), // Pink
            Color.parseColor("#9C27B0"), // Purple
            Color.parseColor("#00BCD4"), // Cyan
            Color.parseColor("#FF5722"), // Deep Orange
            Color.parseColor("#3F51B5"), // Indigo
            Color.parseColor("#009688"), // Teal
            Color.parseColor("#795548"), // Brown
    };

    public static int getColorForName(String name) {
        if (name == null || name.isEmpty()) {
            return COLORS[0];
        }

        // Tính hash từ tên để chọn màu
        int hash = Math.abs(name.hashCode());
        int index = hash % COLORS.length;

        return COLORS[index];
    }
}