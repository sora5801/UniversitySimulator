package UniversitySimulator.model;

import javax.swing.*;
import UniversitySimulator.view.*;

import java.util.HashMap;

public class CafeteriaStrategy implements CampusStrategy{
    private Cafeteria cafeteria;
    private double orderPrice;
    private String lastOrder;
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;

    public CafeteriaStrategy(Cafeteria cafeteria){
        this.cafeteria = cafeteria;
    }

    @Override
    public double getMoneyAmount(){
        return this.moneyAmount;
    }

    @Override
    public HashMap<String, Double> getItemMoney(){
        return this.itemMoney;
    }

    @Override
    public void interact(HashMap<String, Double> itemMoney, double money) {
        this.itemMoney = itemMoney;
        this.moneyAmount = money;
        this.orderPrice = cafeteria.getOrders();
        this.lastOrder =cafeteria.lastOrder();
        this.itemMoney.put(lastOrder, orderPrice);
        if(moneyAmount >= this.orderPrice)
            moneyAmount -= this.orderPrice;
    }
}
