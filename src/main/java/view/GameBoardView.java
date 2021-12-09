package view;

import handler.GameBallHandler;
import handler.BrickHandler;
import model.PlayerModel;
import model.WallModel;
import model.TimerModel;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;


/**
 * GameBoardView class displays the in-game screen and handles the in-game implementation.
 */
public class GameBoardView extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String HOME = "Home Menu";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(255,255,0); // yellow colour


    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = new Color(0,150,255);// baby blue color

    private Timer gameTimer;


    private WallModel wall;

    private String message;

    private String timeMessage;

    private TimerModel timer;

    private HomeMenuView homeMenu;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private Rectangle homeButtonRect;
    private int strLen;

    private DebugConsoleView debugConsole;
    private GameController owner;


    /**
     * GameBoardView is a parameterized constructor that displays the In-Game screen. Handles the in-game implementation.
     * Deals with the Wall implementation, the Debug Console implementation.
     * Sets the Game Timer.
     * @param owner     reads the owner of the system the game runs on.
     */
    public GameBoardView(GameController owner){
        super();

        strLen = 0;
        showPauseMenu = false;
        this.owner=owner;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);


        this.initialize();
        message = "";
        timeMessage = "";

        wall = new WallModel(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));

        timer = new TimerModel();
        debugConsole = new DebugConsoleView(owner,wall,this);
        //initialize the first level
        wall.nextGameLevel();

        gameTimer = new Timer(10,e ->{
            wall.movePlayerAndBall();
            wall.getImpacts();
            timer.setGameRunning(true);
            message = String.format("Bricks: %d Balls %d",wall.getBrickCounter(),wall.getBallCounter());
            timeMessage = String.format("Time: %02d minute(s) %02d second(s)", timer.getMinutes(), timer.getSeconds());

            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.resetWall();
                    message = "Game over";
                    timer.resetGameTimer();

                }
                timer.setGameRunning(true);
                wall.resetBall();
                gameTimer.stop();
            }
            else if(wall.isCompleted()){
                if(wall.hasGameLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wall.resetBall();
                    wall.resetWall();
                    wall.nextGameLevel();

                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                    timer.resetGameTimer();
                }
                PlayerModel.score+=35;
                ScoreBoardView.HIGHSCORE1=System.getProperty("user.name")+" : "+PlayerModel.score;
            }

            repaint();
        });
       gameTimer.start();
    }


    /**
     * initialize is a Private Method to initialize the in-game screen.
     * Sets the in-game screen size, enables game screen to gain focus.
     * Listens and notifies when any keys are pressed, and if there are any mouse clicks or motions.
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    /**
     * paint is an Overridden Method from the JComponent class.
     * The method paints/draws the game board view.
     * Draws the message on screen.
     * Draws the ball, player and wall on the in-game screen.
     * Draws the Pause screen as well.
     * @param g
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.WHITE);//white text colour
        g2d.drawString(message,250,225);
        g2d.drawString(timeMessage,215,245);


        drawBall(wall.ball,g2d);

        for(BrickHandler b : wall.bricks)
            if(!b.isBrickBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.player,g2d);

        if(showPauseMenu)
            writeMenu(g2d);

        Toolkit.getDefaultToolkit().sync();

    }

    /**
     * clear is a Private Method which is responsible for clearing the in-game screen.
     * @param g2d
     */
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    /**
     * drawBrick is a private Method that draws the brick to the in-game screen.
     * Sets and Draws the brick color, shape and type.
     * @param brick     passing in the Object/Reference variable of the BrickHandler class. Aggregation relationship.
     * @param g2d
     */
    private void drawBrick(BrickHandler brick, Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrickFace());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrickFace());
        g2d.setColor(tmp);
    }

    /**
     * drawBall is a private Method that draws the ball components to the in-game screen.
     * Draws the ball features such as color and shape
     * @param ball      passing in the Object/Reference variable of the GameBallHandler class. Aggregation relationship.
     * @param g2d
     */
    private void drawBall(GameBallHandler ball, Graphics2D g2d){
        Color tmp = g2d.getColor();
        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * drawPlayer is a private Method that draws the player components to the in-game screen.
     * Draws the player shape, color.
     * @param p     passing in the Object/Reference variable of the PlayerModel class. Aggregation relationship.
     * @param g2d
     */
    private void drawPlayer(PlayerModel p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(PlayerModel.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(PlayerModel.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * drawMenu calls the obscureGameBoard and drawPauseMenu methods to completely draw the Pause Menu screen.
     * Draws the Pause Menu screen and background.
     * Draws the text and buttons in the Pause Menu.
     * @param g2d
     */
    private void writeMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        displayPauseMenu(g2d);
    }

    /**
     * obscureGameBoard is a private Method that draws the Pause Menu container screen and sets its properties.
     * Draws on top of the existing In-Game Screen.
     * Handles the blending and transparency of the screen.
     * @param g2d
     */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * drawPauseMenu is a private Method that draws and renders the fonts on the Pause Menu Screen.
     * Draws the CONTINUE, RESTART and EXIT buttons.
     * @param g2d
     */
    private void displayPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 3.5/2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 2.85/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }


        g2d.drawString(EXIT,x,y);

        y *= 2.59/2;

        if(homeButtonRect == null){
            homeButtonRect = (Rectangle) continueButtonRect.clone();
            homeButtonRect.setLocation(x,y-homeButtonRect.height);
        }


        g2d.drawString(HOME,x,y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }



    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * keyPressed implements the method in KeyListener.
     * Listens and notifies if a key is pressed.
     * Implements the next course of actions if specific keys are pressed, such as:
     * A: move player left, D: move player right, ESC: show pause menu, SPACE: pause game, ALT-SHIFT-F1: display DebugConsole.
     * @param keyEvent      to indicate if a key action has occurred or not.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                if (keyEvent.isShiftDown())//if player presses SHIFT+A key, player moves faster to the right
                {
                    wall.player.sprintLeft();
                }
                else
                {
                    wall.player.moveLeft();
                }
                break;
            case KeyEvent.VK_D:
                if (keyEvent.isShiftDown())//if player presses SHIFT+D key, player moves faster to left
                {
                    wall.player.sprintRight();
                }
                else
                {
                    wall.player.movRight();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                timer.setGameRunning(false);
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning()) {
                        gameTimer.stop();
                        timer.setGameRunning(false);
                    }
                    else
                        gameTimer.start();
                        timer.setGameRunning(true);
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.player.stop();
        }
    }

    /**
     * keyReleased implements the method in KeyListener.
     * Stops the player's movement once key released.
     * @param keyEvent      to indicate if a key action has occurred or not.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.player.stop();
    }

    /**
     * mouseClicked implements the method in MouseListener.
     * Implements the next course of actions if the CONTINUE, RESTART and EXIT buttons are clicked on.
     * @param mouseEvent    to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            timer.resetGameTimer();
            wall.resetBall();
            wall.resetWall();
            showPauseMenu = false;
            repaint();
            timer.setGameRunning(true);
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }
        else if (homeButtonRect.contains(p))
        {
             showPauseMenu=false;
              repaint();
            wall.resetBall();
            timer.resetGameTimer();
            timer.setGameRunning(true);
            //this.setVisible(false);
            owner.enableHomeMenu();
          
        }


    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * mouseMoved implements the method in MouseListener.
     * Implements what the cursor should look like when the cursor hovers over the CONTINUE, RESTART and EXIT buttons.
     * Implements what the cursor should look like otherwise.
     * @param mouseEvent       to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p) || homeButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * onLostFocus method implements what should occur when focus is lost.
     * Stops the game timer.
     * Prints out the message mentioning the focus is lost.
     * Redraw the screen.
     */
    public void onLostFocus(){
        gameTimer.stop();
        timer.setGameRunning(false);
        message = "Focus Lost";
        repaint();
    }

}
