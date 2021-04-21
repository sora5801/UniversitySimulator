package UniversitySimulator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import UniversitySimulator.controller.*;
import UniversitySimulator.model.*;
import UniversitySimulator.view.*;

//
public class UniversitySimulator {
    public static void main(String[] args) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

        Student model = new Student("John");
        UniversityCampusFrame campusFrame = new UniversityCampusFrame(queue);
        Controller controller = new Controller(queue, model, campusFrame);

        controller.mainLoop();
    }
}
