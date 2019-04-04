package com.example.component;


import java.util.ArrayList;
import java.util.List;

public enum KindExamEnum {

    TEST("Test"),
    AUTODROME("Autodrome"),
    DRIVING("driving");

    private String title;


    KindExamEnum(final String title) {
        this.title = title;
    }

    public static List<String> getTitles() {
        List<String> list = new ArrayList<>();
        for (KindExamEnum value : values())
            list.add(value.getTitle());
        return list;
    }

    public String getTitle() {
        return this.title;
    }

}

