package com.example.component;


import java.util.ArrayList;
import java.util.List;

public enum StatusLicenseEnum {

    YES("Yes"),
    NO("No");

    private String title;


    StatusLicenseEnum(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


    public static List<String> getTitles() {
        List<String> list = new ArrayList<>();
        for (StatusLicenseEnum value : values())
            list.add(value.getTitle());
        return list;
    }

}

