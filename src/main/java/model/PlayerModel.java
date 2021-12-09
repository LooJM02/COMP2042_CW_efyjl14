package model;


import handler.GameBallHandler;
import java.awt.*;


/**
 * PlayerModel class is responsible for all the implementations regarding the player.
 */
public class PlayerModel {


    public static final Color BORDER_COLOR = Color.WHITE.darker().brighter();
    public static final Color INNER_COLOR = Color.WHITE;

    private static final int DEF_MOVE_AMOUNT = 5;
    private static final int DEF_SPRINT_AMOUNT = 10; //allows player to move faster
    public static int score;

    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    /**
     * PlayerModel is a Parameterized Constructor that handles the initial implementation of the player.
     * Handles the player movement amount when game begins. Sets it to zero initially.
     * Creates the player.
     * Sets the min and max variables to set the dimensions of the player.
     * @param ballPoint     the location of the ball.
     * @param width         the width of the player.
     * @param height        the height of the player.
     * @param container     the rectangular container/frame of the game screen.
     */
    public PlayerModel(Point ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * makeRectangle is a Private Method that creates the player of rectangular shape.
     * @param width     the width of the player.
     * @param height    the height of the player.
     * @return          returns a Rectangle player based on the dimensions specified.
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * impact is a Method that implements the impact of the ball on the platform.
     * @param b     passing in the Object/Reference variable of the GameBallHandler class. Aggregation relationship.
     * @return      returns a boolean value denoting if an impact has occurred or not.
     */
    public boolean impact(GameBallHandler b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * move is a Method that implements the movement of the ball and the platform.
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * moveLeft is a Method that handles the movement of the player to the left.
     * Sets the move amount and direction when move left.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void sprintLeft(){moveAmount = -DEF_SPRINT_AMOUNT;}
    /**
     * movRight is a Method that handles the movement of the player to the right.
     * Sets the move amount and direction when move right.
     */
    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void sprintRight(){moveAmount = DEF_SPRINT_AMOUNT;}
    /**
     * stop if a Method sets the movement amount to zero to stop the player movement.
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * getPlayerFace is a Method that returns the playerFace.
     * @return      the shape of the player
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * moveTo is a Method that sets the location of the ball and player.
     * @param p
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
}
