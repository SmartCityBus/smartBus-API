package com.example.smartbusapi.service;

import com.example.smartbusapi.controller.BUSController;
import com.example.smartbusapi.model.BUSModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class BUSService {

    public String createBUS(BUSModel busModel) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("du_smart_bus")
                .document(busModel.getDocumentId()).set(busModel);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public BUSModel getBUS(String documentId) throws ExecutionException, InterruptedException {
        System.out.println("받음" + documentId);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("du_smart_bus").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        BUSModel busModel;
        if(document.exists()){
            busModel = document.toObject(BUSModel.class);
            return busModel;
        }
        return null;
    }

    public String updateBUS(BUSModel busModel) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("du_smart_bus").document(busModel.getDocumentId()).set(busModel);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteBUS(String documentId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("du_smart_bus").document(documentId).delete();
        return "삭제 성공 : " + documentId;
    }


}
