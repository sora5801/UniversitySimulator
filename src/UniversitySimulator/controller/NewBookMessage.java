package UniversitySimulator.controller;

import java.util.HashMap;
import java.util.HashSet;

import UniversitySimulator.model.*;

/**
 * This is the message that is called everytime the student checks out a new book
 */
public class NewBookMessage implements Message {
    HashMap<Book, Boolean> books;

    /**
     * Constructs the message with the books checked out.
     * @param books
     */
    public NewBookMessage(HashMap<Book, Boolean> books) {
        this.books = books;
    }

    /**
     * Get the list of books checked out
     * @return
     */
    public HashMap<Book, Boolean> getNewBooks() {
        return books;
    }

    /**
     * not used
     * @return
     */
    @Override
    public String getName() {
        return "";
    }
}
