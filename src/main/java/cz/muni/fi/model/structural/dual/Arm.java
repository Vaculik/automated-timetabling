package cz.muni.fi.model.structural.dual;

public class Arm {

    private int armAngle;
    private int armNum;
    private int armOffset;
    private int leftLanes;
    private int rightLanes;
    private Site nextSite;
    private long osmWayId;
    private String streetName;
    private int arrivalVehicles;
    private int priorityVehicles;
    private double priorityVehiclesRatio;
    private int vehicles;
    private double vehiclesRatio;

    public static class Builder {
        private int armAngle;
        private int armNum;
        private int armOffset;
        private int leftLanes;
        private int rightLanes;
        private Site nextSite;
        private long osmWayId;
        private String streetName;

        public Builder armAngle(int val) {
            armAngle = val;
            return this;
        }

        public Builder armNum(int val) {
            armNum = val;
            return this;
        }

        public Builder armOffset(int val) {
            armOffset = val;
            return this;
        }

        public Builder leftLanes(int val) {
            leftLanes = val;
            return this;
        }

        public Builder rightLanes(int val) {
            rightLanes = val;
            return this;
        }

        public Builder nextSite(Site val) {
            nextSite = val;
            return this;
        }

        public Builder osmWayId(long val) {
            osmWayId = val;
            return this;
        }

        public Builder streetName(String val) {
            streetName = val;
            return this;
        }

        public Arm build() {
            return new Arm(this);
        }
    }

    private Arm(Builder builder) {
        this.armAngle = builder.armAngle;
        this.armNum = builder.armNum;
        this.armOffset = builder.armOffset;
        this.leftLanes = builder.leftLanes;
        this.rightLanes = builder.rightLanes;
        this.nextSite = builder.nextSite;
        this.osmWayId = builder.osmWayId;
        this.streetName = builder.streetName;
    }

    public int getArmAngle() {
        return armAngle;
    }

    public int getArmNum() {
        return armNum;
    }

    public int getArmOffset() {
        return armOffset;
    }

    public int getLeftLanes() {
        return leftLanes;
    }

    public int getRightLanes() {
        return rightLanes;
    }

    public Site getNextSite() {
        return nextSite;
    }

    public long getOsmWayId() {
        return osmWayId;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getArrivalVehicles() {
        return arrivalVehicles;
    }

    public void setArrivalVehicles(int arrivalVehicles) {
        this.arrivalVehicles = arrivalVehicles;
    }

    public double getPriorityVehiclesRatio() {
        return priorityVehiclesRatio;
    }

    public void setPriorityVehiclesRatio(double priorityVehiclesRatio) {
        this.priorityVehiclesRatio = priorityVehiclesRatio;
    }

    public double getVehiclesRatio() {
        return vehiclesRatio;
    }

    public void setVehiclesRatio(double vehiclesRatio) {
        this.vehiclesRatio = vehiclesRatio;
    }

    public int getPriorityVehicles() {
        return priorityVehicles;
    }

    public void setPriorityVehicles(int priorityVehicles) {
        this.priorityVehicles = priorityVehicles;
    }

    public int getVehicles() {
        return vehicles;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }
}
