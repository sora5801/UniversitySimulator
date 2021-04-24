package UniversitySimulator.model;

import java.util.HashMap;

public interface CampusStrategy {
    public void interact(HashMap<String, Double> itemMoney, double money);
    public double getMoneyAmount();
    public HashMap<String, Double> getItemMoney();
}
