package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


/**
 * HomeMenuView class is the class for the implementation of the starting page when the game begins.
 * Displays the Game Title and the Start and Exit buttons.
 */
public class HomeMenuView extends JComponent implements MouseListener, MouseMotionListener {

    // home menu text
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "BRICK DESTROY";
    private static final String CREDITS = "Version 0.1";

    // home menu button
    private static final String START_TEXT = "Start";
    private static final String MENU_TEXT = "Exit";
    private static final String INFO_TEXT = "Info";


    private static final Color BG_COLOR = Color.YELLOW.brighter();
    private static final Color BORDER_COLOR = new Color(200,8,21);
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.darker();
    private static final Color CLICKED_TEXT = Color.BLACK;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    Toolkit t=Toolkit.getDefaultToolkit();
    private Image HomeImage = t.getImage("src/HomePic.jpg");//background picture


    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle menuButton;
    private Rectangle infoButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameController owner;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean infoClicked;


    /**
     * HomeMenu is a parameterized constructor that sets the HomeMenu elements.
     * Sets the location of the menu.
     * Sets the dimensions of the Start and Exit buttons
     * Sets the font style and size of all the text in the HomeMenu.
     * Sets the HomeMenu's border and dashes.
     * @param owner     passing in the Object/reference variable of the GameController class. Aggregation relationship.
     * @param area
     */
    public HomeMenuView(GameController owner, Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;
        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,15);
        buttonFont = new Font("Monospaced",Font.BOLD,startButton.height-2);
    }


    /**
     * paint is an Overridden Method from the JComponent class.
     * Method to invoke the painting of the HomeMenu page.
     * Calls the drawMenu method.
     * @param g
     */
    public void paint(Graphics g){
        writeMenu((Graphics2D)g);
    }


    /**
     * drawMenu method is used to paint directly into the HomeMenu rectangle frame.
     * Calls the drawContainer method to draw the Home Menu screen.
     * Calls the drawText method to draw and render the font in the Home Menu screen.
     * Calls the drawButton method to paint/draw the text and button layout of the START and EXIT buttons onto the frame.
     * @param g2d
     */
    public void writeMenu(Graphics2D g2d)
    {
        g2d.drawImage(HomeImage, 1, 1, (int) (menuFace.getWidth()), (int) (menuFace.getHeight()), this);

        writeText(g2d);
        drawButton(g2d);
    }

    /**
     * drawText Method is used to render the text for the Home Menu page.
     * Responsible for drawing the Game Title, Greetings and Credits on the Menu.
     * @param g2d
     */
    private void writeText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    /**
     * drawButton Method is used to render the button features and elements such as:
     * The logical bounds of the Start and Exit button text.
     * Sets the location of the buttons on the menuFace.
     * Changes the text and button color when clicked.
     * @param g2d
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.6);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);

        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);

        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;


        menuButton.setLocation(x,y);




        x = (int)(menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(menuButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.9);

        if(menuClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT,x,y);
        }
        //adding the info button
        x = startButton.x;
        y = startButton.y;

        y *= 1.4;

        infoButton.setLocation(x,y);


        x = (int)(infoButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - iTxtRect.getHeight() / 2);



        x += infoButton.x;
        y += infoButton.y + (menuButton.height * 0.5);

        if(infoClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);
        }
    }

    /**
     * mouseClicked implements the method in MouseListener.
     * It contains the implementation for when user clicks on the START or EXIT buttons.
     * START button will enable the GameBoard.
     * EXIT button will display a message "Goodbye _user_name" in the terminal.
     * @param mouseEvent        to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            owner.enableGameBoard();
        }
        else if(menuButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }

        else if(infoButton.contains(p)){
            owner.enableInfoMenu();
        }


    }

    /**
     * mousePressed Method invoked when a mouse button has been pressed on the START and EXIT buttons.
     * @param mouseEvent    to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }

        else if (infoButton.contains(p))
        {
            infoClicked = true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }

    }

    /**
     * mouseReleased Method invoked when the mouse is released.
     * @param mouseEvent    to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if (infoClicked)
        {
            infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }

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
     * mouseMoved Method implements what should happen when the mouse hovers over the START or EXIT button
     * and what the cursor should look like otherwise.
     * @param mouseEvent    to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || menuButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
