package UniversitySimulator.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Student {
    private String name;
    private HashSet<Book> bookLists;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private LinkedList<String> diary; // What the student has done today.
    private LinkedList<String> foodOrdered; // What food did the student order
    private HashMap<String, Double> itemMoney;
    private CampusStrategy campusStrategy;


    public Student(String name){
        this.itemMoney = new HashMap<>();
        this.campusStrategy = new MainCampusStrategy();
        this.foodOrdered = new LinkedList<>();
        this.name = name;
        this.money = 250;
        bookLists = new HashSet<>();
    }

    public void setCampusStrategy(CampusStrategy campusStrategy){
        this.campusStrategy = campusStrategy;
    }

    public void interactWithBuilding(){
         campusStrategy.interact(itemMoney, this.money);
         this.itemMoney = campusStrategy.getItemMoney();
         this.money = campusStrategy.getMoneyAmount();
    }

    public String getName(){ return this.name; }
    public double getWallet(){ return this.money;}

    public void addBooks(HashSet<Book> books){
        for(Book b: books){
            bookLists.add(b);
        }
    }

    public void returnBooks(){
        bookLists.clear();
    }

    public HashSet<Book> getBookLists(){
        return bookLists;
    }

    public void printBooks(){
        for(Book b: bookLists){
            System.out.println("book: " + b+"\n");
        }
    }

    public void writeDiary(String diaryEntry){
        this.diary.add(diaryEntry);
    }


    public void addFood(String food){
        this.foodOrdered.add(food);
    }

    public String getFood(){
        return foodOrdered.pop();
    }


}
