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


public class CementBrickHandelerImplTest {
    
    public CementBrickHandelerImplTest() {
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
     * Test of makeBrickFace method, of class CementBrickHandelerImpl.
     */
    @Test
    public void testMakeBrickFace() {
        System.out.println("makeBrickFace");
        Point pos = null;
        Dimension size = null;
        CementBrickHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.makeBrickFace(pos, size);
        assertEquals(expResult, result);
    }

    /**
     * Test of setImpact method, of class CementBrickHandelerImpl.
     */
    @Test
    public void testSetImpact() {
        System.out.println("setImpact");
        Point2D point = null;
        int dir = 0;
        CementBrickHandelerImpl instance = null;
        boolean expResult = false;
        boolean result = instance.setImpact(point, dir);
        assertEquals(expResult, result);

    }

    /**
     * Test of getBrickFace method, of class CementBrickHandelerImpl.
     */
    @Test
    public void testGetBrickFace() {
        System.out.println("getBrickFace");
        CementBrickHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.getBrickFace();
        assertEquals(expResult, result);

    }

    /**
     * Test of repairBrick method, of class CementBrickHandelerImpl.
     */
    @Test
    public void testRepairBrick() {
        System.out.println("repairBrick");
        CementBrickHandelerImpl instance = null;
        instance.repairBrick();

    }
    
}
