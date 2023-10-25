package com.example.smartbusapi.service;

import com.example.smartbusapi.model.Station;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class StationService {

    public String createStation(Station station) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("Station")
                .document(station.getNodenm()).set(station);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Station getStation(String nodenm) throws ExecutionException, InterruptedException {
        System.out.println("받음" + nodenm);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Station").document(nodenm);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Station station;
        if(document.exists()){
            station = document.toObject(Station.class);
            return station;
        }
        return null;
    }

    public String updateStation(Station station) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("Station").document(station.getNodenm()).set(station);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteStation(String nodenm){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("Station").document(nodenm).delete();
        return "삭제 성공 : " + nodenm;
    }
}
