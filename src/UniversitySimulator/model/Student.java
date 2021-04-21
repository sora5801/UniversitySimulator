package UniversitySimulator.model;

import java.util.HashSet;
import java.util.LinkedList;

public class Student {
    private String name;
    private HashSet<Book> bookLists;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private LinkedList<String> diary; // What the student has done today.
    private LinkedList<String> foodOrdered; // What food did the student order

    public Student(String name){
        this.foodOrdered = new LinkedList<>();
        this.name = name;
        this.money = 250;
        bookLists = new HashSet<>();
        bookLists.add(new Book("a book"));//serena
    }

    public String getName(){ return this.name; }
    public double getWallet(){ return this.money;}
    public void setWallet(double money){ this.money = money;}

    public void spendMoney(double money){this.money -= money;}

    public void addBooks(HashSet<Book> books){
        for(Book b: books){
            bookLists.add(b);
        }
    }

    public HashSet<Book> getBookLists(){
        for(Book b: bookLists){
            System.out.println("book: " + b+"\n");
        }
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
