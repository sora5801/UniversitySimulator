package UniversitySimulator.controller;

/**
 * This is the class that gets called everything the user wants to check on the student's status
 */
public class StudentStatusMessage implements Message {
    String name;

    /**
     * Construct the class
     * @param name name of the message
     */
    public StudentStatusMessage(String name) {
        this.name = name;
    }

    /**
     * Get the student's status
     * @return student status
     */
    public String getName(){
        return name;
    }
}
