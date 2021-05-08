package UniversitySimulator.model;

import java.util.*;

/**
 * This is the Student class. This is the main model of project. Everything that is done by the user affects the student
 * class.
 * @author Matthew Fu, Shiting Li, Nam Ta, Dias Mustafin
 */
public class Student {
    private String name;
    private double gpa;
    private HashSet<Book> bookLists;
    private HashMap<String, Integer> inventory;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private LinkedList<String> classList; // Student's class list
    private final List<Double> credits = new ArrayList<>();
    private final List<Double> points = new ArrayList<>();
    private HashMap<String, Double> itemMoney;
    private CampusStrategy campusStrategy;

    /**
     * Construct the student by giving it a name.
     * Initialize the student's wallet inventory, booklists, campusStrategy, and itemMoney list;
     * @param name
     */
    public Student(String name) {
        this.inventory = new HashMap<>();
        this.itemMoney = new HashMap<>();
        this.campusStrategy = new MainCampusStrategy();
        this.name = name;
        this.money = 25000;
        bookLists = new HashSet<>();
    }

    /**
     * Return student's name
     * @return String name of the student
     */
    public String getName(){ return this.name; }

    /**
     * Return student's wallet
     * @return double money;
     */
    public double getWallet(){ return this.money;}

    private double calculateGpa(Student student) {
        double totalCredits = getTotal(student.credits);
        double totPts = getTotal(student.points);
        double gpa = totPts / totalCredits;
        return gpa;
    }

    /**
     * Sets the campus Strategy of the student
     * @param campusStrategy the campus strategy to be set.
     */
    public void setCampusStrategy(CampusStrategy campusStrategy){
        this.campusStrategy = campusStrategy;
    }

    /**
     * This is the method that will allow the student to interact with the buildings of the campus.
     * This method passes all of the student's private field into the campusStrategy that it currently has.
     * When the user changes something in the controller, student's field will also change.
     */
    public void interactWithBuilding(){
        campusStrategy.interact(itemMoney, this.money, this.inventory, this.bookLists);
        this.itemMoney = campusStrategy.getItemMoney();
        this.money = campusStrategy.getMoneyAmount();
        this.inventory = campusStrategy.getInventory();
        this.bookLists = campusStrategy.getBookList();
    }

    private double getTotal(List<Double> doubles) {
        double total = 0;
        for(Double d:doubles) {
            total += d;
        }
        return total;
    }

    /**
     * Get the book list of the student
     * @return HashSet list of books the student has
     */
    public HashSet<Book> getBookLists(){
        return bookLists;
    }

    /**
     * Return all the books on the student
     * @return
     */
    public void returnBooks(){
        bookLists.clear();
    }

    /**
     * Formats the inventory on the student
     * @return String the inventory of the student.
     */
    public String getInventory(){
        String stuff = "";
        for(HashMap.Entry<String, Integer> entry: inventory.entrySet()){
            stuff += entry.getValue() + " " + entry.getKey() + "\n";
        }
        return stuff;
    }

}
