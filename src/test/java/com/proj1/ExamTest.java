package com.proj1; import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.logic.Exam;
import com.logic.Student;

import org.junit.jupiter.api.Test;

//Of junit5 (e.g. org.junit.jupiter) of junit4 (e.g. org.junit.Test)
public class ExamTest {
    static TestScanner testScanner = new TestScanner();
    static Exam testExam = new Exam("OPT1", "HBO-ICT");
    static Student testStudent = new Student("Pietje", 21146633);
    static Student testStudent2 = new Student("Klaasje", 21146634);

    //test of de naam wordt opgevraagd
    @Test
    public void testGetName(){
        String testName = "OPT1";
        String foutTestName = "Biologie";
        
        String resultName = testExam.getName();
        assertEquals(testName, resultName);
        assertNotEquals(foutTestName, resultName);
    }

    //test of de category wordt opgevraagd
    @Test
    public void testGetCategory(){
        String testCategory = "HBO-ICT";
        String foutTestCategory = "Beta";

        String resultCategory = testExam.getCategory();
        assertEquals(testCategory, resultCategory);
        assertNotEquals(foutTestCategory, resultCategory);
    }

    /*
    Deze test test of een examen goed wordt toegevoegd bij een student. 
    */
    @Test
    public void testAddExam(){
        testExam.examResult(testStudent, 0, true);
        testExam.examResult(testStudent2, 0, false);
        assertEquals(testExam, testStudent.behaaldeExamens.get(0));
        assertTrue(testStudent2.behaaldeExamens.isEmpty());
    }
    
    @Test
    public void testRandom(){
        Exam.printAllExams(testScanner);
        testScanner.setInt(3);
        assertEquals(3, testScanner.nextInt());
    }

    @Test
    public void testGetExam(){
        assertEquals(testExam, Exam.getExam(0));
    }
    
}
