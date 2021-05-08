package UniversitySimulator.model;

import UniversitySimulator.controller.Message;
import UniversitySimulator.view.*;
import org.junit.*;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

/**
 * This is the Junit test class. This class will only test the Model package class methods
 */
public class ModelTest {

    /**
     * Test the Student Methods
     */
    @Test
    public void testStudentMethods(){
        Student student = new Student("John");
        assertEquals("John", student.getName());
        assertTrue(25000 == student.getWallet());

    }

    /**
     * Test the CafeteriaStrategy methods
     */
    @Test
    public void testCafeteriaStrategy(){
        Student student = new Student("John");
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        Cafeteria cafeteria = new Cafeteria(queue);
        CafeteriaStrategy cafeteriaStrategy = new CafeteriaStrategy(cafeteria);

        student.setCampusStrategy(cafeteriaStrategy);
        student.interactWithBuilding();
        assertTrue(25000 == cafeteriaStrategy.getMoneyAmount());
        assertTrue(cafeteriaStrategy.getInventory().isEmpty());
        assertTrue(cafeteriaStrategy.getBookList().isEmpty());
        assertTrue(!cafeteriaStrategy.getItemMoney().isEmpty());
    }

    /**
     * Test the BookStoreStrategy methods
     */
    @Test
    public void testBookStoreStrategy()  {
        Student student = new Student("John");
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        BookStore bookStore = new BookStore(queue);
        BookStoreStrategy bookStoreStrategy = new BookStoreStrategy(bookStore);

        student.setCampusStrategy(bookStoreStrategy);
        student.interactWithBuilding();
        assertTrue(25000 == bookStoreStrategy.getMoneyAmount());
        assertTrue(bookStoreStrategy.getInventory().isEmpty());
        assertTrue(bookStoreStrategy.getBookList().isEmpty());
        assertTrue(bookStoreStrategy.getItemMoney().isEmpty());
    }

    /**
     * Test the LibraryStrategy methods
     */
    @Test
    public void testLibraryStrategy(){
        Student student = new Student("John");
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        Library library = new Library(queue);
        LibraryStrategy libraryStrategy = new LibraryStrategy(library);

        student.setCampusStrategy(libraryStrategy);
        student.interactWithBuilding();
        assertTrue(25000 == libraryStrategy.getMoneyAmount());
        assertTrue(libraryStrategy.getInventory().isEmpty());
        assertTrue(libraryStrategy.getBookList().isEmpty());
        assertTrue(libraryStrategy.getItemMoney().isEmpty());
    }

    /**
     * Test the ClassroomStrategy methods
     */
    @Test
    public void testClassroomStrategy(){
        Student student = new Student("John");
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        Classroom classroom = new Classroom();
        ClassroomStrategy classroomStrategy = new ClassroomStrategy();

        student.setCampusStrategy(classroomStrategy);
        student.interactWithBuilding();
        assertTrue(25000 == classroomStrategy.getMoneyAmount());
        assertTrue(classroomStrategy.getInventory().isEmpty());
        assertTrue(classroomStrategy.getBookList().isEmpty());
        assertTrue(classroomStrategy.getItemMoney().isEmpty());
    }

}

