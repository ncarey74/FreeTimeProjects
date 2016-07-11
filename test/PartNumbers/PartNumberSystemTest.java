/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartNumbers;

import IndividualParts.VehiclePart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carey
 */
public class PartNumberSystemTest {
    
    public PartNumberSystemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generatePartNumber method, of class PartNumberSystem.
     */
    @Test
    public void testGeneratePartNumber() {
        System.out.println("generatePartNumber");
        VehiclePart part = null;
        PartNumberSystem instance = new PartNumberSystem();
        PartNumber expResult = null;
        PartNumber result = instance.generatePartNumber(part);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
