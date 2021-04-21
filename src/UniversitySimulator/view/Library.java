package UniversitySimulator.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import UniversitySimulator.controller.*;
import UniversitySimulator.model.*;

//


/**
 * This is a part of the View Section
 * TODO Draw a frame that depicts the Martin Luther King Jr. Library
 * @Author: Serena
 */
public class Library extends JPanel {
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




    /**
     * A frame of a library will be drawn here
     * @param g
     */
    public void paintComponent (Graphics g){

    }
}


