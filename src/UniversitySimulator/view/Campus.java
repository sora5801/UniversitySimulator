package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;

//

/**
 * This is the University Campus Frame
 * TODO Finish drawing a frame that depicts a topdown view of SJSU
 * This is a part of the View section
 */
public class Campus extends JPanel{
    private final int xCoord = 50; //Centered at 0,0
    private final int yCoord = 50;
    private int Width;
    private int Height;
    private static final int rectangleWidth = 250;
    private static final int rectangleHeight = 150;
    private JTextArea resultArea;
    private static final int AREA_ROWS = 10;
    private static final int AREA_COLUMNS = 30;

    public Campus(){

    }

    /***
     * Draws the entire Frame
     * @param g
     */
    public void paintComponent (Graphics g){
        super.paintComponent(g);

        // draw the Classroom Box
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Classroom", 650, 150);
        g.drawRect(600, 100, rectangleWidth, rectangleHeight);

        // draw the Library Box
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Library", 950, 625);
        g.drawRect(900, 600, rectangleWidth, rectangleHeight);

    }
}


