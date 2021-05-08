package UniversitySimulator.model;

import UniversitySimulator.view.BookStore;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * This is the strategy class for the bookstore. When the student goes to the bookstore,
 * the student will interact with the bookstore.
 * @author Matthew Fu
 */
public class BookStoreStrategy implements CampusStrategy{
    private BookStore bookstore;
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;
    private double orderPrice;
    private LinkedList<String> bookStoreItems;
    private HashMap<String, Integer> inventory;
    private HashSet<Book> booklists;

    /**
     * This method constructs the class and passes the a bookstore object into the bookstore field.
     * @param bookstore pass the bookstore object
     */
    public BookStoreStrategy(BookStore bookstore){
        this.bookstore = bookstore;
    }

    /**This is the interact method. It will be called in the student class.
     * This method adds book store items into the student's inventory
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
        this.orderPrice = bookstore.getTotalPrice();
        this.bookStoreItems = bookstore.getItems();
        addItems(bookStoreItems);
        if(moneyAmount >= this.orderPrice)
            moneyAmount -= this.orderPrice;
    }

    /**
     * Change the inventory of the student
     * @param items A list of items to add to the inventory of the class
     */
    private void addItems(LinkedList<String> items){
        for (String i: items){
            if(!this.inventory.containsKey(i))
                this.inventory.put(i, 1);
            else
                this.inventory.replace(i, this.inventory.get(i) + 1);
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
