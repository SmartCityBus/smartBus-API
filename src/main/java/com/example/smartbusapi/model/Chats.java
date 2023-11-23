package com.example.smartbusapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Chats {
    private String vehicleno;
    private List<Map<String, Object>> messages;
    private String date;
    private String id;
    private String senderId;
    private String text;
}
