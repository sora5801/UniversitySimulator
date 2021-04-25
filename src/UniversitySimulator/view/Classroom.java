package UniversitySimulator.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//

/***
 * TODO Create an abridged classroom system.
 * OPTIONAL TODO Add a test system based on how well the student did his or her homework.
 * This is a part of the View Section
 */
public class Classroom extends JPanel {
    private static final int rectangleWidth = 850;
    private static final int rectangleHeight = 600;

    private String className;
    private String professorName;
    private double classGpa;

    public Classroom(/*String className, String professorName*/) {
        this.className = className;
        this.professorName = professorName;
    }

    public class Lecture {
        private String lectureTopic;
        private String lectureMaterial;

        public Lecture(String lectureTopic, String lectureMaterial) {
            this.lectureTopic = lectureTopic;
            this.lectureMaterial = lectureMaterial;
        }
    }

    public class Homework {
        private ArrayList<String> quizQuestions;

        public void assignHW() {

        }
    }



    public class ClassGrade {
        private final Map<String, Double> gradeToScore = new HashMap<>();
        private ArrayList<Double> hwGrades;
        private ArrayList<Double> testGrades;
        private ArrayList<Double> attendanceGrades;

        public void classGrade() {
            gradeToScore.put("A+", 4.33);
            gradeToScore.put("A", 4.00);
            gradeToScore.put("A-", 3.67);
            gradeToScore.put("B+", 3.33);
            gradeToScore.put("B", 3.00);
            gradeToScore.put("B-", 2.67);
            gradeToScore.put("C+", 2.33);
            gradeToScore.put("C", 2.00);
            gradeToScore.put("C-", 1.67);
            gradeToScore.put("D+", 1.33);
            gradeToScore.put("D", 1.00);
            gradeToScore.put("F", 0.0);
            gradeToScore.put("FX", 0.0);
        }

        public double getGpa() {
            double hwTotal = 0;
            double testTotal = 0;
            double attendanceTotal = 0;

            for (double i : hwGrades)
                hwTotal += i;
            for (double i : testGrades)
                testTotal += i;
            for (double i : attendanceGrades)
                attendanceTotal += i;

            classGpa = 0.6 * testTotal + 0.3 * hwTotal + 0.1 * attendanceTotal; // not done

            return 0;
        }
    }

    /**
     * A frame of a classroom will be drawn here
     * @param g
     */
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        //g.drawString(className, 10, 10);
        g.drawRect(100, 50, rectangleWidth, rectangleHeight);

    }
}


