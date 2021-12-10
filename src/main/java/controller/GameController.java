package controller;

import view.GameBoardView;
import view.HomeMenuView;
import view.InfoMenuView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


/**
 * GameController is the class responsible for the implementation of the Game Frame window.
 */
public class GameController extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroyer";//game title

    private GameBoardView gameBoardView;
    private HomeMenuView homeMenuView;
    private InfoMenuView infoMenuView;
    private boolean gameRunning;

    /**
     * GameFrameModel is a Default Constructor that adds the HomeMenuView page to the Game frame.
     */
    public GameController(){
        super();

        gameRunning = false;

        this.setLayout(new BorderLayout());

        gameBoardView = new GameBoardView(this);

        infoMenuView = new InfoMenuView(this,new Dimension(600,450));


        homeMenuView = new HomeMenuView(this,new Dimension(600,450));//from (450,300) . make size of HomeMenuView same as size of GameBoardView

        this.add(homeMenuView,BorderLayout.CENTER);

        this.setUndecorated(true);
    }

    /**
     * initialize is a Method to initialize the in-game frame.
     * Sets the title for the game frame window.
     * Handles the window's closing options.
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * enableGameBoard is a Method that makes the transition from the HomeMenu to the GameBoard screen.
     * It removes the HomeMenu page and adds the GameBoard page.
     * It also sets the frame's features and properties such as Frame decorations and Window Focus Listeners.
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenuView);
        this.add(gameBoardView,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * autoLocate is a Method that sets the location of the Game Frame depending on the device's screen size.
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    public void enableInfoMenu(){
        this.dispose();
        this.remove(homeMenuView);
        this.add(infoMenuView,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
    }

    public void enableHomeMenu(){
        this.dispose();
        this.remove(infoMenuView);
        this.remove(gameBoardView);
        this.add(homeMenuView,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
    }




    /**
     * windowGainedFocus implements the method in WindowListener.
     * Handles implementation for when Game Board is back in focus.
     * @param windowEvent       to indicate if a key action has occurred or not.
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoardView,
            so when it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoardView has been displayed
            at least once
         */
        gameRunning = true;
    }

    /**
     * windowLostFocus implements the method in WindowListener.
     * Handles implementation when Game Board has lost focus.
     * Calls the onLostFocus method from the GameBoardView class.
     * @param windowEvent   to indicate if a key action has occurred or not.
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gameRunning)
            gameBoardView.onLostFocus();
    }
}

