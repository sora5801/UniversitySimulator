package UniversitySimulator.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Student {
    private String name;
    private double gpa;
    private HashSet<Book> bookLists;
    private double money; //Money to purchase things from the bookstore or cafeteria.
    private LinkedList<String> homework;
    private LinkedList<String> diary; // What the student has done today.
    private LinkedList<String> foodOrdered; // What food did the student order
    private LinkedList<String> classList; // Student's class list
    private final List<Double> credits = new ArrayList<>();
    private final List<Double> points = new ArrayList<>();

    public Student(String name) {
        this.foodOrdered = new LinkedList<>();
        this.name = name;
        this.money = 250;
        bookLists = new HashSet<>();
        bookLists.add(new Book("a book"));//serena

    }

    public String getName(){ return this.name; }
    public double getWallet(){ return this.money;}
    public void setWallet(double money){ this.money = money;}

    private double calculateGpa(Student student) {
        double totalCredits = getTotal(student.credits);
        double totPts = getTotal(student.points);
        double gpa = totPts / totalCredits;
        return gpa;
    }

    private double getTotal(List<Double> doubles) {
        double total = 0;
        for(Double d:doubles) {
            total += d;
        }
        return total;
    }

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
