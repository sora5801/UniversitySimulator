package UniversitySimulator.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * This is the MainCampusStrategy class. It will be called whenever the student goes to the main campus
 */
public class MainCampusStrategy implements CampusStrategy{
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;
    private LinkedList<String> foodOrdered;
    private HashMap<String, Integer> inventory;
    private HashSet<Book> booklists;

    /**This is the interact method. It will be called in the student class.
     *
     * @param itemMoney What the student bought
     * @param money The amount of money the student has
     * @param inventory The inventory of the student
     * @param booklists The booklists of the student
     */
    @Override
    public void interact(HashMap<String, Double> itemMoney, double money,
                         HashMap<String, Integer> inventory, HashSet<Book> booklists) {
        this.itemMoney = itemMoney;
        this.moneyAmount = money;
        this.booklists = booklists;
        this.inventory = inventory;
    }

    /**
     * Get the amount of money
     * @return double
     */
    @Override
    public double getMoneyAmount() {
        return moneyAmount;
    }

    /**
     * Get what has been purchased and for how much
     * @return HashMap
     */
    @Override
    public HashMap<String, Double> getItemMoney() {
        return this.itemMoney;
    }

    /**
     * Get the inventory
     * @return HashMap
     */
    @Override
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    /**
     * Get the list of books
     * @return HashSet
     */
    @Override
    public HashSet<Book> getBookList() {
        return booklists;
    }

}
