package cz.muni.fi.model.perperiod;

import cz.muni.fi.model.GeographicCoordinates;


public class Bus {
    private String id;
    private GeographicCoordinates coordinates;
    private long siteId;
    private int armNum;
    private int state;

    public Bus(String id) {
        this.id = id;
    }

    public Bus(String id, GeographicCoordinates coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public long getSiteId() {
        return siteId;
    }

    public void setCoordinates(GeographicCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public int getArmNum() {
        return armNum;
    }

    public void setArmNum(int armNum) {
        this.armNum = armNum;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
