package com.wangzai.lovesy.core.net.bean;

public class LocationBean {

    /**
     * city : Montreal
     * country : Canada
     * position : {"latitude":45.4732984,"longitude":-73.6384879}
     */

    private String city;
    private String country;
    private PositionBean position;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }
}
