package UniversitySimulator.controller;

public class NewNameMessage implements Message {
    String name;

    public NewNameMessage(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }
}
