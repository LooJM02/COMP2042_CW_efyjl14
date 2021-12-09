package handler;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;


/**
 * GameBallHandler is an Abstract Class that handles all the implementations regarding the Ball Movement.
 */
abstract public class GameBallHandler {

    private Shape ballFace;

    private Point2D centerPosition;

    public Point2D up;
    public Point2D down;
    public Point2D left;
    public Point2D right;

    private Color borderColor;
    private Color innerColor;

    private int ballSpeedX;
    private int ballSpeedY;

    /**
     * BallController is a Parameterized Constructor that handles the initial implementation of the ball.
     * Handles the ball movement.
     * Creates the ball.
     * Sets the initial ball speeds in the x and y direction to 0 at the beginning of the game.
     * @param center        the initial location of the ball.
     * @param radiusA       the width of the ball.
     * @param radiusB       the height of the ball.
     * @param inner         the inner color of the ball.
     * @param border        the border color of the ball.
     */
    public GameBallHandler(Point2D center, int radiusA, int radiusB, Color inner, Color border){
        this.centerPosition = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.borderColor = border;
        this.innerColor  = inner;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    /**
     * makeBall is an Abstract Method which creates the ball.
     * This method will later be implemented by the RubberBallModel or any other extensions the programmer wishes to add.
     * @param center        the initial location of the ball.
     * @param radiusA       the width of the ball.
     * @param radiusB       the height of the ball.
     * @return              returns the ball.
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * move is a Method that implements the width and height of the ball when in motion.
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        centerPosition.setLocation((centerPosition.getX() + ballSpeedX),(centerPosition.getY() + ballSpeedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((centerPosition.getX() -(w / 2)),(centerPosition.getY() - (h / 2)),w,h);
        setScreenPoints(w,h);


        ballFace = tmp;
    }

    /**
     * setSpeed is a Setter Method that sets the ball speed in both the x-axis and y-axis direction.
     * @param x     an integer variable that sets ballSpeedX value.
     * @param y     an integer variable that sets ballSpeedY value.
     */
    public void setSpeed(int x,int y){
        ballSpeedX = x;
        ballSpeedY = y;
    }

    /**
     * setXSpeed is a Setter Method that sets the ball speed in the x-axis direction.
     * Encapsulation of the speedX variable.
     * @param s     an integer variable that sets ballSpeedX value.
     */
    public void setXSpeed(int s){
        ballSpeedX = s;
    }

    /**
     * setYSpeed is a Setter Method that sets the ball speed in the y-axis direction.
     * Encapsulation of the speedY variable.
     * @param s     an integer variable that sets ballSpeedY value.
     */
    public void setYSpeed(int s){
        ballSpeedY = s;
    }

    /**
     * reverseX is a Method that sets the ball speed in the x-axis direction in the reverse direction.
     * ball moves in negative x-axis direction.
     * Used after ball makes impact and returns back.
     */
    public void reverseX(){
        ballSpeedX *= -1;
    }

    /**
     * reverseY is a Method that sets the ball speed in the y-axis direction in the reverse direction.
     * ball moves in negative y-axis direction.
     * Used after ball makes impact and returns back.
     */
    public void reverseY(){
        ballSpeedY *= -1;
    }

    /**
     * getBorderColor is a Getter Method that returns the ball's border color.
     * Encapsulation of the border color variable.
     * @return      returns the border color of the ball.
     */
    public Color getBorderColor(){
        return borderColor;
    }

    /**
     * getInnerColor is a Getter Method that returns the ball's border color.
     * Encapsulation of the inner color variable.
     * @return      returns the inner color of the ball.
     */
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * getPosition is a Getter Method that returns the ball's centerPosition location.
     * @return      returns the centerPosition position for the initial location of the ball.
     */
    public Point2D getPosition(){
        return centerPosition;
    }

    /**
     * getBallFace Method that returns the ballFace.
     * Encapsulation of the ballFace variable.
     * @return      the shape and features of the ball.
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * moveTo Method implements the ball shape and location when the ball resets.
     * @param p
     */
    public void moveTo(Point p){
        centerPosition.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((centerPosition.getX() -(w / 2)),(centerPosition.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * setPoints is a Private Method that sets the Screen points
     * @param width     the width of the screen.
     * @param height    the height of the screen
     */
    private void setScreenPoints(double width,double height){
        up.setLocation(centerPosition.getX(),centerPosition.getY()-(height / 2));
        down.setLocation(centerPosition.getX(),centerPosition.getY()+(height / 2));

        left.setLocation(centerPosition.getX()-(width / 2),centerPosition.getY());
        right.setLocation(centerPosition.getX()+(width / 2),centerPosition.getY());
    }

    /**
     * getSpeedX is a Getter Method that returns the the ball speed in the x-axis direction.
     * Encapsulation of the speedX variable.
     * @return      returns the ballSpeedX private variable value.
     */
    public int getBallSpeedX(){
        return ballSpeedX;
    }

    /**
     * getSpeedY is a Getter Method that returns the the ball speed in the y-axis direction.
     * Encapsulation of the speedY variable.
     * @return      returns the ballSpeedY private variable value.
     */
    public int getBallSpeedY(){
        return ballSpeedY;
    }


}
