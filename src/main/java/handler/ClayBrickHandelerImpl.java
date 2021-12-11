package handler;


import java.awt.*;


/**
 * ClayBrickModel class is SubClass/Child Class of the BrickController class.
 * It extends the BrickController class. Inheritance
 * Responsible for all the implementations regarding the Clay Brick.
 */
public class ClayBrickHandelerImpl extends BrickHandler {
 
    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(253,218,13);//clay brick color as cadmium yellow
    private static final Color DEF_BORDER = new Color(0,150,255);//border color as baby blue colour
    private static final int CLAY_STRENGTH = 1;


    /**
     * ClayBrickModel is a Parameterized Constructor that runs the Parent Class's, BrickHandler class constructor.
     * @param point     brick position/location.
     * @param size      size of the brick.
     */
    public ClayBrickHandelerImpl(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * makeBrickFace overrides the makeBrickFace in the parent class.
     * This method creates the clay brick.
     * @param pos   the position/location of the brick.
     * @param size  size of the brick.
     * @return      returns the brick.
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * getBrickFace implements the Abstract method from the parent class.
     * @return      returns the Clay Brick.
     */
    @Override
    public Shape getBrickFace() {
        return super.brickFace;
    }


}
