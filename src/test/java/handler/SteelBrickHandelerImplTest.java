package handler;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SteelBrickHandelerImplTest {
    
    public SteelBrickHandelerImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of makeBrickFace method, of class SteelBrickHandelerImpl.
     */
    @Test
    public void testMakeBrickFace() {
        System.out.println("makeBrickFace");
        Point pos = null;
        Dimension size = null;
        SteelBrickHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.makeBrickFace(pos, size);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBrickFace method, of class SteelBrickHandelerImpl.
     */
    @Test
    public void testGetBrickFace() {
        System.out.println("getBrickFace");
        SteelBrickHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.getBrickFace();
        assertEquals(expResult, result);

    }

    /**
     * Test of setImpact method, of class SteelBrickHandelerImpl.
     */
    @Test
    public void testSetImpact() {
        System.out.println("setImpact");
        Point2D point = null;
        int dir = 0;
        SteelBrickHandelerImpl instance = null;
        boolean expResult = false;
        boolean result = instance.setImpact(point, dir);
        assertEquals(expResult, result);
    }

    /**
     * Test of impactMade method, of class SteelBrickHandelerImpl.
     */
    @Test
    public void testImpactMade() {
        System.out.println("impactMade");
        SteelBrickHandelerImpl instance = null;
        instance.impactMade();
    }
    
}
