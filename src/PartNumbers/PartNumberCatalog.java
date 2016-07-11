/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartNumbers;

import IndividualParts.VehiclePart;
import static IndividualParts.VehiclePart.ENGINE;
import static PartNumbers.PartNumber.ENGINE_TYPE;
import static PartNumbers.PartNumber.SEAT_TYPE;
import static PartNumbers.PartNumber.WHEEL_TYPE;
import java.util.ArrayList;
import java.util.List;

/**
 * The part number catalog.
 * Contains only unique, valid part numbers.
 * @author Carey
 */
public class PartNumberCatalog {
    private final List<PartNumber> partNumberCatalog;
    private final List<PartNumber> engineParts;
    private final List<PartNumber> wheelParts;
    private final List<PartNumber> seatParts;

    public PartNumberCatalog() {
        partNumberCatalog = new ArrayList<>();
        engineParts = new ArrayList<>();
        wheelParts = new ArrayList<>();
        seatParts = new ArrayList<>();
    }
    
    /**
     * Adds a part number if it is not already in the system.
     * @param partNumber - part number to add to the system
     * @return true if the part number was successfully added, false otherwise
     */
    public boolean addPartNumber(PartNumber partNumber) {
        if (partNumber.isValidPartNumber() && !partNumberCatalog.contains(partNumber)) {
            partNumberCatalog.add(partNumber);
            switch (partNumber.getPartType()) {
                case ENGINE_TYPE:
                    engineParts.add(partNumber);
                    break;
                case WHEEL_TYPE:
                    wheelParts.add(partNumber);
                    break;
                case SEAT_TYPE:
                    seatParts.add(partNumber);
                default:
                    break;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Removes a part number if it is in the system.
     * @param partNumber - part number to remove from the system
     * @return true if the part number was removed, false if the part number 
     * didn't exist or was not a valid part number
     */
    public boolean removePartNumber(PartNumber partNumber) {
        if (partNumber.isValidPartNumber() && partNumberCatalog.contains(partNumber)) {
            partNumberCatalog.remove(partNumber);
            switch (partNumber.getPartType()) {
                case ENGINE_TYPE:
                    engineParts.remove(partNumber);
                    break;
                case WHEEL_TYPE:
                    wheelParts.remove(partNumber);
                    break;
                case SEAT_TYPE:
                    seatParts.remove(partNumber);
                default:
                    break;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Determines if the part number already exists.
     * @param partNumber - part number to check against the system
     * @return true if the part number exists, false otherwise
     */
    public boolean isPartNumberInSystem(PartNumber partNumber) {
        return partNumberCatalog.contains(partNumber);
    }
    
    /**
     * 
     * @param part
     * @return 
     */
    public String generateUniqueID(VehiclePart part){
        int uniqueID;
        
        switch (part.getPartType()) {
            case ENGINE:
                uniqueID = engineParts.size() + 1;
                break;
            default:
                uniqueID = 0;
        } 
        
        return String.format("%03d", uniqueID);
    }
}
