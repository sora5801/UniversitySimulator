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
     * @param itemMoney
     * @param money
     * @param inventory
     * @param booklists
     */
    public void interact(HashMap<String, Double> itemMoney, double money,
                         HashMap<String, Integer> inventory, HashSet<Book> booklists);

    /**
     * This will return the amount of money that the student has
     * @return
     */
    public double getMoneyAmount();

    /**
     * This will return what item the student has bought and for how much
     * @return
     */
    public HashMap<String, Double> getItemMoney();

    /**
     * This will return the current inventory of the student.
     * @return
     */
    public HashMap<String, Integer> getInventory();

    /**
     * This will return the current book list of the student.
     * @return
     */
    public HashSet<Book> getBookList();
}
