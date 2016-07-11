/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartNumbers;

import java.util.Objects;

/**
 * Represents the part number of any given part.
 * A part number consists of two fields, the part type and the individual ID. The
 * part type is a number between 0 and 999, and the individual ID is the unique
 * identifier for the part within a type. This will be in the form of "xxx-yyy".
 * For example, a engine will have the part type corresponding to engine and the 
 * unique identifier will be refer to this specific engine. 
 * @author Carey
 */
public class PartNumber {
    //General part number constraints
    private static final int PART_NUM_LENGTH = 7;
    private static final int FIELD_LENGTH = 3;
    
    //Invalid part number values
    public static final String INVALID_PART_NUM = "000-000";
    public static final String INVALID_FIELD = "000";
    private static final String MINIMUM_PART_NUM = "001-001";
    
    //Part types
    public static final String ENGINE_TYPE = "001";
    public static final String WHEEL_TYPE = "002";
    public static final String SEAT_TYPE = "003";
    
    String partType = INVALID_FIELD;
    String uniqueID = INVALID_FIELD;
    String partNumber = INVALID_PART_NUM;

    public PartNumber(String partNumber) {
        String[] partNumFields;
        String partTypeField;
        String uniqueIDField;
        boolean correctLength;
        boolean validTypeField;
        boolean validIDField;
        boolean atLeastMinPartNum;
        
        //Part number is in the format "xxx-yyy".
        if (partNumber.contains("-") && partNumber.length() == PART_NUM_LENGTH) {
            partNumFields = partNumber.split("-");
            partTypeField = partNumFields[0];
            uniqueIDField = partNumFields[1];
            correctLength = partTypeField.length() == FIELD_LENGTH && 
                            uniqueIDField.length() == FIELD_LENGTH;
            validTypeField = partTypeField.matches("[0-9]+") && correctLength;
            validIDField = uniqueIDField.matches("[0-9]+") && correctLength;
            atLeastMinPartNum = partNumber.compareTo(MINIMUM_PART_NUM) >= 0;
            
            /*
            Part number has valid fields and has a value greater than or equal
            to 001-001. Otherwise, the part number and its fields are invalid.
            */
            if (validTypeField && validIDField && atLeastMinPartNum) {
                this.partNumber = partNumber;
                this.partType = partTypeField;
                this.uniqueID = uniqueIDField;
            }
        }
    }

    /**
     * Gets the type of the part.
     * @return 
     */
    public String getPartType() {
        return partType;
    }

    /**
     * Sets the type of the part.
     * @param partType 
     */
    public void setPartType(String partType) {
        this.partType = partType;
    }

    /**
     * Gets the unique identifier of the part.
     * @return 
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Sets the unique identifier of the part.
     * @param uniqueID 
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
    
    /**
     * Gets the part number.
     * @return partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Determines if a part number is valid.
     * A valid part number is in the form of xxx-yyy.
     * @return true if the part number is valid, false otherwise.
     */
    public boolean isValidPartNumber() {
        return !partNumber.equals(INVALID_PART_NUM);
    }

    /**
     * Determines if two part numbers are equal.
     * @param obj - the part number to compare against
     * @return true if both objects are the same part number, false if the 
     * comparison object is not a part number or is not the same part number.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PartNumber other = (PartNumber) obj;
        if (!Objects.equals(this.partType, other.partType)) {
            return false;
        }
        if (!Objects.equals(this.uniqueID, other.uniqueID)) {
            return false;
        }
        if (!Objects.equals(this.partNumber, other.partNumber)) {
            return false;
        }
        return true;
    }    
}
