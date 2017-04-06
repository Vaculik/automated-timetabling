package cz.muni.fi.dto;


import cz.muni.fi.model.structural.dual.GeographicCoordinates;

import java.util.List;

public class SiteDto {
    private int id;
    private GeographicCoordinates coordinates;
    private List<ArmDto> arms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GeographicCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<ArmDto> getArms() {
        return arms;
    }

    public void setArms(List<ArmDto> arms) {
        this.arms = arms;
    }
}
