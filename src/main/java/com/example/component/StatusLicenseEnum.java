package com.example.component;


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

}

