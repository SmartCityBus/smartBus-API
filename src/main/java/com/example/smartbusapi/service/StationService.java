package com.example.smartbusapi.service;

import com.example.smartbusapi.model.Station;
import com.google.api.core.ApiFuture;
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

}
