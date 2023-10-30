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
        getVehicleData(vehicle);
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

    // 버스 API 요청 함수
    private void getVehicleData(Vehicle vehicle) throws IOException, InterruptedException, ExecutionException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("37100", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        // 종류소ID Station에서 가져와야함
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("DJB30300052", "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(sb.toString());
        JsonNode response = rootNode.get("response");
        System.out.println("response : " + response);
        if (response != null) {
            JsonNode body = response.get("body");
            System.out.println("body : " + body);
            if (body != null) {
                JsonNode items = body.get("items");
                System.out.println("items : " + items);
                if (items != null) {
                    // "item" 객체 가져오기
                    JsonNode item = items.get("item");
                    System.out.println("item : " + item);
                    // 검색 결과가 단일 결과, 다중 결과로 나오기 때문에 조건에 따라 처리
                    if (item != null) {
                        if (item.isArray() && item.size() > 0){
                            for (int i = 0; i < item.size(); i++) {
                                JsonNode curItem = item.get(i);
                                processVehicle(curItem, vehicle);
                            }
                        } else{
                            processVehicle(item, vehicle);
                        }
                    }
                }
            }
        }
    }

    // 버스 DB저장을 처리하는 함수
    private void processVehicle (JsonNode item, Vehicle vehicle) throws ExecutionException, InterruptedException {
        JsonNode vehiclenoNode = item.get("vehicleno");
        JsonNode routenmNode = item.get("routenm");
        JsonNode nodeidNode = item.get("nodeid");
        System.out.println(vehiclenoNode + " " + routenmNode + " " + nodeidNode);
        if (vehiclenoNode != null && routenmNode != null && nodeidNode != null) {
            String vehicleno = vehiclenoNode.asText();
            String routenm = routenmNode.asText();
            String nodeid = nodeidNode.asText();
            System.out.println(vehicleno + " " + routenm + " " + nodeid);
            // Vehicle 컬렉션에 설정
            vehicle.setVehicleno(vehicleno);
            vehicle.setRoutenm(routenm);
            vehicle.setNodeid(nodeid);
            // 혼잡도 Congestion 디폴트 1
            vehicle.setCongestion("1");
            vehicleService.createVehicle(vehicle);
        }
    }
}
