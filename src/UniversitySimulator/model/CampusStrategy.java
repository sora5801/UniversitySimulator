package UniversitySimulator.model;

import java.util.HashMap;
import java.util.HashSet;


/**
 * This interface is used to implement the strategy pattern. When the student goes to each respective location
 * on the campus, a new CampusStrategy will be set in the student's field.
 */
public interface CampusStrategy {
    /**
     * This is the method that the student will use to interact with the building of the campus.
     * @param itemMoney What the student bought
     * @param money The amount of money the student has
     * @param inventory The inventory of the student
     * @param booklists The booklists of the student
     */
    public void interact(HashMap<String, Double> itemMoney, double money,
                         HashMap<String, Integer> inventory, HashSet<Book> booklists);

    /**
     * This will return the amount of money that the student has
     * @return the amount of money
     */
    public double getMoneyAmount();

    /**
     * This will return what item the student has bought and for how much
     * @return the list of items and money
     */
    public HashMap<String, Double> getItemMoney();

    /**
     * This will return the current inventory of the student.
     * @return the inventory of the student
     */
    public HashMap<String, Integer> getInventory();

    /**
     * This will return the current book list of the student.
     * @return the booklist in hashset
     */
    public HashSet<Book> getBookList();
}
