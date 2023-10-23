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
    public String getBUS(@RequestParam String document) throws InterruptedException, ExecutionException{
        return busService.getBUS(document);
    }

    @PutMapping("/update")
    public String updateBUS(@RequestBody BUSModel busModel) throws InterruptedException, ExecutionException{
        return busService.updateBUS(busModel);
    }

    @DeleteMapping("/delete")
    public String deleteBUS(@RequestParam String document) throws InterruptedException, ExecutionException{
        return busService.deleteBUS(document);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndPoint(){
        return ResponseEntity.ok("테스트 완료");
    }

}