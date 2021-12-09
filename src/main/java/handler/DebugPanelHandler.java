package handler;

import model.WallModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * DebugPanelHandler Class handles the implementation of the Debug Panel Window.
 */
public class DebugPanelHandler extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;


    private JButton skipLevel;
    private JButton resetBalls;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    private WallModel wall;

    /**
     * DebugPanelController is a Parameterized Constructor that initializes the Debug Console window and it's properties.
     * Calls the initialize() method.
     * Creates the 'Skip Level' and 'Reset Balls' buttons.
     * Creates a slider for the ball x-axis speed.
     * Creates a slider for the ball y-axis speed.
     * @param wall      passing in the Object/Reference variable of the WallController class. Aggregation relationship.
     */
    public DebugPanelHandler(WallModel wall){

        this.wall = wall;

        initialize();

        //create buttons for the SKIP LEVEL and RESET BALLS
        skipLevel = createButton("Skip Level",e -> wall.nextGameLevel());
        resetBalls = createButton("Reset Balls",e -> wall.resetBallCounter());

        //creates a slider for the ball's speed in the x and y-axis directions
        ballXSpeed = createSlider(-4,4,e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = createSlider(-4,4,e -> wall.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * initialize is a Private Method to initialize the Debug Panel.
     * Sets the layout and background color of the debug panel.
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * makeButton is a Private Method which creates a button for the Debug Panel.
     * @param title     title/name of the button
     * @param e         notifies if an action has been performed
     * @return          returns a button.
     */
    private JButton createButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * makeSlider is a Private Method which creates a slider in the Debug Panel.
     * @param min       the min value.
     * @param max       the max value.
     * @param e         notifies if any change of event.
     * @return          returns a slider.
     */
    private JSlider createSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * setValues Method sets the values of the ball's speed in x-axis and y-axis. Self-calling method.
     * @param x     speed of ball in x-axis.
     * @param y     speed of ball in y-axis.
     */
    public void setBallSpeed(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
