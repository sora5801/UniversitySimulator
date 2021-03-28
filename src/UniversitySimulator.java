import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JMenu;

/**
 * Project Introduction
Do you miss going to University Campus? Here is a program that let's you simulate that experience once again
*
*/

/***
 * Student class. This is the class that represents the user, which is presumably a student at SJSU
 */
class Student extends JComponent {
    private String name;
    private LinkedList<String> books;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private LinkedList<String> diary; // What the student has done today.

    public Student(String name){
        this.name = name;
        this.money = 250;
    }

    public String getName(){ return this.name; }
    public double getWallet(){ return this.money;}

    public void spendMoney(double money){this.money -= money;}

    public void addBooks(String books){
        this.books.add(books);
    }

    public void writeDiary(String diaryEntry){
        this.diary.add(diaryEntry);
    }

}

/**
 * Sources
 * For Date: https://www.javatpoint.com/java-get-current-date
 * For BufferedWriter: https://www.baeldung.com/java-write-to-file
 */
class UniversityCampusFrame extends JFrame {
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 1000;
    private static final int SCROLL_HEIGHT = 150; //The

    private ActionListener listener;
    private JPanel UniversityCampus;
    private CardLayout card = new CardLayout();
    private JTextArea resultArea;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    private String saveFileName = "savefile.txt";

    Student student;
    Classroom classroom;
    Campus campus;
    Library library;
    BookStore bookstore;
    Cafeteria cafeteria;

    /***
     * TODO: add a time and date system. That is, everytime a student goes somewhere, time increaseses
     */
    public UniversityCampusFrame(){
        //JOptionPane.showMessageDialog(null, "Welcome to the University!");
        student = new Student("Jane");

        card = new CardLayout();

        UniversityCampus = new JPanel();
        UniversityCampus.setLayout(card);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("monospaced", Font.PLAIN, 12));
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
        JButton button2 = new JButton("User option");
        cafeteria.add(button2);

        UniversityCampus.add(campus, "campus");
        UniversityCampus.add(classroom, "classroom");
        UniversityCampus.add(library, "library");
        UniversityCampus.add(bookstore, "bookstore");
        UniversityCampus.add(cafeteria, "cafeteria");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createNavigationMenu());
        menuBar.add(createStatusMenu());
        menuBar.add(createFileMenu());

        add(UniversityCampus);
        add(subPanel, BorderLayout.SOUTH);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void cafeteriaOperation(){
        resultArea.append(cafeteria.cafeteriaUI() + "\n");
        resultArea.append(cafeteria.displayMenu());
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

        public JMenu createStatusMenu()
        {
            JMenu menu = new JMenu("Status");
            menu.add(createStatusItem("Name"));
            menu.add(createStatusItem("Wallet"));
            menu.add(createStatusItem("Homeworks"));
            menu.add(createStatusItem("Books"));
            menu.add(createStatusItem("Diary"));
            return menu;
        }

        public JMenu createFileMenu(){
            JMenu menu = new JMenu("File");
            menu.add(createFileItem("Save"));
            menu.add(createFileItem("Load"));
            return menu;

        }

    public JMenuItem createNavigationItem(final String name)
    {
        class NavigationItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event)
            {
                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());
                if(name == "Campus"){
                    resultArea.append(dtf.format(now) + " You went to the main campus." + "\n");
                    cl.show(UniversityCampus, "campus");
                }
                if(name == "Classroom") {
                    resultArea.append(dtf.format(now) + " You went to the classroom." + "\n");
                    cl.show(UniversityCampus,"classroom");
                    //student.
                }
                if(name == "Cafeteria") {
                    resultArea.append(dtf.format(now) + " You went to the cafeteria." + "\n");
                    cl.show(UniversityCampus,"cafeteria");
                    cafeteriaOperation();
                }
                if(name == "Library") {
                    resultArea.append(dtf.format(now) + " You went to the library." + "\n");
                    cl.show(UniversityCampus,"library");
                }
                if(name == "Bookstore") {
                    resultArea.append(dtf.format(now) + " You went to the bookstore." + "\n");
                    cl.show(UniversityCampus,"bookstore");
                }
            }
        }

        JMenuItem item = new JMenuItem(name);
        ActionListener listener = new NavigationItemListener();
        item.addActionListener(listener);
        return item;
    }

    public JMenuItem createStatusItem(final String name){
        class StatusItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());
                if(name == "Name"){
                    resultArea.append(" Your name is " + student.getName() + "\n");
                }
                if(name == "Wallet") {
                    resultArea.append(" You have $" + student.getWallet() + "in your wallet." + "\n");
                }
                if(name == "Homeworks") {
                    resultArea.append(" You went to the cafeteria." + "\n");
                }
                if(name == "Books") {
                    resultArea.append(" You went to the library." + "\n");
                }
                if(name == "Diary") {
                    resultArea.append(" You went to the bookstore." + "\n");
                }
            }
        }
        JMenuItem item = new JMenuItem(name);
        ActionListener listener = new StatusItemListener();
        item.addActionListener(listener);
        return item;
    }

    public JMenuItem createFileItem(final String name){
        class FileItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                if(name == "Save"){
                    try {
                        save(saveFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    resultArea.append("Saved");
                }
            }
        }
        JMenuItem item = new JMenuItem(name);
        ActionListener listener = new FileItemListener();
        item.addActionListener(listener);
        return item;
    }

    //Functions to be implemented

    /***
     * TODO
     * This is a save function that saves everything that the student did
     * This function will write to the savefile, which is a text file, and input everything that
     * the student has done today and what
     */
    public void save(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.append(resultArea.getText());
        writer.close();
        System.out.println(resultArea.getText());
    }

    public void load(){ }
}

/***
 * TODO Create an abridged classroom system.
 * OPTIONAL TODO Add a test system based on how well the student did his or her homework.
 */
class Classroom extends JPanel {
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

/**
 * This is the University Campus Frame
 * TODO Finish drawing a frame that depicts a topdown view of SJSU
 */
class Campus extends JPanel{
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
class Cafeteria extends JPanel {
    private HashMap<String, Double> menu = new HashMap<String, Double>();
    private ArrayList<Integer> receipt = new ArrayList<Integer>();

    public void paintComponent (Graphics g){}

    public Cafeteria(){
        initializeMenu();
    }

    public String displayMenu(){
        int count = 1;
        final int dashedLines = 100;
        final int spaces = 25;
        String bars = "";
        LinkedList<String> items = new LinkedList<>();
        LinkedList<Double> price = new LinkedList<>();
        LinkedList<Integer> number = new LinkedList<>();

        bars = spacePad(bars, spaces);
        bars = dashPad(bars, dashedLines);
        bars+="\n";
        bars = spacePad(bars, 3 * spaces);
        bars += "Menu";
        bars = spacePad(bars, spaces);
        bars+="\n";
        bars = spacePad(bars, spaces);
        bars = dashPad(bars, dashedLines);
        bars += "\n";
        for(HashMap.Entry<String, Double> entry: menu.entrySet()){
            items.add(entry.getKey());
            price.add(entry.getValue());
            number.add(count);
            count++;
        }

        for(int i = 0; i < items.size(); i++){
            bars += String.format("%25d. %5s", number.get(i), items.get(i));
            int length = 120 - String.format("%25d. %s", number.get(i), items.get(i)).length();
            bars = periodPad(bars, length);
            bars += String.format("$%.2f%n", price.get(i));
              }

        bars = spacePad(bars, spaces);
        bars = dashPad(bars, dashedLines);
        bars += "\n";

        return bars;
    }

    private String periodPad(String s, int n){
        for (int i = 0; i < n; i++)
            s += ".";
        return s;
    }

    private String spacePad(String s, int n){
        for (int i = 0; i < n; i++)
            s += " ";
        return s;
    }

    private String dashPad(String s, int n){
        for (int i = 0; i < n; i++)
            s += "-";
        return s;
    }

    public void initializeMenu(){
        menu.put("AppleWood Bacon and Black Olives Pizza", 7.50);
        menu.put("Sliced Genoa Salami and Roasted Garlic Pizza", 7.00);
        menu.put("New England Clam Chowder", 6.00);
        menu.put("Pasta Primavera", 6.00);
        menu.put("Spinach Ravioli with Pesto Sauce", 6.00);
        menu.put("Pot Pie", 6.00);
        menu.put("Lasagna", 6.50);
        menu.put("Pesto Chicken & Bow Tie Pasta with Roasted Squash", 7.00);
        menu.put("Chicken Tikka", 6.50);
        menu.put("Spaghetti and MeatBalls", 6.50);
        menu.put("Avocado Sandwich", 5.50);
        menu.put("Cajun Chicken Benedict", 6.00);
    }

    public String cafeteriaUI(){
        return "Welcome to the Cafeteria!";
    }

    public double sellFood(int i){
        switch (i){
            case 1:
                return menu.get("AppleWood Bacon and Black Olives Pizza");
            case 2:
                return menu.get("Sliced Genoa Salami and Roasted Garlic Pizza");
            case 3:
                return menu.get("New England Clam Chowder");
            case 4:
                return menu.get("Pasta Primavera");
            case 5:
                return menu.get("Spinach Ravioli with Pesto Sauce");
            case 6:
                return menu.get("Pot Pie");
            case 7:
                return menu.get("Lasagna");
            case 8:
                return menu.get("Pesto Chicken & Bow Tie Pasta with Roasted Squash");
            case 9:
                return menu.get("Chicken Tikka");
            case 10:
                return menu.get("Spaghetti and MeatBalls");
            case 11:
                return menu.get("Avocado Sandwich");
            case 12:
                return menu.get("Cajun Chicken Benedict");

        }
        return 0;
    }

    public String printReceipt(){
        return "";
    }
}

class Book{
    private int k;
    private String l;
    Book(int i, String s){
        k = i;
        l = s;
    }
}

/**
 * TODO Draw a frame that depicts the Martin Luther King Jr. Library
 */
class Library extends JPanel {
    private LinkedList<String> bookLists;
    //private LinkedList<Book> bookLists = new LinkedList<Book>();

    public void displayBookLists(){

    }

    Library(){
      //  Book b1 = new Book(100, "s");
      //  bookLists.add(b1);
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
class BookStore extends JPanel {

    private LinkedList<String> Items;

    public BookStore(){


    }

    public void paintComponent (Graphics g){}

    public JMenu createPurchaseMenu(){
        JMenu menu = new JMenu("Purchase");
        return menu;
    }
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
