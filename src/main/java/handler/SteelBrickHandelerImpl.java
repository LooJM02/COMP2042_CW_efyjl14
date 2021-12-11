package handler;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


/**
 * SteelBrickModel class is SubClass/Child Class of the BrickController class.
 * It extends the BrickController class. Inheritance
 * Responsible for all the implementations regarding the Steel Brick.
 */
public class SteelBrickHandelerImpl extends BrickHandler {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = new Color(192, 64, 0);
    private static final Color DEF_BORDER = new Color(0,150,255);
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace; 

    /**
     * SteelBrickModel is a Parameterized Constructor that runs the Parent Class's, BrickHandler class constructor.
     * @param point     brick position/location.
     * @param size      size of the brick.
     */
    public SteelBrickHandelerImpl(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }


    /**
     * makeBrickFace overrides the makeBrickFace in the parent class.
     * This method creates the clay brick.
     * @param pos  the position/location of the brick.
     * @param size size of the brick.
     * @return      returns the brick.
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * getBrickFace implements the Abstract method from the parent class.
     * @return      returns the Steel Brick.
     */
    @Override
    public Shape getBrickFace() {
        return brickFace;
    }

    /**
     * setImpact is an Overridden Method from the parent class.
     * Responsible for knowing if an impact has occurred or not.
     * Calls the BrickController's impact() and isBroken() method.
     * @param point point of impactMade.
     * @param dir   direction of impactMade.
     * @return      returns a boolean value stating if an impactMade has occurred or not.
     */
    @Override
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBrickBroken())
            return false;
        impactMade();
        return  super.isBrickBroken();
    }

    /**
     * impact is an Overrides the Method in the parent class, BrickController class.
     * Responsible for deducting the strength of a brick when an impact has occurred.
     */
    @Override
    public void impactMade(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impactMade();
        }
    }

}
