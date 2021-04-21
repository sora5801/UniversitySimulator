package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;

//

/***
 * TODO Create an abridged classroom system.
 * OPTIONAL TODO Add a test system based on how well the student did his or her homework.
 * This is a part of the View Section
 */
public class Classroom extends JPanel {
    private String homework;
    public Classroom(){

    }
    public String giveHomework(){
        return "";
    }

    /**
     * A frame of a classroom will be drawn here
     * @param g
     */
    public void paintComponent (Graphics g){
        super.paintComponent(g);

    }
}


