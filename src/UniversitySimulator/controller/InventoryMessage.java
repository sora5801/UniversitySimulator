package UniversitySimulator.controller;

public class InventoryMessage implements Message{
    String name;

    public InventoryMessage(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }
}
