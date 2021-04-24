package UniversitySimulator.model;

import java.util.HashMap;

public class ClassroomStrategy implements CampusStrategy{
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;

    @Override
    public void interact(HashMap<String, Double> itemMoney, double money) {
        this.moneyAmount = money;
        this.itemMoney = itemMoney;
    }

    @Override
    public double getMoneyAmount() {
        return this.moneyAmount;
    }

    @Override
    public HashMap<String, Double> getItemMoney() {
        return this.itemMoney;
    }
}
