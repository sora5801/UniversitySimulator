package UniversitySimulator.model;


import UniversitySimulator.view.Library;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This is the LibraryStrategy class. It will be called when the student goes to the library
 * @author Matthew Fu
 */
public class LibraryStrategy implements CampusStrategy{
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;
    private HashMap<String, Integer> inventory;
    private HashSet<Book> booklists;
    private Library library;
    private HashMap<Book, Boolean> checkedOut;

    /**
     * Construct the LibraryStrategy class by passing a library object in
     * @param library a library object
     */
    public LibraryStrategy(Library library){
        this.library = library;
    }

    /**This is the interact method. It will be called in the student class. This method
     * adds checked out books to the student Book lists
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
        this.inventory = inventory;
        this.booklists = booklists;
        this.checkedOut = library.getCheckedOut();
        for (HashMap.Entry<Book, Boolean> entry: this.checkedOut.entrySet()){
            if(!this.booklists.contains(entry.getKey()) && entry.getValue())
                this.booklists.add(entry.getKey());
        }
    }

    /**
     * Get the amount of money
     * @return double
     */
    @Override
    public double getMoneyAmount() {
        return this.moneyAmount;
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
