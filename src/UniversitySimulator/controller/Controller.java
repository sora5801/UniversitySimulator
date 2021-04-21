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
                // button updateStudentName was clicked
                NewBookMessage nameMessage = (NewBookMessage) message;
                studentModel.addBooks(nameMessage.getNewBooks()); // update model
                view.checkedOutMessage(studentModel.getName()); // update view
            }

            else if(message.getClass() == FoodOrderedMessage.class) {
                FoodOrderedMessage foodMessage = (FoodOrderedMessage) message;
                studentModel.addFood(foodMessage.getName());
                view.addOrderedMessage(studentModel.getFood());

            }

             else if(message.getClass() == StudentActionMessage.class){
                 StudentActionMessage actionMessage = (StudentActionMessage) message;
                 view.addActionMessage(actionMessage.getName());
             }

             else if (message.getClass() == StudentStatusMessage.class){
                 StudentStatusMessage statusMessage = (StudentStatusMessage) message;
                 view.addOrderedMessage(statusMessage.getName());
             }

        }
    }
}


