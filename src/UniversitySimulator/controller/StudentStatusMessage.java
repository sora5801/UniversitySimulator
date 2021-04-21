package UniversitySimulator.controller;

public class StudentStatusMessage implements Message {
    String name;

    public StudentStatusMessage(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
