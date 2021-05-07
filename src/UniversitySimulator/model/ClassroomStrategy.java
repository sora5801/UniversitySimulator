package UniversitySimulator.model;

import UniversitySimulator.view.Classroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClassroomStrategy implements CampusStrategy {
    private double moneyAmount; //This is to try to simulate pass by reference.
    private HashMap<String, Double> itemMoney;
    private double classGpa;
    private String className;
    private String professorName;

    public ClassroomStrategy() {
        this.className = null;
        this.professorName = null;
    }

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

    public class Lecture {
        private String lectureTopic;
        private String lectureMaterial;

        public Lecture(String lectureTopic, String lectureMaterial) {
            this.lectureTopic = lectureTopic;
            this.lectureMaterial = lectureMaterial;
        }

    }

    public class Homework {

    }

    public class Test {

    }

    public class Quiz {

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

            return classGpa;
        }
    }
}
