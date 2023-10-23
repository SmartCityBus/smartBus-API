package com.example.smartbusapi.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BUSModel {
    private String document;
    private String id;
    private String name;
    private boolean booleans;

    public BUSModel(String document, String id, String name, boolean booleans) {
        this.document = document;
        this.id = id;
        this.name = name;
        this.booleans = booleans;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBooleans() {
        return booleans;
    }

    public void setBooleans(boolean booleans) {
        this.booleans = booleans;
    }
}
