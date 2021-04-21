package UniversitySimulator.controller;

import java.util.HashSet;

import UniversitySimulator.model.*;

public class NewBookMessage implements Message {
    HashSet<Book> books;

    public NewBookMessage(HashSet<Book> books) {
        this.books = books;
    }

    public HashSet<Book> getNewBooks() {
        return books;
    }

    @Override
    public String getName() {
        return "";
    }
}
