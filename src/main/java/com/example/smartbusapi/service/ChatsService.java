package com.example.smartbusapi.service;

import com.example.smartbusapi.model.Chats;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        System.out.println("받은 vehicleno : " + vehicleno);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("chats").document(vehicleno);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            Chats chats = document.toObject(Chats.class);

            // 유효성 검사: chats가 null이 아닌지 확인
            if (chats != null) {
//                System.out.println("Messages for Vehicle No: " + vehicleno);
//                List<Map<String, Object>> messages = chats.getMessages();
//
//                // messages가 null이 아니고 비어 있지 않은 경우 메시지 출력
//                if (messages != null && !messages.isEmpty()) {
//                    Map<String, Object> firstMessage = messages.get(0);
//                    System.out.println(firstMessage);
//                    for (Map<String, Object> message : messages) {
//                        System.out.println("Message: " + message);
//                        System.out.println("id : " + message.get("id"));
//                    }
//                } else {
//                    System.out.println("No messages found.");
//                }

                return chats;
            }
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

    // messages 변경 후 업데이트하는 함수
    public String updateText(Chats chats, String vehicleno) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("chats").document(vehicleno).set(chats);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteChats(String vehicleno){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("chats").document(vehicleno).delete();
        return "삭제 성공 : " + vehicleno;
    }

    // 요청 받으면 60분 마다 해당 차량 번호 채팅방의 0번 인덱스를 삭제
    public String deleteText(String vehicleno) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        // 해당 차량 번호에 대한 채팅 정보를 가져옴
        Chats chats = getChats(vehicleno);
        if (chats != null) {
            List<Map<String, Object>> messages = chats.getMessages();
            // messages가 null이 아니고 비어 있지 않은 경우 0번 인덱스 삭제
            if (messages != null && !messages.isEmpty()) {
                messages.remove(0);
                // 업데이트된 메시지 리스트를 다시 저장
                updateText(chats, vehicleno);
                return "60분 후 텍스트 삭제 완료";
            } else {
                return vehicleno + "번 메시지 삭제 실패";
            }
        }
        return vehicleno + "번 채팅이 존재하지 않음";
    }
}
