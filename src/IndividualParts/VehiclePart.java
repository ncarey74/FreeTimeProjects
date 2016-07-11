/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IndividualParts;

import PartNumbers.PartNumber;

/**
 * Abstract class for vehicle parts.
 * All parts inherit from this class.
 * @author Carey
 */
public abstract class VehiclePart {
    public static final String ENGINE = "Engine";
    public static final String WHEEL = "Wheel";
    public static final String SEAT = "Seat";
    
    private PartNumber partNumber;
    private String partType;

    public PartNumber getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(PartNumber partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }
    
}
