package UniversitySimulator.controller;

/**
 * This passes the message to the controller that the user wants to know their name
 */
public class NewNameMessage implements Message {
    String name;

    /**
     * Constructor
     * @param str
     */
    public NewNameMessage(String str) {
        this.name = str;
    }

    /**
     * Gets the name of the student.
     * @return
     */
    public String getName() {
        return name;
    }
}
