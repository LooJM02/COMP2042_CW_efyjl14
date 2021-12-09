package view;

import handler.GameBallHandler;
import handler.DebugPanelHandler;
import model.WallModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * DebugConsoleView Class handles the implementation of the Debug Console Window.
 */
public class DebugConsoleView extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";
    
    private JFrame owner;
    private DebugPanelHandler debugPanelHandler;
    private GameBoardView gameBoard;
    private WallModel wallHandler;

    /**
     * DebugConsoleView is parameterized constructor that initializes the Debug Console window and it's properties.
     * @param owner     reads the owner of the system the game runs on.
     * @param wall      passing in the Object/Reference variable of the WallController class. Aggregation relationship.
     * @param gameBoard passing in the Object/Reference variable of the GameBoardView class. Aggregation relationship.
     */
    public DebugConsoleView(JFrame owner, WallModel wall, GameBoardView gameBoard){

        this.wallHandler = wall;
        this.owner = owner;
        this.gameBoard = gameBoard;
        initialize();

        debugPanelHandler = new DebugPanelHandler(wall);
        this.add(debugPanelHandler,BorderLayout.CENTER);
        this.pack();
    }

    /**
     * initialize is a Private Method to initialize the Debug Console modal dialog box.
     * Handles what the title of the dialog box should be and what to do when user initiates a close of the dialog box.
     * Enables dialog box to gain focus and to listen and receive any window events.
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }


    /**
     * setLocation is a Private Method that sets the location of displaying the Debug Console dialog box.
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }


    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * windowClosing implements the method in WindowListener.
     * When user closes the window/dialog box, repaint the in-game screen.
     * @param windowEvent   to indicate if a key action has occurred or not.
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameBoard.repaint();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * windowActivated implements the method in WindowListener.
     * When the window is activated, calls the setLocation method to set the location of the Debug Console.
     * And change the speed and direction of the balls based on the user's option from the debug panel.
     * @param windowEvent   to indicate if a key action has occurred or not.
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        GameBallHandler b = wallHandler.ball;
        debugPanelHandler.setBallSpeed(b.getBallSpeedX(),b.getBallSpeedY());
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
