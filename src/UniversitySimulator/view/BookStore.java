package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import UniversitySimulator.controller.Message;
import UniversitySimulator.controller.ItemBoughtMessage;
//

/***
 * This is the bookstore. Here is where the student may purchase new items
 * @author Nam Ta
 */
public class BookStore extends JPanel {
    BlockingQueue<Message> queue;
    LinkedList<String> Items;
    JLabel supplyLabel, apparelLabel, softwareLabel;
    JCheckBox item1, item2, item3, item4, item5, item6, item7, item8, item9;
    JButton checkoutButton;
    double totalPrice;
    String lastOrder;


    /**
     * This is the constructor to the bookstore. This initializes all the bookstore items
     * as well as their checkboxes and prices. It also adds the button actions to the
     * purchase button
     * @param queue This is the blocking queue that is accepted.
     */
    public BookStore(BlockingQueue<Message> queue) {
        this.queue = queue;
        setLayout(null);
        this.Items = new LinkedList<>();
        supplyLabel = new JLabel("Supply:");
        supplyLabel.setBounds(20, 20, 150, 30);
        item1 = new JCheckBox("Notebook        $4.99");
        item1.setBounds(20, 50, 150, 30);
        item2 = new JCheckBox("Pencil set       $9.99");
        item2.setBounds(20, 80, 150, 30);
        item3 = new JCheckBox("Calculator    $39.99");
        item3.setBounds(20, 110, 150, 30);

        apparelLabel = new JLabel("Apparel:");
        apparelLabel.setBounds(20, 140, 150, 30);
        item4 = new JCheckBox("SJSU T-Shirt   $19.99");
        item4.setBounds(20, 170, 150, 30);
        item5 = new JCheckBox("SJSU Jogger  $29.99");
        item5.setBounds(20, 200, 150, 30);
        item6 = new JCheckBox("SJSU Hoodie  $34.99");
        item6.setBounds(20, 230, 150, 30);

        softwareLabel = new JLabel("Software:");
        softwareLabel.setBounds(20, 260, 150, 30);
        item7 = new JCheckBox("Software 1     $79.99");
        item7.setBounds(20, 290, 150, 30);
        item8 = new JCheckBox("Software 2     $89.99");
        item8.setBounds(20, 320, 150, 30);
        item9 = new JCheckBox("Software 3     $99.99");
        item9.setBounds(20, 350, 150, 30);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(20, 390, 150, 30);
        checkoutButton.addActionListener(e -> {
            try {
                clickPurchase();
                queue.put(new ItemBoughtMessage(Items));
            } catch (InterruptedException exception) {
                // do nothing
            }
        });

        add(supplyLabel);
        add(item1);
        add(item2);
        add(item3);
        add(apparelLabel);
        add(item4);
        add(item5);
        add(item6);
        add(softwareLabel);
        add(item7);
        add(item8);
        add(item9);
        add(checkoutButton);
        setSize(400, 400);

    }

    /**
     * This method displays the Item purchased by the user and pops up a message.
     */
    public void clickPurchase() {
        totalPrice = 0;
        int count = 0;
        String message = "Items purchased:";
        Items = new LinkedList<String>();
        if (item1.isSelected()) {
            count++;
            totalPrice += 4.99;
            message += "\nNotebook";
            Items.add("Notebook");
        }
        if (item2.isSelected()) {
            count++;
            totalPrice += 9.99;
            message += "\nPencil set";
            Items.add("Pencil set");
        }
        if (item3.isSelected()) {
            count++;
            totalPrice += 39.99;
            message += "\nCalculator";
            Items.add("Calculator");
        }
        if (item4.isSelected()) {
            count++;
            totalPrice += 19.99;
            message += "\nSJSU T-Shirt";
            Items.add("SJSU T-Shirt");
        }
        if (item5.isSelected()) {
            count++;
            totalPrice += 29.99;
            message += "\nSJSU Jogger";
            Items.add("SJSU Jogger");
        }
        if (item6.isSelected()) {
            count++;
            totalPrice += 34.99;
            message += "\nSJSU Hoodie";
            Items.add("SJSU Hoodie");
        }
        if (item7.isSelected()) {
            count++;
            totalPrice += 79.99;
            message += "\nSoftware 1";
            Items.add("Software 1");
        }
        if (item8.isSelected()) {
            count++;
            totalPrice += 89.99;
            message += "\nSoftware 2";
            Items.add("Software 2");
        }
        if (item9.isSelected()) {
            count++;
            totalPrice += 99.99;
            message += "\nSoftware 3";
            Items.add("Software ");
        }
        message += "\n----------\nTotal items: " + Integer.toString(count) + "\nTotal price: " + Double.toString(totalPrice) + "\n";
        JOptionPane.showMessageDialog(checkoutButton, message);
    }

    /**
     * Draws a frame of the bookstore.
     * @param g the frame
     */
    public void paintComponent(Graphics g) {
    }

    /**
     * Gets the total price of the items
     * @return totalPrice
     */
    public double getTotalPrice(){
        return totalPrice;
    }

    /**
     * Gets the list of items bought
     * @return All the items
     */
    public LinkedList<String> getItems(){
        return this.Items;
    }
}


