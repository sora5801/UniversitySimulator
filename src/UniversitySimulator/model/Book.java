package UniversitySimulator.model;

//

/**
 * This is a book class. It simply contains the name of the book
 * @author Shiting Li
 * @version 1
 */
public class Book {
    public String name;

    /**
     * The constructor of the book class
     * @param n name of the book
     */
    public Book(String n) {
        name = n;
    }

    /**
     * Get the name of the book
     * @return String this is the name of the book
     */
    public String getName(){
        return name;
    }
}


