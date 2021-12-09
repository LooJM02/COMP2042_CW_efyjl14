package model;


import handler.BrickHandler;
import handler.CementBrickHandelerImpl;
import handler.ClayBrickHandelerImpl;
import handler.GameBallHandler;
import handler.RubberBallHandelerImpl;
import handler.SteelBrickHandelerImpl;
import model.PlayerModel;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * WallModel class is responsible for all the implementations on the wall,ball and the impacts.
 */
public class WallModel {

    private static final int LEVELS_COUNT = 6;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Random rnd;
    private Rectangle area;

    public BrickHandler[] bricks;
    public GameBallHandler ball;
    public PlayerModel player;

    private BrickHandler[][] gameLevels;
    private int gameLevel;

    private Point startingPoint;
    private int brickCounter;
    private int ballCounter;
    private boolean idBallLost;

    /**
     * WallModel is a Parameterized Constructor that hat handles the initial implementation of the wall.
     * @param drawArea
     * @param brickCount
     * @param lineCount
     * @param brickDimensionRatio
     * @param ballPos       the position/location of the ball.
     */
    public WallModel(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startingPoint = new Point(ballPos);

        gameLevels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        gameLevel = 0;

        ballCounter = 3;
        idBallLost = false;

        rnd = new Random();

        displayBall(ballPos);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        player = new PlayerModel((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
    }

    /**
     * makeSingleTypeLevel is a Private Method implements a wall with only 1 type of brick.
     * @param drawArea      a rectangular area for the wall.
     * @param brickCnt      the number of bricks.
     * @param lineCnt       the number of rows of bricks on the wall.
     * @param brickSizeRatio    the brick dimension.
     * @param type          the type of brick.
     * @return          returns a single brick type wall.
     */
    private BrickHandler[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCounter is not divisible by line count,brickCounter is adjusted to the biggest
          multiple of lineCount smaller then brickCounter
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        BrickHandler[] tmp  = new BrickHandler[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = createBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrickHandelerImpl(p,brickSize);
        }
        return tmp;

    }

    /**
     * makeChessBoard level is a Private Method implements a wall with multiple types of brick.
     * Creates a Chessboard like wall.
     * @param drawArea      a rectangular area for the wall.
     * @param brickCnt      the number of bricks.
     * @param lineCnt       the number of rows of bricks on the wall.
     * @param brickSizeRatio    the brick dimension.
     * @param typeA     the type of brick 1.
     * @param typeB     the type of brick 2.
     * @return          returns a Chessboard wall.
     */
    private BrickHandler[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCounter is not divisible by line count,brickCounter is adjusted to the biggest
          multiple of lineCount smaller then brickCounter
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        BrickHandler[] tmp  = new BrickHandler[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  createBrick(p,brickSize,typeA) : createBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = createBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * displayBall is a Private Method that calls the RubberBall Constructor to make the rubber ball.
     * Composition relationship
     * @param ballPos       the initial location of the ball.
     */
    private void displayBall(Point2D ballPos){
        ball = new RubberBallHandelerImpl(ballPos);
    }

    /**
     * makeLevels is a Private Method that creates the wall based on the levels.
     * @param drawArea      a rectangular area for the wall.
     * @param brickCount    the number of bricks.
     * @param lineCount     the number of rows of bricks on the wall.
     * @param brickDimensionRatio   the brick dimension.
     * @return      returns a wall. Either Single type wall or Chessboard wall.
     */
    private BrickHandler[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        BrickHandler[][] tmp = new BrickHandler[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,STEEL);
        tmp[5] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL);
        return tmp;
    }

    /**
     * movePlayerAndBall Method calls the movePlayerAndBall methods in the Player and GameBallHandler classes.
     */
    public void movePlayerAndBall(){
        player.move();
        ball.move();
    }

    /**
     * findImpacts Method is responsible for implementing all ball and brick properties when impact is made.
     * Implements when ball makes impact with the player.
     * Implements when ball makes impact with the wall. Calls the impactWall() method.
     * Implements when ball makes impact with the game frame/border.
     */
    public void getImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(ballImpactWithWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCounter--;
        }
        else if(ballImpactWithBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCounter--;
            idBallLost = true;
        }
    }

    /**
     * impactWall is a Private Method that is responsible for when the ball makes impactMade with the wall.
     * @return      returns a boolean value to denote if ball made impactMade with wall or not.
     */
    private boolean ballImpactWithWall(){
        for(BrickHandler b : bricks){
            switch(b.findImpactDirection(ball)) {
                //Vertical Impact
                case BrickHandler.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, BrickHandler.Crack.UP);
                case BrickHandler.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up, BrickHandler.Crack.DOWN);

                //Horizontal Impact
                case BrickHandler.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right, BrickHandler.Crack.RIGHT);
                case BrickHandler.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left, BrickHandler.Crack.LEFT);
            }
        }
        return false;
    }

    /**
     * impactBorder is a Private Method that implements when the ball makes impactMade with game border.
     * @return      returns a boolean value to denote if ball made impactMade with the border or not.
     */
    private boolean ballImpactWithBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * getBrickCounter is a Getter Method.
     * @return  returns the number of bricks.
     */
    public int getBrickCounter(){
        return brickCounter;
    }

    /**
     * getBallCounter is a Getter Method.
     * @return  returns the number of balls.
     */
    public int getBallCounter(){
        return ballCounter;
    }

    /**
     * isBallLost is a Getter Method.
     * @return  returns a boolean value of whether the ball is lost or not.
     */
    public boolean isBallLost(){
        return idBallLost;
    }

    /**
     * resetBall Method resets the ball back to the starting position.
     */
    public void resetBall(){
        player.moveTo(startingPoint);
        ball.moveTo(startingPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        idBallLost = false;
    }

    /**
     * wallReset Method resets/repairs the wall.
     * Sets number of ball back to 3 (full amount).
     */
    public void resetWall(){
        for(BrickHandler b : bricks)
            b.repairBrick();
        brickCounter = bricks.length;
        ballCounter = 3;
    }

    /**
     * ballEnd Method checks if the number of balls in the game has reached 0.
     * @return  returns a boolean value based on the number of balls present.
          True if ballCounter is 0.
          False if ballCounter is greater than 0.
     */
    public boolean ballEnd(){
        return ballCounter == 0;
    }

    /**
     * isCompleted Method checks if the number of bricks on the wall is 0.
     * @return  returns a boolean value based on the number of bricks present.
          True if brickCounter is 0.
          False if brickCounter is greater than 0.
     */
    public boolean isCompleted(){
        return brickCounter == 0;
    }

    /**
     * nextGameLevel Method sets the next level.
     */
    public void nextGameLevel(){
        bricks = gameLevels[gameLevel++];
        this.brickCounter = bricks.length;
    }

    /**
     * hasGameLevel Method checks if there is a next level.
     * @return  returns a boolean value.
     */
    public boolean hasGameLevel(){
        return gameLevel < gameLevels.length;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCounter(){
        ballCounter = 3;
    }

    /**
     * createBrick is a Private Method that creates the different types of bricks.
     * @param point     brick position/location.
     * @param size      size of the brick.
     * @param type      the type of brick, CLAY, CEMENT or STEEL.
     * @return          returns the brick.
     */
    private BrickHandler createBrick(Point point, Dimension size, int type){
        BrickHandler out;
        switch(type){
            case CLAY:
                out = new ClayBrickHandelerImpl(point,size);
                break;
            case STEEL:
                out = new SteelBrickHandelerImpl(point,size);
                break;
            case CEMENT:
                out = new CementBrickHandelerImpl(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

}
