package com.example.smartbusapi.service;

import com.example.smartbusapi.model.Chats;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ChatsService {

    public String createChats(Chats chats) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("chats")
                .document(chats.getVehicleno()).set(chats);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Chats getChats(String vehicleno) throws ExecutionException, InterruptedException {
        System.out.println("받은 id : " + vehicleno);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("chats").document(vehicleno);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Chats chats;
        if(document.exists()){
            chats = document.toObject(Chats.class);
            return chats;
        }
        return null;
    }

    public List<Chats> getChatsList() throws InterruptedException, ExecutionException {
        System.out.println("모든 채팅 출력");
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = dbFirestore.collection("Chats");
        ApiFuture<QuerySnapshot> query = collectionReference.get();
        QuerySnapshot querySnapshot  = query.get();
        List<Chats> allChats = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot) {
            Chats ChatsInfo = document.toObject(Chats.class);
            allChats.add(ChatsInfo);
        }
        return allChats;
    }

    public String updateChats(Chats chats) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("chats").document(chats.getVehicleno()).set(chats);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteChats(String vehicleno){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("chats").document(vehicleno).delete();
        return "삭제 성공 : " + vehicleno;
    }

}
