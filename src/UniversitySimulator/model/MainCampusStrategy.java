package UniversitySimulator.model;

import java.util.HashMap;

public class MainCampusStrategy implements CampusStrategy{
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;


    @Override
    public void interact(HashMap<String, Double> itemMoney, double money) {
        this.itemMoney = itemMoney;
        this.moneyAmount = money;
    }

    @Override
    public double getMoneyAmount() {
        return moneyAmount;
    }

    @Override
    public HashMap<String, Double> getItemMoney() {
        return this.itemMoney;
    }

}
