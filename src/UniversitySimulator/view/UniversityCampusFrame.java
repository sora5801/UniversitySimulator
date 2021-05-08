package UniversitySimulator.view;

import UniversitySimulator.controller.*;
import UniversitySimulator.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

//

/**
 * This is the heart of the view section. This is where all JFrame is displayed. This class contains
 * an object of all the other classes in view. Here is where a JtextField is at the bottom. Whenever the user
 * selects something from Navigation dropdown menu, the jpanel changes and a message is added to the bottom
 * of where the user went. Whenever the user selects something from the status menu, a message is added to the bottom
 * that displays the status that the user wants.
 * Sources
 * For Date: https://www.javatpoint.com/java-get-current-date
 * @author Matthew Fu, Shiting Li, Nam Ta, Dias Mustafin.
 */
public class UniversityCampusFrame extends JFrame {
    BlockingQueue<Message> queue;
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 1000;
    private static final int SCROLL_HEIGHT = 150; //The

    private ActionListener listener;
    private JPanel UniversityCampus;
    private CardLayout card = new CardLayout();
    private JTextArea resultArea;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    private JTextField rateField;

    Classroom classroom;
    Campus campus;
    Library library;
    BookStore bookstore;
    Cafeteria cafeteria;

    /***
     * This is the constructor of the class. It takes in a Blocking Queue for the controller class.
     * This is where the frame is initialized as well as all other jpanels.
     */
    public UniversityCampusFrame(BlockingQueue<Message> queue){
        //JOptionPane.showMessageDialog(null, "Welcome to the University!");
        this.queue = queue;

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
        bookstore = new BookStore(queue);
        cafeteria = new Cafeteria(queue);
        //cafeteria.setLayout(new FlowLayout(FlowLayout.RIGHT));


        UniversityCampus.add(campus, "campus");
        UniversityCampus.add(classroom, "classroom");
        UniversityCampus.add(library, "library");
        UniversityCampus.add(bookstore, "bookstore");
        UniversityCampus.add(cafeteria, "cafeteria");


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createNavigationMenu());
        menuBar.add(createStatusMenu());


        add(UniversityCampus);
        add(subPanel, BorderLayout.SOUTH);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("University Campus");
        setVisible(true);
    }

    /**
     * Get the cafeteria object
     * @return Cafeteria
     */
    public Cafeteria getCafeteria(){
        return this.cafeteria;
    }

    /**
     * Get the bookstore object
     * @return bookstore object
     */
    public BookStore getBookStore() {return this.bookstore;}

    /**
     * Get the Library object
     * @return library object
     */
    public Library getLibrary() {return this.library;}

    /**
     * Add an error message to the bottom
     * @param error error message
     */
    public void addErrorMessage(String error){
        resultArea.append(error + "\n");
    }

    /**
     * Add what food has been ordered to the bottom
     * @param food food ordered
     */
    public void addOrderedMessage(String food){
        resultArea.append(dtf.format(now) + " You ordered a " + food + "\n");
    }

    /**
     * Add what kind of items has been bought
     * @param items items bought
     */
    public void addItemMessage(LinkedList<String> items) {
        String names = "";
        for (String item : items) {
            names += "\"" + item + "\" ";
        }
        if (names.length() > 0) {
            resultArea.append(dtf.format(now) + " You bought " + names + "\n");
        }
    }

    /**
     * Add what kind of books has been checked out.
     * @param books books checked out.
     * @param booklists the student's current book list.
     */
    public void checkedOutMessage(HashMap<Book, Boolean> books, HashSet<Book> booklists){
        if(!books.isEmpty()) {
            String booksName = "";
            for (HashMap.Entry<Book, Boolean> entry: books.entrySet()) {
                if(entry.getValue() && !booklists.contains(entry.getKey())) {
                    booksName += "\"";
                    booksName += entry.getKey().getName();
                    booksName += "\"  ";
                }
            }
            resultArea.append(dtf.format(now) + " You checked out " + booksName + "\n");
          //  library.updateBookList(books);
        }
    }

    /**
     * Checks the book that the student currently has.
     * @param books
     */
    public void addBooksMessage(HashSet<Book> books){
        String booksName = "";
        String textbooks = "";
        for(Book b: books){
            booksName +="\"";
            booksName += b.name;
            booksName += "\"  ";
        }
        resultArea.append("Library books: " + booksName + "\n");
        resultArea.append("Textbooks: " + textbooks + "\n");
    }

    /**
     * Add where the student has went.
     * @param action
     */
    public void addActionMessage(String action){
        resultArea.append(dtf.format(now) + " You went to the " + action + "." + "\n");
    }

    /**
     * Sends the message that the student has returned all the books
     */
    public void returnBooksMessage(){
        resultArea.append(dtf.format(now) + " You return all the books" + "\n");
    }

    /**
     * Sends the name of the user at the bottom.
     * @param name
     */
    public void addNameMessage(String name){
        resultArea.append("Your name is " + name + "\n");
    }

    /**
     * Tells how much money that the user currently has
     * @param money
     */
    public void addWalletMessage(double money){
        resultArea.append("You have $" + money + " in your wallet." + "\n");
    }

    /**
     * Tells the inventory that the user currently have
     * @param stuff
     */
    public void addInventoryMessage(String stuff){
        resultArea.append("You have: \n" + stuff + "in your inventory. \n");
    }

    /**
     * Constructs the navigation drop down menu
     * @return
     */
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

    /**
     * Constructs the status dropdown menu.
     * @return
     */
    public JMenu createStatusMenu()
        {
            JMenu menu = new JMenu("Status");
            menu.add(createStatusItem("Name"));
            menu.add(createStatusItem("Wallet"));
            menu.add(createStatusItem("Homeworks"));
            menu.add(createStatusItem("Books"));
            menu.add(createStatusItem("Inventory"));
            return menu;
        }


    /**
     * This is navigation action listener. Whenever the user selects a new location
     * the frame will change to go to that location
     * @param name
     * @return
     */
    public JMenuItem createNavigationItem(final String name)
    {
        class NavigationItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event)
            {
                try {
                    Message msg = new StudentActionMessage(name);
                    queue.put(msg);
                } catch (InterruptedException e) {
                    //do nothing
                }
                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());
                if(name.equals("Campus")){
                    cafeteria.clearAll(); //Adding the clearAll method is the only way I can think of to clear
                    //the fields of cafeteria for now 4/2/2021
                    cl.show(UniversityCampus, "campus");
                }
                if(name.equals("Classroom")) {
                    cafeteria.clearAll();
                    cl.show(UniversityCampus,"classroom");
                    //student.
                }
                if(name.equals("Cafeteria")) {
                    cafeteria.clearAll();
                    cl.show(UniversityCampus,"cafeteria");
                }
                if(name.equals("Library")) {
                    cafeteria.clearAll();
                    cl.show(UniversityCampus,"library");
                }
                if(name.equals("Bookstore")) {
                    cafeteria.clearAll();
                    cl.show(UniversityCampus,"bookstore");
                }
            }
        }

        JMenuItem item = new JMenuItem(name);
        ActionListener listener = new NavigationItemListener();
        item.addActionListener(listener);
        return item;
    }

    /**
     * This is the action listener class for the status menu. Whenever the user wants to check on the student's
     * status, the status is added at the bottom
     * @param name
     * @return
     */
    public JMenuItem createStatusItem(final String name){
        class StatusItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                try {
                    Message msg = new StudentStatusMessage(name);
                    queue.put(msg);
                } catch (InterruptedException e) {
                }

            }
        }
        JMenuItem item = new JMenuItem(name);
        ActionListener listener = new StatusItemListener();
        item.addActionListener(listener);
        return item;
    }


}


