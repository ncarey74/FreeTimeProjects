/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IndividualParts;

import PartNumbers.PartNumber;
import PartNumbers.PartNumberCatalog;
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
        PartNumber partNumber = new PartNumber("111-111");
        PartNumberCatalog instance = new PartNumberCatalog();
        boolean expResult = true;
        boolean result = instance.addPartNumber(partNumber);
        assertEquals(expResult, result);
        System.out.println("# " + partNumber.getPartNumber());
        
        assert(instance.isPartNumberInSystem(partNumber));
        
        PartNumber p2 = new PartNumber("212-111");
        expResult = true;
        result = instance.addPartNumber(p2);
        assertEquals(expResult, result);
        System.out.println("# " + p2.getPartNumber());
        
        expResult = false;
        result = instance.addPartNumber(p2);
        assertEquals(expResult, result);
        System.out.println("# " + p2.getPartNumber());
        
        PartNumber p3 = new PartNumber("112-111");
        expResult = true;
        result = instance.addPartNumber(p3);
        assertEquals(expResult, result);
        System.out.println("# " + p3.getPartNumber());
    }

    /**
     * Test of isPartNumberInSystem method, of class PartNumberCatalog.
     */
    @Test
    public void testIsPartNumberInSystem() {
        System.out.println("isPartNumberInSystem");
        PartNumber partNumber = new PartNumber("111-111");
        PartNumberCatalog instance = new PartNumberCatalog();
        boolean expResult = false;
        boolean result = instance.isPartNumberInSystem(partNumber);
        assertEquals(expResult, result);
        System.out.println("# " + expResult + ", " + result);
        
        assert(instance.addPartNumber(partNumber));
        PartNumber p2 = new PartNumber("111-111");
        expResult = true;
        result = instance.isPartNumberInSystem(p2);
        assertEquals(expResult, result);
        System.out.println("# " + expResult + ", " + result);
        
        PartNumber p3 = new PartNumber("000-000");
        expResult = false;
        result = instance.isPartNumberInSystem(p3);
        assertEquals(expResult, result);
        System.out.println("# " + expResult + ", " + result);
    }

    /**
     * Test of removePartNumber method, of class PartNumberCatalog.
     */
    @Test
    public void testRemovePartNumber() {
        System.out.println("removePartNumber");
        PartNumber partNumber = new PartNumber("111-111");
        PartNumberCatalog instance = new PartNumberCatalog();
        boolean expResult = false;
        boolean result = instance.removePartNumber(partNumber);
        assertEquals(expResult, result);
        System.out.println("# " + expResult + ", " + result);
        
        assert(instance.addPartNumber(partNumber));
        expResult = true;
        result = instance.removePartNumber(partNumber);
        assertEquals(expResult, result);
        System.out.println("# " + expResult + ", " + result);
        
        PartNumber p1 = new PartNumber("aaabbbc");
        assertFalse(instance.addPartNumber(p1));
        expResult = false;
        result = instance.removePartNumber(p1);
        assertEquals(expResult, result);
        System.out.println("# " + expResult + ", " + result);
    }
}
