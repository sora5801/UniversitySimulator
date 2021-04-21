import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Project Introduction
Do you miss going to University Campus? Here is a program that let's you simulate that experience once again
*
*/

interface Message {
    String getName();
}

class StudentActionMessage implements Message {
    String name;

    public StudentActionMessage(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }
}

class FoodOrderedMessage implements Message{
    String name;

    public FoodOrderedMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class StudentStatusMessage implements Message{
    String name;

    public StudentStatusMessage(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

/***
 * This is the Model Section
 * Student class. This is the class that represents the user, which is presumably a student at SJSU
 */
class Student extends JComponent {
    private String name;
    private HashSet<Book> bookLists;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private LinkedList<String> diary; // What the student has done today.

    public Student(String name){
        this.name = name;
        this.money = 250;
        bookLists = new HashSet<>();
        bookLists.add(new Book("a book"));//serena
    }

    public String getName(){ return this.name; }
    public double getWallet(){ return this.money;}
    public void setWallet(double money){ this.money = money;}

    public void spendMoney(double money){this.money -= money;}

    public void addBooks(HashSet<Book> books){
        for(Book b: books){
            bookLists.add(b);
        }
    }

    public HashSet<Book> getBookLists(){
        for(Book b: bookLists){
            System.out.println("book: " + b+"\n");
        }
        return bookLists;
    }
    public void printBooks(){
        for(Book b: bookLists){
            System.out.println("book: " + b+"\n");
        }
    }

    public void writeDiary(String diaryEntry){
        this.diary.add(diaryEntry);
    }

    public void interactWithCafeteria(Cafeteria cafeteria){
        while(cafeteria.isDisplayable()){

        }
    }



}

/**
 * This is the Controller section
 * Sources
 * For Date: https://www.javatpoint.com/java-get-current-date
 * For BufferedWriter: https://www.baeldung.com/java-write-to-file
 */
class UniversityCampusFrame extends JFrame {
    BlockingQueue<Message> queue = new LinkedBlockingQueue<>();;
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
    private JTextField rateField;

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
        library = new Library(queue);
        classroom = new Classroom();
        bookstore = new BookStore();
        cafeteria = new Cafeteria();
        //cafeteria.setLayout(new FlowLayout(FlowLayout.RIGHT));


        UniversityCampus.add(campus, "campus");
        UniversityCampus.add(classroom, "classroom");
        UniversityCampus.add(library, "library");
        UniversityCampus.add(bookstore, "bookstore");
        UniversityCampus.add(cafeteria, "cafeteria");


        //The following are bad practices and Should be elimited, but I need to figure out a way to
        //To be able to add messages to the JTextArea of UniversityCampusFrame without giving any of the
        //View classes access to UniversityCampusFram's JTextArea. I hope that it does not involve a massive
        //reworking of the code. That would be a nightmare (Date logged 04/05/2021)
        cafeteria.getResultArea(resultArea);
        cafeteria.getBlockingQueue(queue);

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
                //Adding BlockingQueue to this part. Maybe it might work here.
                try {
                    Message msg = new StudentActionMessage(name);
                    queue.put(msg);
                } catch (InterruptedException e) {
                }
                Message message = null;
                try {
                    message = queue.take();
                } catch (InterruptedException exception) {
                    // do nothing
                }
                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());
                if(message.getName().equals("Campus")){
                    cafeteria.clearAll(); //Adding the clearAll method is the only way I can think of to clear
                    //the fields of cafeteria for now 4/2/2021
                    resultArea.append(dtf.format(now) + " You went to the main campus." + "\n");
                    cl.show(UniversityCampus, "campus");
                }
                if(message.getName().equals("Classroom")) {
                    cafeteria.clearAll();
                    resultArea.append(dtf.format(now) + " You went to the classroom." + "\n");
                    cl.show(UniversityCampus,"classroom");
                    //student.
                }
                if(message.getName().equals("Cafeteria")) {
                    cafeteria.clearAll();
                    resultArea.append(dtf.format(now) + " You went to the cafeteria." + "\n");
                    cl.show(UniversityCampus,"cafeteria");
                    cafeteria.getStudent(student);
                }
                if(message.getName().equals("Library")) {
                    cafeteria.clearAll();
                    resultArea.append(dtf.format(now) + " You went to the library." + "\n");
                    cl.show(UniversityCampus,"library");
                }
                if(message.getName().equals("Bookstore")) {
                    cafeteria.clearAll();
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
                //Adding BlockingQueue to this part. Maybe it might work here.
                try {
                    Message msg = new StudentStatusMessage(name);
                    queue.put(msg);
                } catch (InterruptedException e) {
                }
                Message message = null;
                try {
                    message = queue.take();
                } catch (InterruptedException exception) {
                    // do nothing
                }

                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());
                assert message != null;
                if(message.getName().equals("Name")){
                    resultArea.append("Your name is " + student.getName() + "\n");
                }
                if(message.getName().equals("Wallet")) {
                    resultArea.append("You have $" + student.getWallet() + " in your wallet." + "\n");
                }
                if(message.getName().equals("Homeworks")) {
                    resultArea.append(" You went to the cafeteria." + "\n");
                }
                if(message.getName().equals("Books")) {
                    resultArea.append(" You went to the library." + "\n");
                }
                if(message.getName().equals("Diary")) {
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
                try {
                    Message msg = new StudentActionMessage(name);
                    queue.put(msg);
                } catch (InterruptedException e) {
                }
                Message message = null;
                try {
                    message = queue.take();
                } catch (InterruptedException exception) {
                    // do nothing
                }
                if(message.getName().equals("Save")){
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
 * @Author Matthew
 */
class Cafeteria extends JPanel {
    private BlockingQueue<Message> queue;
    private static final int rectangleWidth = 250;
    private static final int rectangleHeight = 150;
    private HashMap<String, Double> menu = new HashMap<>();
    private HashMap<Integer, String> menuItems = new HashMap<>();
    private HashMap<Integer, Integer> menuCount = new HashMap<>();
    private HashMap<Integer, Boolean> isOnReceipt = new HashMap<>();
    private ArrayList<Integer> receipt = new ArrayList<>();
    private JTextField rateField;
    private JTextArea menuOptions;
    private double orders;
    private double total;
    private JTextArea textArea = new JTextArea();
    private JTextArea receiptArea = new JTextArea();
    private JTextArea resultArea;
    private int itemAmount = 0;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private String lastOrder;
    private Student student;
    ButtonGroup buttonGroup;

    public void paintComponent (Graphics g){
        g.drawString("Bricks Pizza", 760, 245);
        g.drawRect(750, 175, rectangleWidth, rectangleHeight);
        g.drawLine(830,175,830,325);
        g.drawArc(778, 25, 100, 300, 270,90);
        g.drawLine(880,175,880,325);
        g.drawString("We make good pizzas", 881, 245);

        g.drawRect(580, 365, rectangleWidth-100, rectangleHeight-100);
        g.drawLine(630,365,630,415);
        g.drawString("Food", 590, 395);
        g.drawLine(670,365,670,415);
        g.drawString("Menu", 675, 395);

        g.drawRect(510, 440, rectangleWidth, rectangleHeight-100);

        g.drawRect(350, 440, 45, 150);

        g.drawRect(275, 540, 40, 50);
        g.drawArc(275, 520, 40, 45, 0,180);

        g.drawArc(800, 370, 400, 45, 180,360);
        g.drawLine(810, 400, 810, 455);
        g.drawLine(1175, 401, 1175, 459);
        g.drawArc(800, 427, 400, 45, 180,360);

    }

    public void getStudent(Student student){
        this.student = student;
    }

    public void getBlockingQueue(BlockingQueue<Message> blockingQueue){
        queue = blockingQueue;
    }

    /**?
     * This is a bad practice. I am basically giving Cafeteria access to the resultArea of the main campus frame,
     * which should not happen in MVC model. I need to figure out a way around it or ask if it this is an
     * acceptable exception (Date logged 4/05/2021)
     * @param jtextArea
     */
    public void getResultArea(JTextArea jtextArea){
        resultArea = jtextArea;
    }


    public Cafeteria(){
        initializeMenu();
        ActionListener listener = new AddInterestListener();
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        radio1 = new JRadioButton("Menu and order");
        radio2 = new JRadioButton("Receipt");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radio1);
        buttonGroup.add(radio2);

        textArea.append(displayMenu());
        textArea.append("\n What would you like to order?");
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));

        receiptArea.setFont(new Font("monospaced", Font.PLAIN, 12));

        JButton button = new JButton("User Input");
        button.addActionListener(listener);

        add(radio1);
        add(radio2);
        add(button, BorderLayout.EAST);

        if(!isVisible()){
            System.out.println("Gone");
            clearAll();
        }
    }

    public String displayMenu(){
        int count = 1;
        final int dashedLines = 100;
        final int spaces = 15;
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
            bars += String.format("%10d. %5s", number.get(i), items.get(i));
            int length = 110 - String.format("%10d. %s", number.get(i), items.get(i)).length();
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

    class AddInterestListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(radio1.isSelected()) {
                String order=
                        JOptionPane.showInputDialog(null,
                                textArea, "Menu", JOptionPane.INFORMATION_MESSAGE);
                orders = sellFood(Integer.parseInt(order));
                menuCount.put(Integer.parseInt(order), menuCount.get(Integer.parseInt(order)) + 1);
                isOnReceipt.replace(Integer.parseInt(order), true);
                lastOrder = menuItems.get(Integer.parseInt(order));
                if(student.getWallet() >= orders)
                    student.setWallet(student.getWallet() - orders);
                try {
                    Message msg = new FoodOrderedMessage(lastOrder);
                    queue.put(msg);
                } catch (InterruptedException e) {
                }
                Message message = null;
                try {
                    message = queue.take();
                } catch (InterruptedException exception) {
                    // do nothing
                }
                resultArea.append("You ordered a " + message.getName() + "\n");
            }
            if(radio2.isSelected()){
                String studentOrders = "";
                total = 0;
                for(HashMap.Entry<Integer, Integer> entry: menuCount.entrySet()){
                    if(entry.getValue() > 0 && isOnReceipt.get(entry.getKey())) {
                        int length = 70 - (entry.getValue() +"x  " + menuItems.get(entry.getKey())).length();
                        studentOrders += entry.getValue() +"x  " + menuItems.get(entry.getKey());
                        studentOrders = periodPad(studentOrders, length);
                        studentOrders += String.format("%.2f", menu.get(menuItems.get(entry.getKey()))
                                * entry.getValue());
                        total += menu.get(menuItems.get(entry.getKey())) * entry.getValue();
                        studentOrders += "\n";
                    }
                }
                if(total != 0) {
                    studentOrders += "Total ";
                    studentOrders = periodPad(studentOrders, 63);
                    studentOrders += "$" + String.format("%.2f", total);
                }
                receiptArea.setText(studentOrders);
                JOptionPane.showMessageDialog(null, receiptArea, "Receipt",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public double getOrders(){
        return orders;
    }

    public String lastOrder(){
        return lastOrder;
    }

    public void clearAll(){
        this.total = 0;
        int count = 1;
        for(HashMap.Entry<String, Double> entry: menu.entrySet()) {
            menuCount.replace(count, 0);
            count++;
        }
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

        int count = 1;
        for(HashMap.Entry<String, Double> entry: menu.entrySet()){
            menuItems.put(count,entry.getKey());
            menuCount.put(count, 0);
            isOnReceipt.put(count, false);
            count++;
        }
    }


    public double sellFood(int i){
        switch (i){
            case 1:
                return menu.get("Avocado Sandwich");
            case 2:
                return menu.get("Cajun Chicken Benedict");
            case 3:
                return menu.get("Spinach Ravioli with Pesto Sauce");
            case 4:
                return menu.get("Chicken Tikka");
            case 5:
                return menu.get("Lasagna");
            case 6:
                return menu.get("Pesto Chicken & Bow Tie Pasta with Roasted Squash");
            case 7:
                return menu.get("Sliced Genoa Salami and Roasted Garlic Pizza");
            case 8:
                return menu.get("New England Clam Chowder");
            case 9:
                return menu.get("Pasta Primavera");
            case 10:
                return menu.get("Spaghetti and MeatBalls");
            case 11:
                return menu.get("AppleWood Bacon and Black Olives Pizza");
            case 12:
                return menu.get("Pot Pie");

        }
        return 0;
    }

    public String printReceipt(){
        return "";
    }
}



/**
 * TODO Draw a frame that depicts the Martin Luther King Jr. Library
 * @Author: Serena
 */
class Library extends JPanel {
    //private LinkedList<String> bookLists;
    BlockingQueue<Message> queue;
    private HashSet<Book> bookLists = new HashSet<Book>();
    BookCollection bookCollection;
    JPanel checkoutPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    JLabel bookList;
    JCheckBox book1, book2, book3, book4, book5, book6;
    JButton checkOut;
    JTextArea textArea;
    Book b1, b2, b3, b4, b5, b6;

    Library(BlockingQueue<Message> queue){
        this.queue = queue;
        add(checkoutPanel,BorderLayout.CENTER);
        ActionListener listener = new AddInterestListener();
        bookList =new JLabel("Book list: ");
        bookList.setBounds(50,50,300,20);
        book1 =new JCheckBox("Hope Was Here");
        book1.setBounds(100,100,150,20);
        b1 = new Book("Hope Was Here");

        book2 =new JCheckBox("Animal Farm");
        book2.setBounds(100,150,150,20);
        b2 =new Book("Animal Farm");

        book3 =new JCheckBox("Diary of a Young Girl");
        book3.setBounds(100,200,150,20);
        b3 = new Book("Diary of a Young Girl");

        book4 =new JCheckBox("The Shadow of the Wind");
        book4.setBounds(100,200,150,20);
        b4 = new Book("The Shadow of the Wind");

        book5 =new JCheckBox("The Lord of the Rings");
        book5.setBounds(100,200,150,20);
        b5 = new Book("The Lord of the Rings");

        book6 =new JCheckBox("The Satanic Verses");
        book6.setBounds(100,200,150,20);
        b6 = new Book("The Satanic Verses");

        checkOut =new JButton("Check out");
        checkOut.setBounds(100,250,80,30);
        checkOut.addActionListener(listener);


        checkoutPanel.setLayout(new BoxLayout(checkoutPanel,BoxLayout.Y_AXIS));
        checkoutPanel.add(bookList);
        checkoutPanel.add(book1);
        checkoutPanel.add(book2);
        checkoutPanel.add(book3);
        checkoutPanel.add(book4);
        checkoutPanel.add(book5);
        checkoutPanel.add(book6);
        checkoutPanel.add(checkOut);
        setSize(400,400);
        //-----------------------
        textArea = new JTextArea(20, 40);
        textArea.setText(toString());
        bookCollection = new BookCollection();
        displayPanel.add(textArea);
        JButton refreshButton = new JButton("Refresh");
        displayPanel.add(refreshButton);//serena
        add(displayPanel,BorderLayout.EAST);
        refreshButton.addActionListener(e -> {
            textArea.setText(toString());
        });

    }

    class AddInterestListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            float amount=0;
            String message="You just checked out: \n";
            message+="---------------------\n\n";
            if(book1.isSelected()){
                amount++;
                message+="Hope Was Here\n";
                bookLists.add(b1);
            }
            if(book2.isSelected()){
                amount++;
                message+="Animal Farm\n";
                bookLists.add(b2);
            }
            if(book3.isSelected()){
                amount++;
                message+="Diary of a Young Girl\n";
                bookLists.add(b3);
            }
            if(book4.isSelected()){
                amount++;
                message+="The Shadow of the Wind\n";
                bookLists.add(b4);
            }
            if(book5.isSelected()){
                amount++;
                message+="The Lord of the Rings\n";
                bookLists.add(b5);
            }
            if(book6.isSelected()){
                amount++;
                message+="The Satanic Verses\n";
                bookLists.add(b6);
            }
            message+="\n---------------------\n";
            JOptionPane.showMessageDialog(bookList,message+"Total number of books: "+amount);
            try {
                Message msg = new NewBookMessage(bookLists);
                queue.put(msg);
            } catch (InterruptedException exception) {
                // do nothing
            }
        }
    }

    public String toString(){
        String collection = "----Book Collection----\n(refresh to see update)\n\n";
        for(Book b: bookLists){
            collection += b.name;
            collection += "\n";
        }
        return collection;
    }

    public void paintComponent (Graphics g){

    }

    public void updateBookList(HashSet<Book> bookLists) {
        for(Book b: bookLists){
            bookCollection.addBooks(b);
        }
    }


    public class BookCollection
    {
        private ArrayList<Book> books;
        private ArrayList<ChangeListener> listeners;

        public BookCollection()
        {
            books = new ArrayList<Book>();
            listeners = new ArrayList<>();
        }

        public void addBooks(Book b) {
            books.add(b);
            ChangeEvent event = new ChangeEvent(bookCollection);
            for (ChangeListener listener : listeners)
                listener.stateChanged(event);
        }

        public void addChangeListener(ChangeListener listener)
        {
            listeners.add(listener);
        }

        public String toString(){
            String collection = "----Book Collection----\n";
            for(Book b: books){
                collection += b.name;
                collection += "\n";
            }
            return collection;
        }
    }
}

class Book{
    String name;
    Book(String n){
        name = n;
    }

    /**
     * A frame of a library will be drawn here
     * @param g
     */
    public void paintComponent (Graphics g){}
}

class NewBookMessage implements Message {
    HashSet<Book> books;

    public NewBookMessage(HashSet<Book> books) {
        this.books = books;
    }

    public HashSet<Book> getNewBooks() {
        return books;
    }

    @Override
    public String getName() {
        return null;
    }
}

class NewNameMessage implements Message {
    String name;

    public NewNameMessage(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }
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
