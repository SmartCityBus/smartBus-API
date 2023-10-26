package com.example.smartbusapi.controller;

import com.example.smartbusapi.model.Vehicle;
import com.example.smartbusapi.service.VehicleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class VehicleController {

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) { this.vehicleService = vehicleService; }

    @PostMapping("/create/vehicle")
    public String createVehicle(@RequestBody Vehicle vehicle) throws IOException, InterruptedException, ExecutionException {

        return "api db에 저장 완료";
    }

    @GetMapping("/get/vehicle")
    public Vehicle getVehicle(@RequestParam String vehicleno) throws InterruptedException, ExecutionException {
        return vehicleService.getVehicle(vehicleno);
    }

    @GetMapping("/get/vehicle/all")
    public List<Vehicle> getStationList() throws InterruptedException, ExecutionException{
        return vehicleService.getVehicleList();
    }

    @PutMapping("/update/vehicle")
    public String updateVehicle(@RequestBody Vehicle vehicle) throws InterruptedException, ExecutionException {
        return vehicleService.updateVehicle(vehicle);
    }

    @DeleteMapping("/delete/vehicle")
    public String deleteVehicle(@RequestParam String vehicleno) {
        return vehicleService.deleteVehicle(vehicleno);
    }

}
