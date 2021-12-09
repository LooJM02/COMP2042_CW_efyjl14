package handler;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClayBrickHandelerImplTest {
    
    public ClayBrickHandelerImplTest() {
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
     * Test of makeBrickFace method, of class ClayBrickHandelerImpl.
     */
    @Test
    public void testMakeBrickFace() {
        System.out.println("makeBrickFace");
        Point pos = null;
        Dimension size = null;
        ClayBrickHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.makeBrickFace(pos, size);
        assertEquals(expResult, result);

    }

    /**
     * Test of getBrickFace method, of class ClayBrickHandelerImpl.
     */
    @Test
    public void testGetBrickFace() {
        System.out.println("getBrickFace");
        ClayBrickHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.getBrickFace();
        assertEquals(expResult, result);

    }
    
}
