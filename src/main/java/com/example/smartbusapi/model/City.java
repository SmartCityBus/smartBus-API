package com.example.smartbusapi.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private String citycode;
    private String cityname;

    public City() {
    }

    public City(String citycode, String cityname) {
        this.citycode = citycode;
        this.cityname = cityname;
    }

    public String getCityCode() {
        return citycode;
    }

    public void setCityCode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityName() {
        return cityname;
    }

    public void setCityName(String cityname) {
        this.cityname = cityname;
    }
}
