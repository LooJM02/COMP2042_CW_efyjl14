package handler;

import java.awt.Shape;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RubberBallHandelerImplTest {
    
    public RubberBallHandelerImplTest() {
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
     * Test of makeBall method, of class RubberBallHandelerImpl.
     */
    @Test
    public void testMakeBall() {
        System.out.println("makeBall");
        Point2D center = null;
        int radiusA = 0;
        int radiusB = 0;
        RubberBallHandelerImpl instance = null;
        Shape expResult = null;
        Shape result = instance.makeBall(center, radiusA, radiusB);
        assertEquals(expResult, result);
    }
    
}
