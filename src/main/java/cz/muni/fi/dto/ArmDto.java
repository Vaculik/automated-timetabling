package cz.muni.fi.dto;


public class ArmDto {
    private int armAngle;
    private String streetName;
    private int nextSiteId;

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
}
