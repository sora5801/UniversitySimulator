package UniversitySimulator.controller;

import java.util.concurrent.BlockingQueue;

import UniversitySimulator.model.*;
import UniversitySimulator.view.*;

//

/**
 * This will be Controller section
 */
public class Controller {
    BlockingQueue<Message> queue;
    Student studentModel; //The model
    UniversityCampusFrame view; //The view

    public Controller(BlockingQueue<Message> queue, Student studentModel, UniversityCampusFrame view) {
        this.queue = queue;
        this.studentModel = studentModel;
        this.view = view;
    }

    public void mainLoop() {
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
            }

        }
    }
}


