package com.example.component;


import java.util.ArrayList;
import java.util.List;

public enum EvaluationEnum {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5");


    private String title;

    EvaluationEnum(final String title) {
        this.title = title;
    }

    public static List<String> getTitles() {
        List<String> list = new ArrayList<>();
        for (EvaluationEnum value : values())
            list.add(value.getTitle());
        return list;
    }

    public String getTitle() {
        return this.title;
    }

}

