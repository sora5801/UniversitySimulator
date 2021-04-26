package UniversitySimulator.view;

import UniversitySimulator.controller.*;
import UniversitySimulator.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

//

/**
 * This is the View section
 * Sources
 * For Date: https://www.javatpoint.com/java-get-current-date
 * For BufferedWriter: https://www.baeldung.com/java-write-to-file
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
    public UniversityCampusFrame(BlockingQueue<Message> queue){
        //JOptionPane.showMessageDialog(null, "Welcome to the University!");
        this.queue = queue;
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
        menuBar.add(createFileMenu());

        add(UniversityCampus);
        add(subPanel, BorderLayout.SOUTH);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("University Campus");
        setVisible(true);
    }

    public Cafeteria getCafeteria(){
        return this.cafeteria;
    }


    public void addOrderedMessage(String food){
        resultArea.append(dtf.format(now) + " You ordered a " + food + "\n");
    }


    public void checkedOutMessage(HashSet<Book> books){
        String booksName = "";
        for(Book b: books){
            booksName +="\"";
            booksName += b.name;
            booksName += "\"  ";
        }
        resultArea.append(dtf.format(now) + " You checked out " + booksName + "\n");
        library.updateBookList(books);
    }

    public void addActionMessage(String action){
        resultArea.append(dtf.format(now) + " You went to the " + action + "." + "\n");
    }

    public void returnBooksMessage(){
        resultArea.append(dtf.format(now) + " You return all the books" + "\n");
    }

    public void addNameMessage(String name){
        resultArea.append("Your name is " + name + "\n");
    }

    public void addWalletMessage(double money){
        resultArea.append("You have $" + money + " in your wallet." + "\n");
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
                    cafeteria.getStudent(student);
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

    public JMenuItem createStatusItem(final String name){
        class StatusItemListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                //Adding BlockingQueue to this part. Maybe it might work here.
                try {
                    Message msg = new StudentStatusMessage(name);
                    queue.put(msg);
                } catch (InterruptedException e) {
                }

                CardLayout cl = (CardLayout) (UniversityCampus.getLayout());

                if(name.equals("Name")){
                    resultArea.append("Your name is " + student.getName() + "\n");
                }
                if(name.equals("Wallet")) {
                    resultArea.append("You have $" + student.getWallet() + " in your wallet." + "\n");
                }
                if(name.equals("Homeworks")) {
                    resultArea.append(" You went to the cafeteria." + "\n");
                }
                if(name.equals("Books")) {
                    resultArea.append(" You went to the library." + "\n");
                }
                if(name.equals("Diary")) {
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


