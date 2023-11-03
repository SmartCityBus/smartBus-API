package com.example.smartbusapi;

import com.example.smartbusapi.controller.RouteController;
import com.example.smartbusapi.controller.VehicleController;
import com.example.smartbusapi.model.Route;
import com.example.smartbusapi.model.Vehicle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class dataSchedule {

    private final RouteController routeController;
    private final VehicleController vehicleController;

    public dataSchedule(RouteController routeController, VehicleController vehicleController) {
        this.routeController = routeController;
        this.vehicleController = vehicleController;
    }

    // 일정 주기마다 route, vehicle 최신화 하는 함수
    @Scheduled(fixedDelay = 60000) // 10분
    public void scheduled_route() {
        try {
            System.out.println("route 스케줄러 실행");
            routeController.createRoute(new Route());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 6000) // 10분, 초기 딜레이 1분
    public void scheduled_vehicle() {
        try {
            System.out.println("vehicle 스케줄러 실행");
            vehicleController.createVehicle(new Vehicle());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
