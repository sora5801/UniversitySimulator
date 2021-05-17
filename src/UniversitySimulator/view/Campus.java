package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;


/**
 * This is the University Campus Frame. This depicts the main campus map
 * @author Matthew Fu
 */
public class Campus extends JPanel{
    private static final int rectangleWidth = 250;
    private static final int rectangleHeight = 150;

    /***
     * Draws the entire Frame of the campus map
     * @param g the frame
     */
    public void paintComponent (Graphics g){
        super.paintComponent(g);

        // draw the Classroom Box
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Classroom", 650, 130);
        g.drawRect(600, 80, rectangleWidth, rectangleHeight-50);

        // draw the Library Box
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Library", 80, 90);
        g.drawRect(40, 40, rectangleWidth, rectangleHeight);

        g.drawString("Cafeteria", 640, 300);
        g.drawString("Bookstore", 900, 300);
        g.drawRect(620, 240, rectangleWidth+200, rectangleHeight-40);

        g.drawLine(1000, 100, 1100, 100);
        g.drawLine(1050, 150, 1050, 50);
        g.drawOval(1000, 50, 100, 100);
        g.drawString("N", 1042, 47);
        g.drawString("E", 1103, 108);
        g.drawString("S", 1042, 171);
        g.drawString("W", 975, 108);

    }
}


