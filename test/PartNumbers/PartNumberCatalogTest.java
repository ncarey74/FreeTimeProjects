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
public class PartNumberCatalogTest {
    
    public PartNumberCatalogTest() {
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
     * Test of addPartNumber method, of class PartNumberCatalog.
     */
    @Test
    public void testAddPartNumber() {
        System.out.println("addPartNumber");
        PartNumber partNumber = null;
        PartNumberCatalog instance = new PartNumberCatalog();
        boolean expResult = false;
        boolean result = instance.addPartNumber(partNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePartNumber method, of class PartNumberCatalog.
     */
    @Test
    public void testRemovePartNumber() {
        System.out.println("removePartNumber");
        PartNumber partNumber = null;
        PartNumberCatalog instance = new PartNumberCatalog();
        boolean expResult = false;
        boolean result = instance.removePartNumber(partNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPartNumberInSystem method, of class PartNumberCatalog.
     */
    @Test
    public void testIsPartNumberInSystem() {
        System.out.println("isPartNumberInSystem");
        PartNumber partNumber = null;
        PartNumberCatalog instance = new PartNumberCatalog();
        boolean expResult = false;
        boolean result = instance.isPartNumberInSystem(partNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateUniqueID method, of class PartNumberCatalog.
     */
    @Test
    public void testGenerateUniqueID() {
        System.out.println("generateUniqueID");
        VehiclePart part = null;
        PartNumberCatalog instance = new PartNumberCatalog();
        String expResult = "";
        String result = instance.generateUniqueID(part);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
