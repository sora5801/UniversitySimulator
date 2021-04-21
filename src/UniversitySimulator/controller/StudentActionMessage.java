package UniversitySimulator.controller;

public class StudentActionMessage implements Message {
    String name;

    public StudentActionMessage(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }
}
