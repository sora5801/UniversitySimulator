package UniversitySimulator.controller;

import java.util.concurrent.BlockingQueue;

import java.util.LinkedList;
import java.util.List;
import UniversitySimulator.model.*;
import UniversitySimulator.view.UniversityCampusFrame;

//

/**
 * This will be Controller section
 */
public class Controller {
    BlockingQueue<Message> queue;
    Student studentModel; //The model
    UniversityCampusFrame view; //The view
    private List<Valve> valves = new LinkedList<Valve>();

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
    }

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
        /*
        while (view.isDisplayable()) {
            Message message = null;
            try {
                message = queue.take();
            } catch (InterruptedException exception) {
                // do nothing
            }

            if(message.getClass() == NewBookMessage.class) {
                NewBookMessage nameMessage = (NewBookMessage) message;
                studentModel.addBooks(nameMessage.getNewBooks()); // update model
                view.checkedOutMessage(studentModel.getBookLists()); // update view
            }

            else if(message.getClass() == ReturnBookMessage.class) {
                studentModel.returnBooks(); // update model
                view.returnBooksMessage(); // update view
            }

            else if(message.getClass() == FoodOrderedMessage.class) {
                FoodOrderedMessage foodMessage = (FoodOrderedMessage) message;
                studentModel.interactWithBuilding();
                studentModel.addFood(foodMessage.getName());
                view.addOrderedMessage(studentModel.getFood());

            }

            else if(message.getClass() == FoodErrorMessage.class){
                FoodErrorMessage foodErrorMessage = (FoodErrorMessage) message;
                view.addErrorMessage(message.getName());
            }

            else if(message.getClass() == StudentActionMessage.class){
                StudentActionMessage actionMessage = (StudentActionMessage) message;
                if(actionMessage.getName().equals("Campus")){
                    this.studentModel.setCampusStrategy(new MainCampusStrategy());
                }
                if(actionMessage.getName().equals("Cafeteria")){
                    this.studentModel.setCampusStrategy(new CafeteriaStrategy(view.getCafeteria()));
                }
                if(actionMessage.getName().equals("Bookstore")){
                    this.studentModel.setCampusStrategy(new BookStoreStrategy());
                }
                if(actionMessage.getName().equals("Library")){
                    this.studentModel.setCampusStrategy(new LibraryStrategy());
                }
                if(actionMessage.getName().equals("Classroom")){
                    this.studentModel.setCampusStrategy(new ClassroomStrategy());
                }
                view.addActionMessage(actionMessage.getName());
            }

            else if (message.getClass() == StudentStatusMessage.class){
                StudentStatusMessage statusMessage = (StudentStatusMessage) message;
                if(statusMessage.getName().equals("Wallet")){
                    view.addWalletMessage(this.studentModel.getWallet());
                }
                if(statusMessage.getName().equals("Name")){
                    view.addNameMessage(this.studentModel.getName());
                }
                if(statusMessage.getName().equals("Books")){
                    view.addBooksMessage(studentModel.getBookLists());
                }
            }

*/

    }

    private class DoFoodOrderedValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != FoodOrderedMessage.class){
                return ValveResponse.MISS;
            }
            FoodOrderedMessage foodMessage = (FoodOrderedMessage) message;
            studentModel.interactWithBuilding();
            studentModel.addFood(foodMessage.getName());
            view.addOrderedMessage(studentModel.getFood());
            return ValveResponse.EXECUTED;
        }
    }

    private class DoNewBookValve implements Valve{

        @Override
        public ValveResponse execute(Message message) {
            if(message.getClass() != NewBookMessage.class){
                return ValveResponse.MISS;
            }
            NewBookMessage nameMessage = (NewBookMessage) message;
            studentModel.addBooks(nameMessage.getNewBooks()); // update model
            view.checkedOutMessage(studentModel.getBookLists());
            return ValveResponse.EXECUTED;
        }
    }

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
                studentModel.setCampusStrategy(new BookStoreStrategy());
            }
            if(actionMessage.getName().equals("Library")){
                studentModel.setCampusStrategy(new LibraryStrategy());
            }
            if(actionMessage.getName().equals("Classroom")){
                studentModel.setCampusStrategy(new ClassroomStrategy());
            }
            view.addActionMessage(actionMessage.getName());
            return ValveResponse.EXECUTED;
        }
    }

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
            return ValveResponse.EXECUTED;
        }
    }

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


    private interface Valve {
        /**
         * Performs certain action in response to message
         */
        public ValveResponse execute(Message message);
    }
}


