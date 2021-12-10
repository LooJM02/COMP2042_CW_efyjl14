package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import controller.GameController;

public class InfoMenuView extends JComponent implements MouseListener, MouseMotionListener{

    //add details in info page
    private static final String BACK_TEXT = "Back";
    private static final String INFO = "HOW TO PLAY:";
    private static final String INFO1 = "'A' or 'D' to move left and right.";
    private static final String INFO2 = "Destroy all bricks to win.";
    private static final String INFO3 = "Press ALT+SHIFT+F1 to skip level ";
    private static final String INFO4 = "Press ESC to pause game";
    private static final String INFO5 = "Press SHIFT+A or SHIFT+D to sprint";
    private Font fontTitle;
    private Font fontInstructions;


    private static final Color BG_COLOR = Color.YELLOW.brighter();
    private static final Color TEXT_COLOR = new Color(0, 0, 0);//black
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.darker();
    private static final Color CLICKED_TEXT = Color.BLACK;
//    private static final int BORDER_SIZE = 5;
//    private static final float[] DASHES = {12,6};

    Toolkit t=Toolkit.getDefaultToolkit();
    private Image infoImage = t.getImage("src/InfoMenu.jpg");//background picture in Info Page

    private Rectangle menuFace;
    private Rectangle backButton;

    private Font buttonFont;

    private GameController owner;

    private boolean backClicked;


    public InfoMenuView(GameController owner,Dimension area){
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        backButton = new Rectangle(btnDim);

        buttonFont = new Font("Monospaced",Font.BOLD,backButton.height-2);
        fontTitle = new Font("Noto Mono",Font.BOLD,40);
        fontInstructions = new Font("Monospaced", Font.BOLD, 20);


    }

    public void paint(Graphics g){
        writeMenu((Graphics2D)g);
    }

    public void writeMenu(Graphics2D g2d){
        g2d.drawImage(infoImage, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);
        writeText(g2d);
        addButton(g2d);

    }

    private void writeText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = fontInstructions.getStringBounds(INFO,frc);


        int sX,sY;

        sX = (int)(menuFace.getWidth())/ 4;
        sY = (int)(menuFace.getHeight() / 4);
        g2d.setFont(fontTitle);
        g2d.drawString(INFO,sX,sY);

        int tY;

        tY = (int)(sY + 50);
        g2d.setFont(fontInstructions);
        g2d.drawString(INFO1,5,tY);

        int uY;
        uY = tY + 25;
        g2d.drawString(INFO2, 5, uY);

        int vY;
        vY = uY + 25;
        g2d.drawString(INFO3, 5, vY);

        int zY;
        zY = vY + 25;
        g2d.drawString(INFO4, 5, zY);

        int mY;
        mY = zY + 25;
        g2d.drawString(INFO5, 5, mY);
    }

    private void addButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(BACK_TEXT,frc);


        g2d.setFont(buttonFont);

        int x = 10;
        int y =(int) ((menuFace.height - backButton.height) * 0.95);

        backButton.setLocation(x,y);

        x = (int)(backButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(backButton.getHeight() - txtRect.getHeight()) / 2;

        x += backButton.x;
        y += backButton.y + (backButton.height * 0.9);


        if(backClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(backButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(BACK_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(backButton);
            g2d.drawString(BACK_TEXT,x,y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            owner.enableHomeMenu();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(backButton.contains(p)){
            backClicked = true;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(backClicked ){
            backClicked = false;
            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
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
        if(backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
