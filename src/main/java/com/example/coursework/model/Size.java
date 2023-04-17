package com.example.coursework.model;

public enum Size {
    S("23"),
    M("25"),
    L("27"),
    XL("29");

    private final String sizeRu;

    Size(String sizeRu) {
        this.sizeRu = sizeRu;
    }

    public String getSizeRu() {
        return sizeRu;
    }
}