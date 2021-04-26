package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import UniversitySimulator.controller.*;
import UniversitySimulator.model.*;

//

/**
 * TODO Draw a frame that depicts the school cafeteria
 * This is a part of the view section
 * @Author Matthew
 */
public class Cafeteria extends JPanel {
    private BlockingQueue<Message> queue;
    private static final int rectangleWidth = 250;
    private static final int rectangleHeight = 150;
    private HashMap<String, Double> menu = new HashMap<>();
    private HashMap<Integer, String> menuItems = new HashMap<>();
    private HashMap<Integer, Integer> menuCount = new HashMap<>();
    private HashMap<Integer, Boolean> isOnReceipt = new HashMap<>();
    private double orders;
    private double total;
    private JTextArea textArea = new JTextArea();
    private JTextArea receiptArea = new JTextArea();
    private int itemAmount = 0;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private String lastOrder;
    ButtonGroup buttonGroup;

    public void paintComponent (Graphics g){
        g.drawString("Bricks Pizza", 760, 245);
        g.drawRect(750, 175, rectangleWidth, rectangleHeight);
        g.drawLine(830,175,830,325);
        g.drawArc(778, 25, 100, 300, 270,90);
        g.drawLine(880,175,880,325);
        g.drawString("We make good pizzas", 881, 245);

        g.drawRect(580, 365, rectangleWidth-100, rectangleHeight-100);
        g.drawLine(630,365,630,415);
        g.drawString("Food", 590, 395);
        g.drawLine(670,365,670,415);
        g.drawString("Menu", 675, 395);

        g.drawRect(510, 440, rectangleWidth, rectangleHeight-100);

        g.drawRect(350, 440, 45, 150);

        g.drawRect(275, 540, 40, 50);
        g.drawArc(275, 520, 40, 45, 0,180);

        g.drawArc(800, 370, 400, 45, 180,360);
        g.drawLine(810, 400, 810, 455);
        g.drawLine(1175, 401, 1175, 459);
        g.drawArc(800, 427, 400, 45, 180,360);

    }





    public Cafeteria(BlockingQueue<Message> queue){
        this.queue = queue;
        initializeMenu();
        ActionListener listener = new AddInterestListener();
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        radio1 = new JRadioButton("Menu and order");
        radio2 = new JRadioButton("Receipt");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radio1);
        buttonGroup.add(radio2);

        textArea.append(displayMenu());
        textArea.append("\n What would you like to order?");
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));

        receiptArea.setFont(new Font("monospaced", Font.PLAIN, 12));

        JButton button = new JButton("User Input");
        button.addActionListener(listener);

        add(radio1);
        add(radio2);
        add(button, BorderLayout.EAST);

        if(!isVisible()){
            System.out.println("Gone");
            clearAll();
        }
    }

    public String displayMenu(){
        int count = 1;
        final int dashedLines = 100;
        final int spaces = 15;
        String bars = "";
        LinkedList<String> items = new LinkedList<>();
        LinkedList<Double> price = new LinkedList<>();
        LinkedList<Integer> number = new LinkedList<>();

        bars = spacePad(bars, spaces);
        bars = dashPad(bars, dashedLines);
        bars+="\n";
        bars = spacePad(bars, 3 * spaces);
        bars += "Menu";
        bars = spacePad(bars, spaces);
        bars+="\n";
        bars = spacePad(bars, spaces);
        bars = dashPad(bars, dashedLines);
        bars += "\n";
        for(HashMap.Entry<String, Double> entry: menu.entrySet()){
            items.add(entry.getKey());
            price.add(entry.getValue());
            number.add(count);
            count++;
        }

        for(int i = 0; i < items.size(); i++){
            bars += String.format("%10d. %5s", number.get(i), items.get(i));
            int length = 110 - String.format("%10d. %s", number.get(i), items.get(i)).length();
            bars = periodPad(bars, length);
            bars += String.format("$%.2f%n", price.get(i));
              }

        bars = spacePad(bars, spaces);
        bars = dashPad(bars, dashedLines);
        bars += "\n";

        return bars;
    }

    private String periodPad(String s, int n){
        for (int i = 0; i < n; i++)
            s += ".";
        return s;
    }

    private String spacePad(String s, int n){
        for (int i = 0; i < n; i++)
            s += " ";
        return s;
    }

    private String dashPad(String s, int n){
        for (int i = 0; i < n; i++)
            s += "-";
        return s;
    }

    class AddInterestListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(radio1.isSelected()) {
                String order=
                        JOptionPane.showInputDialog(null,
                                textArea, "Menu", JOptionPane.INFORMATION_MESSAGE);
                orders = sellFood(Integer.parseInt(order));
                if(!order.equals("") && orders >= 1 && orders <= 12) {
                    menuCount.put(Integer.parseInt(order), menuCount.get(Integer.parseInt(order)) + 1);
                    isOnReceipt.replace(Integer.parseInt(order), true);
                    lastOrder = menuItems.get(Integer.parseInt(order));


                    try {
                        Message msg = new FoodOrderedMessage(lastOrder);
                        queue.put(msg);
                    } catch (InterruptedException e) {
                    }
                }
                else{
                    try {
                        Message msg = new FoodErrorMessage();
                        queue.put(msg);
                    } catch (InterruptedException e) {
                    }
                }
            }
            if(radio2.isSelected()){
                String studentOrders = "";
                total = 0;
                for(HashMap.Entry<Integer, Integer> entry: menuCount.entrySet()){
                    if(entry.getValue() > 0 && isOnReceipt.get(entry.getKey())) {
                        int length = 70 - (entry.getValue() +"x  " + menuItems.get(entry.getKey())).length();
                        studentOrders += entry.getValue() +"x  " + menuItems.get(entry.getKey());
                        studentOrders = periodPad(studentOrders, length);
                        studentOrders += String.format("%.2f", menu.get(menuItems.get(entry.getKey()))
                                * entry.getValue());
                        total += menu.get(menuItems.get(entry.getKey())) * entry.getValue();
                        studentOrders += "\n";
                    }
                }
                if(total != 0) {
                    studentOrders += "Total ";
                    studentOrders = periodPad(studentOrders, 63);
                    studentOrders += "$" + String.format("%.2f", total);
                }
                receiptArea.setText(studentOrders);
                JOptionPane.showMessageDialog(null, receiptArea, "Receipt",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public double getOrders(){
        return orders;
    }

    public String lastOrder(){
        return lastOrder;
    }

    public void clearAll(){
        this.total = 0;
        int count = 1;
        for(HashMap.Entry<String, Double> entry: menu.entrySet()) {
            menuCount.replace(count, 0);
            count++;
        }
    }

    public void initializeMenu(){
        menu.put("AppleWood Bacon and Black Olives Pizza", 7.50);
        menu.put("Sliced Genoa Salami and Roasted Garlic Pizza", 7.00);
        menu.put("New England Clam Chowder", 6.00);
        menu.put("Pasta Primavera", 6.00);
        menu.put("Spinach Ravioli with Pesto Sauce", 6.00);
        menu.put("Pot Pie", 6.00);
        menu.put("Lasagna", 6.50);
        menu.put("Pesto Chicken & Bow Tie Pasta with Roasted Squash", 7.00);
        menu.put("Chicken Tikka", 6.50);
        menu.put("Spaghetti and MeatBalls", 6.50);
        menu.put("Avocado Sandwich", 5.50);
        menu.put("Cajun Chicken Benedict", 6.00);

        int count = 1;
        for(HashMap.Entry<String, Double> entry: menu.entrySet()){
            menuItems.put(count,entry.getKey());
            menuCount.put(count, 0);
            isOnReceipt.put(count, false);
            count++;
        }
    }


    public double sellFood(int i){
        switch (i){
            case 1:
                return menu.get("Avocado Sandwich");
            case 2:
                return menu.get("Cajun Chicken Benedict");
            case 3:
                return menu.get("Spinach Ravioli with Pesto Sauce");
            case 4:
                return menu.get("Chicken Tikka");
            case 5:
                return menu.get("Lasagna");
            case 6:
                return menu.get("Pesto Chicken & Bow Tie Pasta with Roasted Squash");
            case 7:
                return menu.get("Sliced Genoa Salami and Roasted Garlic Pizza");
            case 8:
                return menu.get("New England Clam Chowder");
            case 9:
                return menu.get("Pasta Primavera");
            case 10:
                return menu.get("Spaghetti and MeatBalls");
            case 11:
                return menu.get("AppleWood Bacon and Black Olives Pizza");
            case 12:
                return menu.get("Pot Pie");

        }
        return 0;
    }

}


