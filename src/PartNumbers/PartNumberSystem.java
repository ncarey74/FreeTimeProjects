/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartNumbers;

import IndividualParts.VehiclePart;
import static PartNumbers.PartNumber.ENGINE_TYPE;
import static PartNumbers.PartNumber.INVALID_FIELD;
import static PartNumbers.PartNumber.SEAT_TYPE;
import static PartNumbers.PartNumber.WHEEL_TYPE;

/**
 *
 * @author Carey
 */
public class PartNumberSystem {
    private final PartNumberCatalog catalog;

    public PartNumberSystem() {
        this.catalog = new PartNumberCatalog();
    }
    
    public PartNumber generatePartNumber(VehiclePart part){
        String partType;
        String partNumber;
        
        switch (part.getPartType()) {
            case VehiclePart.ENGINE:
                partType = ENGINE_TYPE;
                break;
            case VehiclePart.WHEEL:
                partType = WHEEL_TYPE;
                break;
            case VehiclePart.SEAT:
                partType = SEAT_TYPE;
                break;
            default:
                partType = INVALID_FIELD;
                break;
        }
        partNumber = partType + "-001";
        
        return new PartNumber(partNumber);
    }
    
}
