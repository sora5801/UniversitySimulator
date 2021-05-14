package UniversitySimulator.view;

import UniversitySimulator.controller.Message;
import UniversitySimulator.controller.SubmitMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;

//

/***
 * Source for PanelHolder:
 * https://stackoverflow.com/questions/2510159
 * /can-i-add-a-component-to-a-specific-grid-cell-when-a-gridlayout-is-used/38800227
 * This is the Classroom
 * @author Dias Mustafin
 */
public class Classroom extends JPanel {
    private static final int rectangleWidth = 850;
    private static final int rectangleHeight = 600;

    private BlockingQueue<Message> queue;

    private JTabbedPane tabbedPane;
    private JComponent classPanel1;
    private JComponent classPanel2;
    private JComponent classPanel3;
    private JComponent classPanel4;


    public Classroom(BlockingQueue<Message> queue) {
        super(new GridLayout(1,1 ));
        this.queue = queue;

        tabbedPane = new JTabbedPane();

        classPanel1 = makeClassPanel("PHILOSOPHY");
        tabbedPane.addTab("Class 1", classPanel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        classPanel2 = makeClassPanel("PHYSICS");
        tabbedPane.addTab("Class 2", classPanel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        classPanel3 = makeClassPanel("COMPUTER SCIENCE");
        tabbedPane.addTab("Class 3", classPanel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        classPanel4 = makeClassPanel("ETHICS");
        tabbedPane.addTab("Class 4", classPanel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.add(tabbedPane);
        this.setSize(rectangleWidth, rectangleHeight);
        this.setVisible(true);
    }

    protected JComponent makeClassPanel(String name) {
        JPanel panel = new JPanel(false);
        JLabel className = new JLabel(name);
        className.setFont(new Font("Verdana", Font.PLAIN, 25));
        className.setPreferredSize(new Dimension(250, 100));
        className.setVerticalAlignment(JLabel.TOP);

        JTabbedPane classTabbedPane = new JTabbedPane();
        JComponent assignment = new JPanel();
        classTabbedPane.addTab("Assignments", assignment);
        //tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

        JComponent lecture = new JPanel();
        classTabbedPane.addTab("Lectures", lecture);
        //tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);

        JComponent quiz = new JPanel();
        JComponent test = new JPanel();
        JComponent grade = new JPanel();

        JButton q = new JButton("quiz");

        q.addActionListener(e -> {
            Quiz newQuiz = new Quiz(1);
        });


        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        panel.setLayout(new GridLayout(1,1));
        panel.add(className);
        panel.add(classTabbedPane);
        panel.add(q);
        return panel;
    }

    public class Quiz extends JFrame implements ActionListener {
        JLabel question;
        JRadioButton jRadioButton[] = new JRadioButton[4];
        ButtonGroup buttonGroup;
        JButton submit;
        JComponent questionPanel1 = new JPanel();

        public Quiz(int quizNumber) {
            int i2 = 3;
            int j = 3;
          //  setLayout(new CardLayout());
            JPanel quizPagePanel = new JPanel();
            JPanel[][] panelHolder = new JPanel[i2][j];
            GridLayout layout = new GridLayout(i2, j);
            quizPagePanel.setLayout(layout);
            setLayout(layout);

            for(int m = 0; m < i2; m++) {
                for(int n = 0; n < j; n++) {
                    panelHolder[m][n] = new JPanel();
                    add(panelHolder[m][n]);
                }
            }
            questionPanel1.setLayout(new BoxLayout(questionPanel1, BoxLayout.Y_AXIS));
           // setLayout(new BorderLayout());
            //quizName = new JLabel("Quiz " + quizNumber);
            question = new JLabel();
            buttonGroup = new ButtonGroup();
            for(int i = 0; i < 4; i++) {
                jRadioButton[i] = new JRadioButton();
                add(jRadioButton[i]);
                buttonGroup.add(jRadioButton[i]);
            }
            submit = new JButton("Submit");
            /*submit.addActionListener(e -> {
                try {
                    Message message = new SubmitMessage();
                    queue.put(message);
                } catch(InterruptedException exception) {

                }
            });*/
            set();
            panelHolder[0][1].add(question);
            panelHolder[1][0].setLayout(new BorderLayout());
            panelHolder[1][0].add(questionPanel1, BorderLayout.WEST);
            panelHolder[2][1].add(submit);

            setSize(600,800);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == submit) {

            }
        }

        public void set() {
            question.setText("question 1");
            questionPanel1.add(question);
            jRadioButton[0].setText("1");
            jRadioButton[1].setText("2");
            jRadioButton[2].setText("3");
            jRadioButton[3].setText("4");

            for(int i =0; i < 4; i++)
                questionPanel1.add(jRadioButton[i]);

            question.setBounds(10,40,450,20);
            for(int i=0,j=0;i<=90;i+=30,j++)
                jRadioButton[j].setBounds(50,80+i,200,20);

        }

    }



    /**
     * A frame of a classroom will be drawn here
     * @param g
     */
   /* public void paintComponent (Graphics g){
        super.paintComponent(g);
        //g.drawString(className, 10, 10);
        g.drawRect(100, 50, rectangleWidth, rectangleHeight);

    }*/
}


