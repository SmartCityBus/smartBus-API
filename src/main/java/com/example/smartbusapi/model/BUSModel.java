package com.example.smartbusapi.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BUSModel {
    private String documentId;
    private String id;
    private String name;
    private boolean booleans;

    public BUSModel() {
    }

    public BUSModel(String documentId, String id, String name, boolean booleans) {
        this.documentId = documentId;
        this.id = id;
        this.name = name;
        this.booleans = booleans;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
