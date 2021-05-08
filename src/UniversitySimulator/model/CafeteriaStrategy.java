package UniversitySimulator.model;


import UniversitySimulator.view.*;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This is the CafeteriaStrategy class. When the student goes to the cafeteria, this class will be called.
 * @author Matthew Fu
 */
public class CafeteriaStrategy implements CampusStrategy{
    private Cafeteria cafeteria;
    private double orderPrice;
    private String lastOrder;
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;
    private HashMap<String, Integer> inventory;
    private HashSet<Book> booklists;

    /**
     * Construct this class by placing a cafeteria object into the field
     * @param cafeteria
     */
    public CafeteriaStrategy(Cafeteria cafeteria){
        this.cafeteria = cafeteria;
    }

    /**
     * Get the amount of money
     * @return double
     */
    @Override
    public double getMoneyAmount(){
        return this.moneyAmount;
    }

    /**
     * Get what has been purchased and for how much
     * @return HashMap
     */
    @Override
    public HashMap<String, Double> getItemMoney(){
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
        this.inventory = inventory;
        this.booklists = booklists;
        this.orderPrice = cafeteria.getOrders();
        this.lastOrder = cafeteria.lastOrder();
        this.itemMoney.put(lastOrder, orderPrice);
        if(moneyAmount >= this.orderPrice)
            moneyAmount -= this.orderPrice;
    }
}
