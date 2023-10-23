package com.example.smartbusapi.controller;

import com.example.smartbusapi.model.BUSModel;
import com.example.smartbusapi.service.BUSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class BUSController {

    public BUSService busService;

    public BUSController(BUSService busService){
        this.busService = busService;
    }

    @PostMapping("/create")
    public String createBUS(@RequestBody BUSModel busModel) throws InterruptedException, ExecutionException{
        return busService.createBUS(busModel);
    }

    @GetMapping("/get")
    public BUSModel getBUS(@RequestParam String documentId) throws InterruptedException, ExecutionException{
        System.out.println("입력" + documentId);
        return busService.getBUS(documentId);
    }

    @PutMapping("/update")
    public String updateBUS(@RequestBody BUSModel busModel) throws InterruptedException, ExecutionException{
        return busService.updateBUS(busModel);
    }

    @DeleteMapping("/delete")
    public String deleteBUS(@RequestParam String documentId) throws InterruptedException, ExecutionException{
        return busService.deleteBUS(documentId);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndPoint(){
        return ResponseEntity.ok("테스트 완료");
    }

}