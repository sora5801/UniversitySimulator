package UniversitySimulator.controller;

import java.util.concurrent.BlockingQueue;

import java.util.LinkedList;
import java.util.List;
import UniversitySimulator.model.*;
import UniversitySimulator.view.UniversityCampusFrame;


/**
 * This is the controller class. This is where all user interactions are executed.
 * In addition, this controller class uses the command pattern in the form of private valve classes.
 * @author Matthew Fu, Shiting Li, Nam Ta, Dias Mustafin
 */
public class Controller {
    BlockingQueue<Message> queue;
    Student studentModel; //The model
    UniversityCampusFrame view; //The view
    private List<Valve> valves = new LinkedList<Valve>();

    /**
     * This is the constructor. This is where the queue, mode, and view objects are passed in. This is also where
     * the valves are added.
     * @param queue the blocking queue used to receive messages from view
     * @param studentModel the model object that will be altered with every command
     * @param view the view class that displays the changes to the model.
     */
    public Controller(BlockingQueue<Message> queue, Student studentModel, UniversityCampusFrame view) {
        this.queue = queue;
        this.studentModel = studentModel;
        this.view = view;

        valves.add(new DoFoodOrderedValve());
        valves.add(new DoNewBookValve());
        valves.add(new DoStudentActionValve());
        valves.add(new DoReturnBookValve());
        valves.add(new DoStudentStatusValve());
        valves.add(new DoFoodErrorValve());
        valves.add(new DoBuyItemValve());
    }

    /**
     * This is the main loop that will execute all chains of command
     */
    public void mainLoop() {
        ValveResponse response = ValveResponse.EXECUTED;
        Message message = null;
        while (response != ValveResponse.FINISH) {
            try {
                message = queue.take(); // <--- take next message from the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Look for a Valve that can process a message
            for (Valve valve : valves) {
                response = valve.execute(message);
                // if successfully processed or game over, leave the loop
                if (response != ValveResponse.MISS) {
                    break;
                }
            }
        }

    }

    /**
     * This is the valve that tells that food has been ordered.
     */
    private class DoFoodOrderedValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != FoodOrderedMessage.class){
                return ValveResponse.MISS;
            }
            FoodOrderedMessage foodMessage = (FoodOrderedMessage) message;
            studentModel.interactWithBuilding();
            view.addOrderedMessage(foodMessage.getName());
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve that tells that items has been bought
     */
    private class DoBuyItemValve implements Valve {
        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != ItemBoughtMessage.class) {
                return ValveResponse.MISS;
            }
            studentModel.interactWithBuilding();
            ItemBoughtMessage itemBoughtMessage = (ItemBoughtMessage) message;
            view.addItemMessage(itemBoughtMessage.getItems());
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve that tells that books has been checked out.
     */
    private class DoNewBookValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != NewBookMessage.class){
                return ValveResponse.MISS;
            }
            NewBookMessage nameMessage = (NewBookMessage) message;
            view.checkedOutMessage(nameMessage.getNewBooks(), studentModel.getBookLists());
            studentModel.interactWithBuilding(); //I don't like that I have to put this here, but it can't be helped.
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve that tells that a student has went to a new location.
     */
    private class DoStudentActionValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != StudentActionMessage.class){
                return ValveResponse.MISS;
            }
            StudentActionMessage actionMessage = (StudentActionMessage) message;
            if(actionMessage.getName().equals("Campus")){
                studentModel.setCampusStrategy(new MainCampusStrategy());
            }
            if(actionMessage.getName().equals("Cafeteria")){
                studentModel.setCampusStrategy(new CafeteriaStrategy(view.getCafeteria()));
            }
            if(actionMessage.getName().equals("Bookstore")){
                studentModel.setCampusStrategy(new BookStoreStrategy(view.getBookStore()));
            }
            if(actionMessage.getName().equals("Library")){
                studentModel.setCampusStrategy(new LibraryStrategy(view.getLibrary()));
            }
            if(actionMessage.getName().equals("Classroom")){
                studentModel.setCampusStrategy(new ClassroomStrategy());
            }
            view.addActionMessage(actionMessage.getName());
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve that tells that books has been returned.
     */
    private class DoReturnBookValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != ReturnBookMessage.class) {
                return ValveResponse.MISS;
            }
            studentModel.returnBooks(); // update model
            view.returnBooksMessage(); // update view
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve that tells that the user wants to know what the student's status are.
     */
    private class DoStudentStatusValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if (message.getClass() != StudentStatusMessage.class){
                return ValveResponse.MISS;
            }
            StudentStatusMessage statusMessage = (StudentStatusMessage) message;
            if(statusMessage.getName().equals("Wallet")){
                view.addWalletMessage(studentModel.getWallet());
            }
            if(statusMessage.getName().equals("Name")){
                view.addNameMessage(studentModel.getName());
            }
            if(statusMessage.getName().equals("Books")){
                view.addBooksMessage(studentModel.getBookLists());
            }
            if(statusMessage.getName().equals("Inventory")){
                view.addInventoryMessage(studentModel.getInventory());
            }
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve that tells that the user has given an invalid food input.
     */
    private class DoFoodErrorValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != FoodErrorMessage.class) {
                return ValveResponse.MISS;
            }
                FoodErrorMessage foodErrorMessage = (FoodErrorMessage) message;
                view.addErrorMessage(message.getName());
            return ValveResponse.EXECUTED;
        }
    }

    /**
     * This is the valve interface that is used to implement the command pattern in the controller.
     */
    private interface Valve {
        /**
         * Performs certain action in response to message
         */
        public ValveResponse execute(Message message);
    }
}


