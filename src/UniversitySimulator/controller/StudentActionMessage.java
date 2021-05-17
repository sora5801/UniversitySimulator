package UniversitySimulator.controller;

/**
 * This is the class that is called whenever the student goes to a new location
 */
public class StudentActionMessage implements Message {
    String name;

    /**
     * Constructs the class
     * @param str name of the location
     */
    public StudentActionMessage(String str) {
        this.name = str;
    }

    /**
     * Gets the name of the location of the student that the student went
     * @return location
     */
    public String getName() {
        return name;
    }
}
