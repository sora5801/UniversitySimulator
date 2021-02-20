import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Project Introduction
Do you miss going to University Campus? Here is a program that let's you simulate that experience once again
*
*/

/***
 * Student class. This is the class that represents the user, which is presumably a student at SJSU 
 */
class Student extends JComponent {
    LinkedList<String> books;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private String diary; // What the student has done today.

    public void addBooks(String books){
        this.books.add(books);
    }

}

/**
 *
 */
class UniversityCampusFrame extends JFrame {
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 1000;
    private static final int SCROLL_HEIGHT = 150; //The

    private ActionListener listener;
    private JPanel UniversityCampus;
    private CardLayout card = new CardLayout();
    private JTextArea resultArea;

    Student student;
    Classroom classroom;
    Campus campus;
    Library library;
    BookStore bookstore;
    Cafeteria cafeteria;

    /***
     * TODO: add a time and date system. That is, everytime a student goes somewhere,
     */
    public UniversityCampusFrame(){
        //JOptionPane.showMessageDialog(null, "Welcome to the University!");
        student = new Student();

        card = new CardLayout();

        UniversityCampus = new JPanel();
        UniversityCampus.setLayout(card);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setText("Welcome to the University!" + "\n");

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setMaximumSize(new Dimension(FRAME_WIDTH, SCROLL_HEIGHT));
        scrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, SCROLL_HEIGHT));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.add(scrollPane, BorderLayout.CENTER);

        campus = new Campus();
        library = new Library();
        classroom = new Classroom();
        bookstore = new BookStore();
        cafeteria = new Cafeteria();

        UniversityCampus.add(campus, "campus");
        UniversityCampus.add(classroom, "classroom");
        UniversityCampus.add(library, "library");
        UniversityCampus.add(bookstore, "bookstore");
        UniversityCampus.add(cafeteria, "cafeteria");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createNavigationMenu());
        add(UniversityCampus);
        add(subPanel, BorderLayout.SOUTH);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }


        public JMenu createNavigationMenu()
        {
            JMenu menu = new JMenu("Navigation");
            menu.add(createNavigationItem("Campus"));
            menu.add(createNavigationItem("Classroom"));
            menu.add(createNavigationItem("Cafeteria"));
            menu.add(createNavigationItem("Library"));
            menu.add(createNavigationItem("Bookstore"));
            return menu;
        }

    public JMenuItem createNavigationItem(final String name)
    {
        class NavigationItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event)
            {
                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());
                if(name == "Campus"){
                    resultArea.append("You went to the main campus");
                    cl.show(UniversityCampus, "campus");
                }
                if(name == "Classroom") {
                    resultArea.append("You went to the classroom." + "\n");
                    cl.show(UniversityCampus,"classroom");
                }
                if(name == "Cafeteria") {
                    resultArea.append("You went to the cafeteria." + "\n");
                    cl.show(UniversityCampus,"cafeteria");
                }
                if(name == "Library") {
                    resultArea.append("You went to the library." + "\n");
                    cl.show(UniversityCampus,"library");
                }
                if(name == "Bookstore") {
                    resultArea.append("You went to the bookstore." + "\n");
                    cl.show(UniversityCampus,"bookstore");
                }
            }
        }

        JMenuItem item = new JMenuItem(name);
        ActionListener listener = new NavigationItemListener();
        item.addActionListener(listener);
        return item;
    }

    class ChoiceListener implements ActionListener{
        public void actionPerformed(ActionEvent event){

        }
    }

    //Functions to be implemented

    /***
     * TODO
     * This is a save function that saves everything that the student did
     * This function will write to the savefile, which is a text file, and input everything that
     * the student has done today and what
     */
    public void save(){ }

    public void load(){ }
}

/***
 * TODO Create an abridged classroom system.
 * OPTIONAL TODO Add a test system based on how well the student did his or her homework.
 */
class Classroom extends JComponent {
    public Classroom(){

    }

    /**
     * A frame of a classroom will be drawn here
     * @param g
     */
    public void paintComponent (Graphics g){
        super.paintComponent(g);

    }
}

/**
 * This is the University Campus Frame
 * TODO Finish drawing a frame that depicts a topdown view of SJSU
 */
class Campus extends JComponent{
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

/**
 * TODO Draw a frame that depicts the school cafeteria
 */
class Cafeteria extends JComponent {
    public void paintComponent (Graphics g){}
}

/**
 * TODO Draw a frame that depicts the Martin Luther King Jr. Library
 */
class Library extends JComponent {
    private LinkedList<String> bookLists;

    public void displayBookLists(){

    }

    /**
     * A frame of a library will be drawn here
     * @param g
     */
    public void paintComponent (Graphics g){}
}

/***
 * TODO Draw a frame that depicts the Bookstore
 */
class BookStore extends JComponent {

    private LinkedList<String> Items;

    public void paintComponent (Graphics g){}
}

/**
 *
 */
public class UniversitySimulator {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new UniversityCampusFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("University Campus");
        frame.setVisible(true);
    }
}
