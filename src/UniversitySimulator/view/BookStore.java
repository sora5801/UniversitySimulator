package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

//


/***
 * TODO Draw a frame that depicts the Bookstore
 */
public class BookStore extends JPanel {

    private LinkedList<String> Items;

    public BookStore(){


    }

    public void paintComponent (Graphics g){}

    public JMenu createPurchaseMenu(){
        JMenu menu = new JMenu("Purchase");
        return menu;
    }
}

