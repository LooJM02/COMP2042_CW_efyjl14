package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import controller.GameController;

public class ScoreBoardView extends JComponent implements MouseListener, MouseMotionListener{

    //add details in info page
    public static final String EXIT_TEXT = "EXIT";
    private static final String INSTRUCTIONS = "HIGH SCORE :";
    public static  String HIGHSCORE1 = "Maeyi : 30 ";
    private static final String HIGHSCORE2 = "Sophie : 28";
    private static final String HIGHSCORES3 = "Alex : 18";
    private static final String HIGHSCORE4 = "Trisha : 17";
    private static final String HIGHSCORE5 = "Crystal : 17";
    private Font TitleFont;
    private Font instructionsFont;


    private static final Color BG_COLOR = Color.YELLOW.brighter();
    private static final Color TEXT_COLOR = new Color(0, 0, 0);//black
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.darker();
    private static final Color CLICKED_TEXT = Color.BLACK;
//    private static final int BORDER_SIZE = 5;
  //  private static final float[] DASHES = {12,6};

    Toolkit t=Toolkit.getDefaultToolkit();
    private Image ScorePic = t.getImage("src/HighScoreBg.jpg");//background picture in Score board

    private Rectangle menuFace;
    private Rectangle exitButton;


    private Font buttonFont;

    private GameController owner;

    private boolean exitClicked;


    public ScoreBoardView(GameController owner,Dimension area){
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        exitButton = new Rectangle(btnDim);

        buttonFont = new Font("Monospaced",Font.BOLD,exitButton.height-2);
        TitleFont = new Font("Noto Mono",Font.BOLD,40);
        instructionsFont = new Font("Monospaced", Font.BOLD, 20);


    }

    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    public void drawMenu(Graphics2D g2d){
        g2d.drawImage(ScorePic, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);
        drawText(g2d);
        drawButton(g2d);
    }



    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = instructionsFont.getStringBounds(INSTRUCTIONS,frc);
        int sX,sY;

        sX = (int)(menuFace.getWidth())/ 4;
        sY = (int)(menuFace.getHeight() / 4);
        g2d.setFont(TitleFont);
        g2d.drawString(INSTRUCTIONS,sX,sY);

        int tY;

        tY = (int)(sY + 50);
        g2d.setFont(instructionsFont);
        g2d.drawString(HIGHSCORE1,5,tY);

        int uY;
        uY = tY + 25;
        g2d.drawString(HIGHSCORE2, 5, uY);

        int vY;
        vY = uY + 25;
        g2d.drawString(HIGHSCORES3, 5, vY);

        int zY;
        zY = vY + 25;
        g2d.drawString(HIGHSCORE4, 5, zY);

        int mY;
        mY = zY + 25;
        g2d.drawString(HIGHSCORE5, 5, mY);
    }

    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);


        g2d.setFont(buttonFont);

        int x = 10;
        int y =(int) ((menuFace.height - exitButton.height) * 0.95);

        exitButton.setLocation(x,y);

        x = (int)(exitButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - txtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (exitButton.height * 0.9);


        if(exitClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(exitClicked ){
            exitClicked = false;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButton.contains(p)) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        else {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    
}