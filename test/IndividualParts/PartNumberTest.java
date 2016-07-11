/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IndividualParts;

import PartNumbers.PartNumber;
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
public class PartNumberTest {
    
    public PartNumberTest() {
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

    @Test
    public void testSomeMethod() {
        PartNumber p1 = new PartNumber("a00-000");
        assert("000-000".compareTo(p1.getPartNumber()) == 0);
        
        PartNumber p2 = new PartNumber("0-00000");
        assert("000-000".compareTo(p2.getPartNumber()) == 0);
        
        PartNumber p3 = new PartNumber("0-0");
        assert("000-000".compareTo(p3.getPartNumber()) == 0);
        
        PartNumber p4 = new PartNumber("0000-0000");
        assert("000-000".compareTo(p4.getPartNumber()) == 0);
        
        PartNumber p5 = new PartNumber("00000000");
        assert("000-000".compareTo(p5.getPartNumber()) == 0);
        
        PartNumber p6 = new PartNumber("001-001");
        assert("001-001".compareTo(p6.getPartNumber()) == 0);
    }

    /**
     * Test of getPartNumber method, of class PartNumber.
     */
    @Test
    public void testGetPartNumber() {
        System.out.println("getPartNumber");
        PartNumber instance = new PartNumber("001-001");
        String expResult = "001-001";
        String result = instance.getPartNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidPartNumber method, of class PartNumber.
     */
    @Test
    public void testIsValidPartNumber() {
        System.out.println("isValidPartNumber");
        PartNumber instance = new PartNumber("001-001");
        boolean expResult = true;
        boolean result = instance.isValidPartNumber();
        assertEquals(expResult, result);
        
        instance = new PartNumber("000-000");
        expResult = false;
        result = instance.isValidPartNumber();
        assertEquals(expResult, result);
        
        instance = new PartNumber("00-00");
        expResult = false;
        result = instance.isValidPartNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class PartNumber.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new PartNumber("001-001");
        PartNumber instance = new PartNumber("001-001");
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        
        obj = new PartNumber("000-001");
        instance = new PartNumber("001-001");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);
    }    
}
