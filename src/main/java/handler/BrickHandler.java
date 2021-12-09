package handler;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;


/**
 *  BrickHandler is an Abstract Class that handles all the implementations regarding the Bricks in the game.
 */
abstract public class BrickHandler {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


    /**
     * Crack class is a Nested Class under the BrickController abstract class as it is a feature of the Brick class.
     * Responsible for all the implementations regarding the crack generated when a ball makes an impact with a brick.
     */
    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;


        /**
         * Crack is a Parameterized Constructor that creates the crack.
         * Sets a randomly generated path from the point of impact.
         * Sets the depth of the crack.
         * Sets the crack steps.
         * @param crackDepth
         * @param steps
         */
        public Crack(int crackDepth, int steps){

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }


        /**
         * drawCrack Method is responsible for drawing out the crack.
         * @return
         */
        public GeneralPath drawCrack(){

            return crack;
        }

        /**
         * resetCrack is a Method to resetCrack the crack.
         */
        public void resetCrack(){
            crack.reset();
        }

        /**
         * setBrickCrackLocation is responsible for setting the start and end location of the crack.
         * @param point         the point of impactMade.
         * @param direction     the direction of the crack.
         */
        public void setBrickCrackLocation(Point2D point, int direction){
            Rectangle bounds = BrickHandler.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeBrickCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeBrickCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeBrickCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeBrickCrack(impact,tmp);

                    break;

            }
        }

        /**
         * setBrickCrackLocation Method is responsible for making a randomly generated path from the start point to the end point.
         * @param start
         * @param end
         */
        protected void makeBrickCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        /**
         * randomInBounds is a Private Method that is responsible for returning a random number between the bound value and the negative bound value.
         * @param bound     the bound value.
         * @return          returns a random integer value between the bound value and the negative bound value.
         */
        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        private int jumps(int bound,double probability){

            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    protected Shape brickFace;
    private Color borderColor;
    private Color innerColor;
    private int fullStrength;
    private int strength;
    private boolean broken;


    /**
     * BrickController is a Parameterized Constructor that that handles the initial implementation of the brick.
     * Sets the brick name.
     * Sets the brick status as NOT BROKEN.
     * Creates the brick.
     * @param name      brick name
     * @param pos       brick position/location
     * @param size      size of the brick
     * @param border    brick border color
     * @param inner     brick inner color
     * @param strength  brick's strength
     */
    public BrickHandler(String name, Point pos, Dimension size, Color border, Color inner, int strength){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.borderColor = border;
        this.innerColor = inner;
        this.fullStrength = this.strength = strength;

    }

    /**
     * makeBrickFace is an Abstract Method which creates the ball.
     * This method will later be implemented by the Cement, Clay and Steel brick classes or any other extensions the programmer wishes to add
     * @param pos       the position/location of the brick.
     * @param size      size of the brick.
     * @return          returns the brick.
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * setImpact Method is responsible for letting know if an impact has occurred.
     * If brick is broken, will return false. Denoting no impact made.
     * Else, will run the impact method and after impact, return the if brick broken of not.
     * @param point     point of impactMade.
     * @param dir       direction of impactMade.
     * @return          returns a boolean value to state if brick is broken or not.
     */
    public boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impactMade();
        return  broken;
    }

    /**
     * getBrick is an Abstract Method that will be later implemented.
     * Returns the brick shape.
     * @return      returns the brick.
     */
    public abstract Shape getBrickFace();


    /**
     * getBorderColor Method is responsible for returning border color of brick.
     * Encapsulation of the border variable.
     * @return      returns border color of brick
     */
    public Color getBorderColor(){
        return  borderColor;
    }

    /**
     * getBorderColor Method is responsible for returning border color of brick.
     * Encapsulation of the border variable.
     * @return      returns border color of brick
     */
    public Color getInnerColor(){
        return innerColor;
    }


    /**
     * findImpactDirection Method is responsible for determining the direction of the impactMade from the ball on the brick.
     * @param b     passing in the Object/Reference variable of the GameBallHandler class. Aggregation relationship.
     * @return
     */
    public final int findImpactDirection(GameBallHandler b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * isBroken Method is responsible for returning if the brick is broken or not.
     * Encapsulation of the broken variable.
     * @return      returns the boolean value of broken variable.
     */
    public final boolean isBrickBroken(){
        return broken;
    }

    /**
     * repair Method is responsible for repairing the brick.
     * Sets the broken back to NOT broken.
     * Sets strength to full capacity.
     */
    public void repairBrick() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * impact Method is responsible to set the broken variable based on the brick's status.
     * Also responsible for deducting the strength of a brick when an impact has occurred.
     */
    public void impactMade(){
        strength--;
        broken = (strength == 0);
    }



}





