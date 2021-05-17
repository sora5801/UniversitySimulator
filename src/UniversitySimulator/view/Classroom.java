package UniversitySimulator.view;

import UniversitySimulator.controller.Message;
import UniversitySimulator.model.ClassroomStrategy;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
 * This is the Classroom class. Here, the student is capable of attending lectures and do quiz assignments
 * @author Dias Mustafin
 */
public class Classroom extends JPanel implements ListSelectionListener {
    private static final int RECTANGLE_WIDTH = 1200;
    private static final int RECTANGLE_HEIGHT = 1000;
    private BlockingQueue<Message> queue;

    private JTabbedPane tabbedPane;
    private JComponent classPanel1;
    private JComponent classPanel2;
    private JComponent classPanel3;
    private JComponent classPanel4;
    private JComponent sectionPanel;
    /**
     * This is the Constructor for the classroom. This initializes all the classes, assignments
     * and quizzes
     * @param queue The blocking queue to send messages to controller
     */
    public Classroom(BlockingQueue<Message> queue) {
        super(new GridLayout(1,1 )); // sets a layout as a single rectangle(one row and one column)
        this.queue = queue;

        tabbedPane = new JTabbedPane();

        classPanel1 = makeLecturePanel("PHILOSOPHY");
        tabbedPane.addTab("Lecture", classPanel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        classPanel2 = makeHWPanel();
        tabbedPane.addTab("Homework", classPanel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        classPanel3 = makeTestPanel();
        tabbedPane.addTab("Test", classPanel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        classPanel4 = makeClassPanel("ETHICS");
        tabbedPane.addTab("Grades", classPanel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        //tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // no need to add this feature since tabs have enough space
        this.add(tabbedPane);
        this.setSize(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        //this.setVisible(true);
    }

    /**
     * This class creates a class panel for the Classroom page.
     * @param name Name of the class
     * @return The JComponent of the class panels
     */
    protected JComponent makeClassPanel(String name) {
        JPanel classPanel = new JPanel(false); //?
        JLabel className = new JLabel(name);
        className.setFont(new Font("Verdana", Font.PLAIN, 25));

        String[] subSections = {"Lectures", "Homework", "Test", "Grades"};
        JList subSectionsList = new JList(subSections);
        subSectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subSectionsList.setSelectedIndex(0);
        //subSectionsList.addListSelectionListener(this::valueChanged);

        JComponent panel = makeHWPanel();
        //sectionPanel.add(className);
        sectionPanel = new JPanel();

        //JList panelList = new JList(new ListListModel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, subSectionsList, sectionPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);
        subSectionsList.setMaximumSize(new Dimension(100, 1000));
        sectionPanel.setMaximumSize(new Dimension(1100, 1000));
        splitPane.setPreferredSize(new Dimension(1200,1000));


        JTabbedPane classTabbedPane = new JTabbedPane();
        JComponent assignment = new JPanel();
        classTabbedPane.addTab("Assignments", assignment);
        classTabbedPane.setEnabledAt(0, false);
        //tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);


        JButton q = new JButton("quiz");
        JButton showAssignmentsTab = new JButton("A");

        showAssignmentsTab.addActionListener(e -> {
            classTabbedPane.setEnabledAt(0, true);
        });

        q.addActionListener(e -> {

        });


        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        //panel.setLayout(new GridLayout(1,1));
        //panel.add(classTabbedPane);
        classPanel.add(splitPane);
        return classPanel;
    }

    /**
     * This is the action class for value changed on the homework.
     * @param e The action
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        int min = lsm.getMinSelectionIndex();
        int max = lsm.getMaxSelectionIndex();

        for(int i = min; i <= max; i++) {
            if (lsm.isSelectedIndex(0)) {
                sectionPanel = makeHWPanel();
            }

            if (lsm.isSelectedIndex(1)) {
                //sectionPanel = makeLecturePanel();
            }

            if (lsm.isSelectedIndex(2)) {

            }

            if (lsm.isSelectedIndex(3)) {

            } else {

            }
        }
        //updateSectionPanel((JPanel) subSectionPanels[list.getSelectedIndex()]);
    }

    /**
     * Returns section panel
     * @param panel The panel returned
     */
    protected void updateSectionPanel(JPanel panel) {
        sectionPanel = new JPanel();
        sectionPanel.removeAll();

        JLabel label = new JLabel("gdf");
        sectionPanel.add(label);
    }

    /**
     * This creates the lecture panel for the philosophy class
     * @param name the name of the class
     * @return JComponent for Lecture Panel
     */
    protected JComponent makeLecturePanel(String name) {
        JPanel lecturePanel = new JPanel();
        JLabel className = new JLabel(name);
        JTextArea lectureMaterial = new JTextArea("Philosophy is the study of general and fundamental questions,\n" +
                "such as those about reason, existence, knowledge, values, mind, and language. Such questions are often posed as problems to be studied or resolved.\n" +
                "The term was probably coined by Pythagoras (c. 570 – c. 495 BCE).\n" +
                "Philosophical methods include questioning, critical discussion, rational argument, and systematic presentation. Historically, philosophy encompassed all bodies of knowledge and a practitioner was known as a philosopher.\n" +
                "From the time of Ancient Greek philosopher Aristotle to the 19th century, \"natural philosophy\" encompassed astronomy, medicine, and physics.\n" +
                "For example, Newton's 1687 Mathematical Principles of Natural Philosophy later became classified as a book of physics.\n");
        lecturePanel.add(className);
        lecturePanel.add(lectureMaterial);
        //lectureMaterial.setLayout(new BoxLayout(lecturePanel, BoxLayout.Y_AXIS));
        return lecturePanel;
    }

    /**
     * This creates a HW Panel for the philosophy class
     * @return JComponent for homework
     */
    protected JComponent makeHWPanel() {
        JPanel hwPanel = new JPanel();
        JLabel hw1 = new JLabel("Homework 1: Describe one of the works of Aristotle.");
        JLabel hw2 = new JLabel("Homework 2: How philosophy did become the subject of interest in Ancient Greece?");
        JLabel hw3 = new JLabel("Homework 3: Essay: The importance of philosophy in modern life(500 words).");
        JLabel hw4 = new JLabel("Homework 4: The most influential philosopher in your opinion and why?");
        JLabel hw5 = new JLabel("Homework 5: The difference between ancient philosophy and contemporary.");

        hwPanel.add(hw1);
        hwPanel.add(hw2);
        hwPanel.add(hw3);
        hwPanel.add(hw4);
        hwPanel.add(hw5);

        hwPanel.setLayout(new BoxLayout(hwPanel, BoxLayout.Y_AXIS));
        return hwPanel;
    }

    /**
     * This functions creates a Test Panel for the philosophy panel
     * @return JComponent for Test Panel
     */
    protected JComponent makeTestPanel() {
        JPanel testPanel = new JPanel();
        JLabel question1 = new JLabel("What is philosophy?");
        /*JRadioButton jRadioButton[] = new JRadioButton[4];
        jRadioButton[0].setText("Sport");
        jRadioButton[1].setText("Study of general questions");
        jRadioButton[2].setText("Video game");
        jRadioButton[3].setText("IDK)");
*/
        JButton submit = new JButton("Submit");
       // submit.addActionListener(e -> {
            //if(jRadioButton[1].isSelected())

        //});

        testPanel.add(question1);
        //for(int i = 0; i < 4; i++)
          //  testPanel.add(jRadioButton[i]);

        //testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
        return testPanel;
    }

    /**
     * This class creates a Quiz page for each classroom
     */
    /*public class Quiz extends JFrame implements ActionListener {
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
            submit.addActionListener(e -> {

            });
            panelHolder[0][1].add(question);
            panelHolder[1][0].setLayout(new BorderLayout());
            panelHolder[1][0].add(questionPanel1, BorderLayout.WEST);
            panelHolder[2][1].add(submit);

            setSize(600,800);
            setVisible(true);
        }

        /**
         * Allows user to submit quizzes to the class.
         * @param e the action performed
         */

        /**
         * Formats the questions for the quiz

    }*/

    public class Grades {


    }



    /**
     * A frame of a classroom will be drawn here
     * @param g the frame
     */
   /* public void paintComponent (Graphics g){
        super.paintComponent(g);
        //g.drawString(className, 10, 10);
        g.drawRect(100, 50, rectangleWidth, rectangleHeight);

    }*/
}



