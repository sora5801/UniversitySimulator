package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//

/***
 *
 * This is the Classroom
 * @author Dias Mustafin
 */
public class Classroom extends JPanel {
    private static final int rectangleWidth = 850;
    private static final int rectangleHeight = 600;

    JPanel classNamesPanel;
    JButton[] classNames;

    public Classroom() {
        this.classNamesPanel = new JPanel();
        this.classNamesPanel.setMaximumSize(new Dimension(200, 400));
        this.classNamesPanel.setLayout(new BoxLayout(this.classNamesPanel, BoxLayout.Y_AXIS));
        this.classNames = new JButton[5];

        for(int i = 0; i < classNames.length; i++) {
            this.classNames[i] = new JButton("Class " + i);
            this.classNamesPanel.add(this.classNames[i]);
        }

        this.add(classNamesPanel);
        this.setSize(rectangleWidth, rectangleHeight);
        this.setVisible(true);


    }



    /**
     * A frame of a classroom will be drawn here
     * @param g
     */
   /* public void paintComponent (Graphics g){
        super.paintComponent(g);
        //g.drawString(className, 10, 10);
        g.drawRect(100, 50, rectangleWidth, rectangleHeight);

    }*/
}


