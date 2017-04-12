package cz.muni.fi.dto;


public class ArmDto {
    private int armAngle;
    private String streetName;
    private int nextSiteId;
    private int arrivalVehicles;
    private int priorityVehicles;
    private double priorityVehiclesRatio;
    private int vehicles;
    private double vehiclesRatio;

    public int getArmAngle() {
        return armAngle;
    }

    public void setArmAngle(int armAngle) {
        this.armAngle = armAngle;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNextSiteId() {
        return nextSiteId;
    }

    public void setNextSiteId(int nextSiteId) {
        this.nextSiteId = nextSiteId;
    }

    public int getArrivalVehicles() {
        return arrivalVehicles;
    }

    public void setArrivalVehicles(int arrivalVehicles) {
        this.arrivalVehicles = arrivalVehicles;
    }

    public int getPriorityVehicles() {
        return priorityVehicles;
    }

    public void setPriorityVehicles(int priorityVehicles) {
        this.priorityVehicles = priorityVehicles;
    }

    public double getPriorityVehiclesRatio() {
        return priorityVehiclesRatio;
    }

    public void setPriorityVehiclesRatio(double priorityVehiclesRatio) {
        this.priorityVehiclesRatio = priorityVehiclesRatio;
    }

    public int getVehicles() {
        return vehicles;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    public double getVehiclesRatio() {
        return vehiclesRatio;
    }

    public void setVehiclesRatio(double vehiclesRatio) {
        this.vehiclesRatio = vehiclesRatio;
    }
}
